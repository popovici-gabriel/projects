#include "packet.h"
#include <QByteArray>
#include <QDebug>
#include "serializer.h"
#include "crc32.h"
#include "datatransferprotocol.h"

Packet::Packet(int pktSize, int chunksPerPkt, long offsetInBlock, quint64 currentSeqno)
{
    lastPacketInBlock = false;
    numChunks = 0;
    this->offsetInBlock = offsetInBlock;
    seqno = currentSeqno;
    buf = QByteArray(pktSize, '0');
    checksumStart = PKT_HEADER_LEN + SIZE_OF_INTEGER;
    checksumPos = checksumStart;
    dataStart = checksumStart + chunksPerPkt * CHECKSUM_CRC32_SIZE;
    dataPos = dataStart;
    maxChunks = chunksPerPkt;
}

void
Packet::writeChecksum(QByteArray& inarray, int off, int len)
{
  if ( checksumPos + len > dataStart)
    {
      qWarning()<<" writeChecksum  BufferOverflowException";
    }
//  memcpy(buf+checksumPos, inarray+off,len);
  for (int var = 0; var < len; ++var)
    {
      buf[checksumPos+var] = inarray[off+var];
    }
  //buf.replace(checksumPos, len, inarray.constData()+off);
  checksumPos += len;
}

Packet::~Packet()
{
  buf.clear();
  buffer.clear();
}

void
Packet::writeData(QByteArray& inarray, int off, int len)
{
  if ( dataPos + len > buf.size())
    {
      qWarning() <<" writeData BufferOverflowException()";
  }
  //memcpy(buf+dataPos,inarray+off,len);
  //buf.replace(dataPos,len, inarray.constData()+off);
  for (int var = 0; var < len; ++var)
    {
      buf[dataPos+var] = inarray[var+off];
    }
  dataPos += len;
}



QByteArray
Packet::getBuffer()
{
  //prepare the header and close any gap between checksum and data.
  int dataLen = dataPos - dataStart;
  int checksumLen = checksumPos - checksumStart;
  if (checksumPos != dataStart)
    {
      /* move the checksum to cover the gap.
       * This can happen for the last packet.
       */
      //System.arraycopy(buf, checksumStart, buf,
                       //dataStart - checksumLen , checksumLen);
      //memcpy(buf+(dataStart-checksumLen),buf+checksumStart,checksumLen);

      QByteArray src = buf.mid(checksumStart, checksumLen);
      for (int var = 0; var < checksumLen; ++var)
        {
          buf[(dataStart-checksumLen) + var] = src[var];
        }
    }
   int pktLen = SIZE_OF_INTEGER + dataLen + checksumLen;
   packetLen = PKT_HEADER_LEN + pktLen;//length
   packetOff =dataStart - checksumPos;//at position
   buffer = QByteArray(buf);//used in socket

   /* write the header and data length.
     * The format is described in comment before DataNode.BlockSender
     */
   int pos = packetOff;
   Serializer s;
   s.writeInt(buffer ,pos, pktLen);//pktSize
   s.writeLong(buffer,pos,offsetInBlock);
   s.writeLong(buffer,pos,seqno);
   buffer[pos++] = (byte) ((lastPacketInBlock) ? 1 : 0);
   //end of pkt header
   s.writeInt(buffer,pos,dataLen); // actual data length, excluding checksum.
  return buffer;
}

quint32 PacketSize::getChunkSize() const
{
    return chunkSize;
}

void PacketSize::setChunkSize(const quint32 &value)
{
    chunkSize = value;
}

quint32 PacketSize::getChunksPerPacket() const
{
    return chunksPerPacket;
}

void PacketSize::setChunksPerPacket(const quint32 &value)
{
    chunksPerPacket = value;
}

quint32 PacketSize::getPacketSize() const
{
    return packetSize;
}

void PacketSize::setPacketSize(const quint32 &value)
{
    packetSize = value;
}

void Packet::increaseNumChunks()
{
  numChunks++;
}

int
Packet::getNumChunks() const {
  return numChunks;
}

