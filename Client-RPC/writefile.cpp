#include "writefile.h"
#include <QFile>
#include<QDebug>
#include "fspermission.h"
#include <QStringList>
#include "remoteconnection.h"
#include<QDir>

WriteFile::WriteFile(
    ClientProtocol * p,
    string absFileName,
    quint64  blocksize)
{
  this->protocol = p;
  this->fileAbsName = absFileName;
  this->blockSize = blocksize;
  created = false;
  replication = 0;
  remain = 0;
  root = "/";
  currentSeqno = 0;
}

WriteFile::~WriteFile()
{
  input.clear();
}

void
WriteFile::readFile()
{
  QFile file(QString::fromStdString(this->fileAbsName));
  QFileInfo fileInfo(file.fileName());
  fileName = fileInfo.fileName().toStdString();

  qDebug() << "File: " <<QString::fromStdString(this->fileAbsName) <<" Size: " <<file.size();
  if (!file.open(QIODevice::ReadOnly ))
    qDebug() << "Error opening file";

  while(!file.atEnd())
    {
      input.append(file.readLine());
    }
  file.close();
}


bool
WriteFile::write(QByteArray& input)
{
  if (!created)
    createFile();

  if (created)
    {
        LocatedBlock* block = protocol->addBlock(hdfsFileName.c_str(),
                                                                                  CLIENT.c_str(),
                                                                                  vector<DatanodeInfo>());
        if (block != NULL)
        {
            vector<DatanodeInfo> locations = block->getLocs();
            int size = locations.size();
            if (size == 0)
              {
                qWarning() << "Error: empty locations";
                return ERROR;
              }

          block->toString();
          quint64 blockId = block->getB().getBlockId();
          quint64 genStamp = block->getB().getGenerationStamp();

          //connnect to the first one in the list;
          QString hostname = block->getLocs().at(0).getName();
          QStringList list = hostname.split(":");
          RemoteConnection connection(list[0].toStdString(),list[1].toInt(),20);
          connection.connect();

          WriteOperation writeOp(blockId, genStamp, locations);
          connection.writeBytes(writeOp.getHeader());

          PacketWriter pw(currentSeqno);
          pw.write(input,input.size(), connection.getSocket());
          currentSeqno = pw.getCurrentSeqno();

          delete block;
        }
    }
  return OK;
}

quint64
WriteFile::read(quint64 offset, quint64 remain)
{
  QByteArray buffer = input.mid(offset,remain >= blockSize ? blockSize : remain);
  write(buffer);
  return remain >= blockSize ? blockSize : remain;
}

void
WriteFile::createFile()
{
  hdfsFileName = root.append(fileName);
  protocol->create(hdfsFileName.c_str(),
                                FsPermission(FsPermission::ALL,FsPermission::ALL,FsPermission::ALL),
                                CLIENT.c_str(),
                                true,
                                true,
                                this->replication,
                                this->blockSize);
  created = true;
}

void
WriteFile::setBlockSize(quint64 b)
{
  this->blockSize =b;
}

void
WriteFile::setReplication(short r)
{
  this->replication =r;
}


void
WriteFile::write()
{
  readFile();
  quint64 n = 0 ,offset= 0;
  while(n < (quint64) input.size())
    n+= read(offset+n, input.size()-n);
}

void
WriteFile::setHdfsLocation(string root)
{
  this->root = root;
}
