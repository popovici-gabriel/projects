#ifndef PACKET_H
#define PACKET_H
#include "global-types.h"
#include <QByteArray>
#include <QTcpSocket>
#include "datanodeinfo.h"

/* ********************************************************************
Protocol when a client reads data from Datanode (Cur Ver: 9):

Client's Request :
=================

   Processed in DataXceiver:
   +----------------------------------------------+
   | Common Header   | 1 byte OP == OP_READ_BLOCK |
   +----------------------------------------------+

   Processed in readBlock() :
   +-------------------------------------------------------------------------+
   | 8 byte Block ID | 8 byte genstamp | 8 byte start offset | 8 byte length |
   +-------------------------------------------------------------------------+
   |   vInt length   |  <DFSClient id> |
   +-----------------------------------+

   Client sends optional response only at the end of receiving data.

DataNode Response :
===================

  In readBlock() :
  If there is an error while initializing BlockSender :
     +---------------------------+
     | 2 byte OP_STATUS_ERROR    | and connection will be closed.
     +---------------------------+
  Otherwise
     +---------------------------+
     | 2 byte OP_STATUS_SUCCESS  |
     +---------------------------+

  Actual data, sent by BlockSender.sendBlock() :

    ChecksumHeader :
    +--------------------------------------------------+
    | 1 byte CHECKSUM_TYPE | 4 byte BYTES_PER_CHECKSUM |
    +--------------------------------------------------+
    Followed by actual data in the form of PACKETS:
    +------------------------------------+
    | Sequence of data PACKETs ....      |
    +------------------------------------+

  A "PACKET" is defined further below.

  The client reads data until it receives a packet with
  "LastPacketInBlock" set to true or with a zero length. If there is
  no checksum error, it replies to DataNode with OP_STATUS_CHECKSUM_OK:

  Client optional response at the end of data transmission :
    +------------------------------+
    | 2 byte OP_STATUS_CHECKSUM_OK |
    +------------------------------+

  PACKET : Contains a packet header, checksum and data. Amount of data
  ======== carried is set by BUFFER_SIZE.

    +-----------------------------------------------------+
    | 4 byte packet length (excluding packet header)      |
    +-----------------------------------------------------+
    | 8 byte offset in the block | 8 byte sequence number |
    +-----------------------------------------------------+
    | 1 byte isLastPacketInBlock                          |
    +-----------------------------------------------------+
    | 4 byte Length of actual data                        |
    +-----------------------------------------------------+
    | x byte checksum data. x is defined below            |
    +-----------------------------------------------------+
    | actual data ......                                  |
    +-----------------------------------------------------+

    x = (length of data + BYTE_PER_CHECKSUM - 1)/BYTES_PER_CHECKSUM *
        CHECKSUM_SIZE

    CHECKSUM_SIZE depends on CHECKSUM_TYPE (usually, 4 for CRC32)

    The above packet format is used while writing data to DFS also.
    Not all the fields might be used while reading.

 ************************************************************************ */

/** Header size for a packet */
 const  static quint32 PKT_HEADER_LEN = ( 4 + /* Packet payload length */
                                      8 + /* offset in block */
                                      8 + /* seqno */
                                      1   /* isLastPacketInBlock */);

const static quint32 CHECKSUM_CRC32_SIZE = 4;

/** io.bytes.per.checksum - Internal Hadoop configuration prop.  */
const static quint32 IO_BYTES_PER_CHECKSUM =512;

/** dfs.write.packet.size - Internal Hadoop configuration prop */
const static quint32 DFS_WRITE_PACKET_SIZE = 64 * 1024;

/** dfs.block.size - Internal Hadoop configuration prop. */
const static quint64 DFS_BLOCK_SIZE = 64 * 1024 * 1024;

/**
 * Keep information on packet size and chuncks
 */
class PacketSize;
/**
 * @brief The Packet class from java Packet
 */
class Packet
{
public:
  explicit Packet(int pktSize, int chunksPerPkt, long offsetInBlock, quint64 currentSeqno);
  ~Packet();
  void writeData(QByteArray& input,  int off, int len);
  void writeChecksum(QByteArray& input, int off, int len);

  /**
   * @brief getBuffer Returns ByteBuffer that contains one full packet, including header.
   * @return pointer to aray byte
   */
  QByteArray getBuffer();
  void increaseNumChunks();
  bool getLastPacketInBlock() const;
  void setLastPacketInBlock(bool value);

  quint32 getPacketOff() const;
  void setPacketOff(const quint32 &value);

  quint32 getPacketLen() const;
  void setPacketLen(const quint32 &value);

  int getMaxChunks() const;
  void setMaxChunks(int value);

  int getNumChunks() const;
private:
  // internal members
  QByteArray  buf;//internal buffer
  qint64    seqno;               // sequencenumber of buffer in block
  qint64    offsetInBlock;       // offset in block
  bool lastPacketInBlock;   // is this the last packet in block?
  int     numChunks;           // number of chunks currently in packet
  int     maxChunks;           // max chunks in packet
  int     dataStart;
  int     dataPos;
  int     checksumStart;
  int     checksumPos;

  // used on socket write
  QByteArray buffer; // external buffer used for socket Tx
  quint32 packetLen; // the actual data length
  quint32 packetOff; // starting from offset
};

class PacketSize
{
public:
  explicit PacketSize(quint32 psize = DFS_WRITE_PACKET_SIZE, quint32 csize= IO_BYTES_PER_CHECKSUM);
  quint32 getChunkSize() const;
  void setChunkSize(const quint32 &value);

  quint32 getChunksPerPacket() const;
  void setChunksPerPacket(const quint32 &value);

  quint32 getPacketSize() const;
  void setPacketSize(const quint32 &value);

private:
  quint32 chunkSize;
  quint32 chunksPerPacket;
  quint32 packetSize;
};

class PacketWriter
{
public:
  PacketWriter();
  PacketWriter(quint64 fromSeq);
  void write(QByteArray& input, const quint64 size,QTcpSocket* io);
  bool lastPacket();
  quint64 getCurrentSeqno() const;
  void setCurrentSeqno(const quint64 &value);

private:
  quint64 remaining;
  quint32 fillPacket(QByteArray& input,quint32 offset, quint32 len);
  void fillPacketWithChecksum(QByteArray& input,
                                                    quint32 offset,
                                                    quint32 len,
                                                    byte*checksum);
  void writePacketToSocket(int number, QTcpSocket* io);
  quint64 blockSize;
  quint32 maxPackets; // each packet 64K, total 5MB
  quint64 currentSeqno;
  qint64 lastQueuedSeqno;
  qint64 lastAckedSeqno;
  quint64 bytesCurBlock; // bytes writen in current block
  quint64 packetSize; // write packet size, including the header.
  quint32 chunksPerPacket;
  int numberOfPackets; //number of packets
  Packet *packet;
  quint64 inputLen;
};

class WriteOperation
{
public:
  WriteOperation(quint64 blockId, quint64 genstamp);
  WriteOperation(quint64 blockId, quint64 genstamp, vector<DatanodeInfo> locs);
  QByteArray getHeader() const;
private:
  QByteArray header;
};

#endif // PACKET_H