PacketSize::PacketSize(quint32 psize, quint32 csize)
{
  chunkSize = csize + CHECKSUM_CRC32_SIZE;
  quint32 n = PKT_HEADER_LEN +  SIZE_OF_INTEGER;
  chunksPerPacket = qMax( (int)((psize - n + chunkSize-1)/chunkSize), 1);
  packetSize = n + chunkSize*chunksPerPacket;
}


bool Packet::getLastPacketInBlock() const
{
  return lastPacketInBlock;
}

void Packet::setLastPacketInBlock(bool value)
{
  lastPacketInBlock = value;
}

quint32 Packet::getPacketOff() const
{
    return packetOff;
}

void Packet::setPacketOff(const quint32 &value)
{
    packetOff = value;
}

quint32 Packet::getPacketLen() const
{
    return packetLen;
}

void Packet::setPacketLen(const quint32 &value)
{
    packetLen = value;
}

int Packet::getMaxChunks() const
{
  return maxChunks;
}

void Packet::setMaxChunks(int value)
{
  maxChunks = value;
}

PacketWriter::PacketWriter()
{
  blockSize = DFS_BLOCK_SIZE;
  maxPackets = 80; // each packet 64K, total 5MB
  currentSeqno = 0;
  lastQueuedSeqno = -1;
  lastAckedSeqno = -1;
  bytesCurBlock = 0; // bytes writen in current block
  packetSize = 0; // write packet size, including the header.
  chunksPerPacket = 0;
  packet = NULL;
  remaining = 0 ;
  inputLen = 0;
}

PacketWriter::PacketWriter(quint64 fromSeq)
{
  blockSize = DFS_BLOCK_SIZE;
  maxPackets = 80; // each packet 64K, total 5MB
  currentSeqno = fromSeq;
  lastQueuedSeqno = -1;
  lastAckedSeqno = -1;
  bytesCurBlock = 0; // bytes writen in current block
  packetSize = 0; // write packet size, including the header.
  chunksPerPacket = 0;
  packet = NULL;
  remaining = 0;
  inputLen = 0;
}

void
PacketWriter::write(QByteArray &input, const quint64 inputSize, QTcpSocket *socket)
{
  inputLen = inputSize;
  PacketSize size;
  int offset = 0, n=0;
  //fill packet
  while(n <input.size())
    {
      if (NULL ==  packet)
        {
          packet  = new Packet(size.getPacketSize(),size.getChunksPerPacket(),bytesCurBlock, currentSeqno);
        }

       n+= fillPacket(input,offset+n, input.size()-n);

       //if packet is full, enqueue it for transmission
        if (packet->getNumChunks() == packet->getMaxChunks())
        {
           writePacketToSocket(currentSeqno, socket);
           delete packet;
           packet = NULL;
           currentSeqno++; //increase seq
         }
    }

  // enqueue it for transmission
  writePacketToSocket(currentSeqno, socket);
  if (NULL != packet)
    delete packet;

  currentSeqno++; //increase seq
}

quint32
PacketWriter::fillPacket(QByteArray &input, quint32 offset, quint32 len)
{
  remaining = len;
  lzo_uint32 check_sum = lzo_crc32(0,
                                                                  input.data()+offset,
                                                                  len >= IO_BYTES_PER_CHECKSUM ? IO_BYTES_PER_CHECKSUM : len);
  byte*b_check_sum = int2byte(check_sum,CHECKSUM_CRC32_SIZE);
  fillPacketWithChecksum(input,
                                            offset,
                                            len >= IO_BYTES_PER_CHECKSUM ? IO_BYTES_PER_CHECKSUM : len,
                                            b_check_sum);
  return len >= IO_BYTES_PER_CHECKSUM ? IO_BYTES_PER_CHECKSUM : len;
}


void
PacketWriter::fillPacketWithChecksum(QByteArray &input, quint32 offset, quint32 len, byte *checksum)
{
 QByteArray byteArrayChecksum((const char *) checksum,CHECKSUM_CRC32_SIZE);
 //qDebug() << "AT OFFSET: " << offset << " CRC32: " << "[" << (byte) byteArrayChecksum[0]<<", "<< (byte) byteArrayChecksum[1]<<", "<< (byte) byteArrayChecksum[2]<<", "<< (byte) byteArrayChecksum[3]<<"] " ;
  packet->writeChecksum(byteArrayChecksum, 0, CHECKSUM_CRC32_SIZE);
  packet->writeData(input,offset,len);
  packet->increaseNumChunks();
  bytesCurBlock += len;
}

