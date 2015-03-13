#include <QCoreApplication>
#include<QTcpSocket>
#include<QBuffer>
#include<QDebug>
#include<iostream>
using namespace std;
#include "clientprotocol.h"
#include "clientprotocolimpl.h"
#include<QDateTime>
#include <QDir>
#include<QTcpSocket>
#include "datatransferprotocol.h"
#include "serializer.h"
#include "packet.h"
#include <QtGlobal>
#include "writefile.h"

const static char EMPTY[] = {'\0'};
static const long blockSize = 8192;
static const short replication = 1;
//static const QString DIR = "/anritsu/mclaw/eoDR/buffer/eoAINT/2013/05/27/16/QXDRS_CAME-AINT1/";
static const QString DIR = "/";
static QString name;

static void test_block_creation(QString hostname,
                                                             long blockId,
                                                             long genStamp);


static void readFile(QByteArray& bytes, QString fileName)
{
  QFile file(fileName);
  qDebug() << "File: " <<fileName <<" Size: " <<file.size();
  if (!file.open(QIODevice::ReadOnly ))
    qDebug() << "Error opening file";

  while(!file.atEnd())
    {
      bytes.append(file.readLine());
    }
}

static void check_list()
{
  ClientProtocol* protocol = ClientProtocolImpl::getInstance();
  DirectoryListing* dir  =
      protocol->getListing(DIR.toStdString().c_str(), EMPTY);

  if (NULL != dir )
    {
     dir->print();
     delete dir;
     dir =NULL;
    }

  delete protocol;
  protocol = NULL;
}

static void check_create()
{
                   name = QString(DIR)
                                .append("gabriel-test")
                                .append(QDateTime::currentDateTime().toString("-dd-m-yyyy-hh-mm-ss"))
                                .append(".dat");
  ClientProtocol* protocol = ClientProtocolImpl::getInstance();
  protocol->create(name.toStdString().c_str(),
                                    FsPermission(FsPermission::ALL,FsPermission::ALL,FsPermission::ALL),
                                    "",
                                    true,
                                    true,
                                    replication,
                                    blockSize
                                  );
  delete protocol;
  protocol = NULL;
}

static void check_add_block()
{
  ClientProtocol* protocol = ClientProtocolImpl::getInstance();
  LocatedBlock* block = protocol->addBlock(name,"", vector<DatanodeInfo>());
  if (block != NULL)
    {
      block->toString();
      delete block;
    }

  delete protocol;
  protocol = NULL;
}

static void create_and_list()
{
  name = QString(DIR)
                //.append(QDir::separator())
               .append("gabriel-test")
               .append(QDateTime::currentDateTime().toString("-dd-m-yyyy-hh-mm-ss"))
               .append(".dat");
  ClientProtocol* protocol =ClientProtocolImpl::getInstance();
  protocol->create(name.toStdString().c_str(),
                     FsPermission(FsPermission::ALL,FsPermission::ALL,FsPermission::ALL),
                     "",
                     true,
                     true,
                     replication,
                     blockSize
                   );
    DirectoryListing* dir  =
        protocol->getListing(DIR.toStdString().c_str(), EMPTY);

    if (NULL != dir )
      {
       dir->print();
       delete dir;
       dir =NULL;
      }

    delete protocol;
    protocol = NULL;
}

static void create_and_add_block()
{
  name = QString(DIR)
               .append("gabriel-test")
               .append(QDateTime::currentDateTime().toString("-dd-m-yyyy-hh-mm-ss"))
               .append(".dat");
  ClientProtocol* protocol =ClientProtocolImpl::getInstance();
  protocol->create(name.toStdString().c_str(),
                     FsPermission(FsPermission::ALL,FsPermission::ALL,FsPermission::ALL),
                     "",
                     true,
                     true,
                     replication,
                     DFS_BLOCK_SIZE);
  LocatedBlock* block = protocol->addBlock(name,"", vector<DatanodeInfo>());
  if (block != NULL)
    {
      block->toString();
      long blockId = block->getB().getBlockId();
      long genStamp = block->getB().getGenerationStamp();
      QString hostname = block->getLocs().at(0).getName();
      test_block_creation(hostname,blockId,genStamp);
      delete block;
    }

  delete protocol;
  protocol = NULL;
}

static void test_block_creation(QString hostname,
                                                             long blockId,
                                                             long genStamp)
{
  QStringList list = hostname.split(":");
  QTcpSocket *socket = new QTcpSocket;
  socket->connectToHost(list[0],list[1].toInt());
  if (socket->waitForConnected())
  {
       qDebug() << "Connected to: "<<list;
  }
  else
  {
      qWarning() << "Unable to connect to socket " << Configuration::readHost() << ":" <<Configuration::readPort();
   }
  QByteArray bytes;
  Serializer s;
  s.writeShort(bytes, DataTransferProtocol::DATA_TRANSFER_VERSION);
  s.writeByte(bytes, DataTransferProtocol::OP_WRITE_BLOCK);
  s.writeLong(bytes,blockId);//block id
  s.writeLong(bytes,genStamp);// generation stamp
  s.writeInt(bytes,1);//nodes length
  s.writeBoolean(bytes, false);//recovery flag

  //write client
  QString client  ="Client";
  s.writeVInt(bytes, client.length());
  bytes.append(client.toUtf8());
  //end write client

  s.writeBoolean(bytes,false);//not sending src node information
  s.writeInt(bytes,0);//nodes length -1

  // access token
  s.writeVLong(bytes,0);
  s.writeVLong(bytes,0);
  s.writeVLong(bytes,0);
  s.writeVLong(bytes,0);

   //checksum header
  s.writeByte(bytes,1);
  s.writeInt(bytes,512);


  socket->write(bytes);
  if (!socket->waitForBytesWritten())
    qWarning() <<"Error writing to socket: "<<socket->errorString();
  socket->flush();
  bytes.clear();


  // write the Packet
  QByteArray input;
  readFile(input,"/home/gabriel/work/libs/eclipse-jee-juno-SR2-linux-gtk-x86_64.tar.gz");
  PacketWriter pw;
  pw.write(input, input.size(),socket);
  socket->close();
  delete socket;
}

int main(int argc, char *argv[])
{
  QCoreApplication a(argc, argv);
  ClientProtocol* protocol = ClientProtocolImpl::getInstance();
  WriteFile writeFile(protocol,"/home/gabriel/work/libs/eclipse-jee-juno-SR2-linux-gtk-x86_64.tar.gz");
  writeFile.setReplication(3);
  writeFile.write();
  qDebug() << "Done";
  return 0;
}