void
PacketWriter::writePacketToSocket(int current, QTcpSocket *socket)
{
  bool last = lastPacket();
  packet->setLastPacketInBlock(last);
  QByteArray p = packet->getBuffer();
  QByteArray bytes =p.mid(packet->getPacketOff(),packet->getPacketLen());
   if (last)
     {
       Serializer s;
      s.writeInt(bytes, 0); // end of packet
     }

   qDebug() << "About to send  packet: " <<current << " with size: "<< bytes.size();
   socket->write(bytes);
   if (!socket->waitForBytesWritten())
     qWarning() <<"Error writing to socket: "<<socket->errorString();
   socket->flush();

   //TODO fix the timeout
   int timeout = 3  * 10;
   quint64 available = 0;
   do
     {
       if (!socket->waitForReadyRead(timeout))
         {
            //qDebug() << socket->error() << " - "<<socket->errorString();
         }
       available = socket->bytesAvailable();
     }while(socket->waitForReadyRead(timeout));

   qDebug() << "Available to read: " << available;
}


bool PacketWriter::lastPacket()
{
  bool last  = bytesCurBlock == inputLen;
//  qDebug() << "Last packet: " << last;
  return last;
}

WriteOperation::WriteOperation(quint64 blockId, quint64 genStamp)
{
  Serializer s;
  s.writeShort(header, DataTransferProtocol::DATA_TRANSFER_VERSION);
  s.writeByte(header, DataTransferProtocol::OP_WRITE_BLOCK);
  s.writeLong(header,blockId);//block id
  s.writeLong(header,genStamp);// generation stamp

  //TODO add replication
  s.writeInt(header,1);//nodes length
  s.writeBoolean(header, false);//recovery flag

  //write client
  s.writeVInt(header, qstrlen(CLIENT.c_str()));
  header.append(QString::fromStdString(CLIENT).toUtf8());
  //end write client

  s.writeBoolean(header,false);//not sending src node information
//TODO add replication support
  s.writeInt(header,0);//nodes length -1 ->no replication


  // access token
  s.writeVLong(header,0);
  s.writeVLong(header,0);
  s.writeVLong(header,0);
  s.writeVLong(header,0);

   //checksum header
  s.writeByte(header,1);
  s.writeInt(header,IO_BYTES_PER_CHECKSUM);
}

WriteOperation::WriteOperation(quint64 blockId, quint64 genStamp, vector<DatanodeInfo> locs)
{
  Serializer s;
  s.writeShort(header, DataTransferProtocol::DATA_TRANSFER_VERSION);
  s.writeByte(header, DataTransferProtocol::OP_WRITE_BLOCK);
  s.writeLong(header,blockId);//block id
  s.writeLong(header,genStamp);// generation stamp

  s.writeInt(header,locs.size());//nodes replication length
  s.writeBoolean(header, false);//recovery flag

  //write client
  s.writeVInt(header, qstrlen(CLIENT.c_str()));
  header.append(QString::fromStdString(CLIENT).toUtf8());
  //end write client

  s.writeBoolean(header,false);//not sending src node information
  s.writeInt(header,locs.size()-1);//first already taken
  for (uint var = 1; var < locs.size(); ++var) //first already taken
    {
       DatanodeInfo node  = locs.at(var);
       node.write(header);
    }

  // access token
  s.writeVLong(header,0);
  s.writeVLong(header,0);
  s.writeVLong(header,0);
  s.writeVLong(header,0);

   //checksum header
  s.writeByte(header,1);
  s.writeInt(header,IO_BYTES_PER_CHECKSUM);
}

QByteArray
WriteOperation ::getHeader() const
{
  return header;
}

quint64 PacketWriter::getCurrentSeqno() const
{
  return currentSeqno;
}

void PacketWriter::setCurrentSeqno(const quint64 &value)
{
  currentSeqno = value;
}
