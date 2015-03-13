#include "block.h"
#include"serializer.h"
#include<QDebug>

Block::Block()
{
}

void Block::readFields(QByteArray &byte, int &pos)
{
  Serializer s;
  blockId = s.readLong(8,byte,pos);
  numBytes = s.readLong(8,byte,pos);
  generationStamp = s.readLong(8,byte,pos);
}

void Block::write(QByteArray &byte)
{
  Serializer s;
  s.writeLong(byte,blockId);
  s.writeLong(byte,numBytes);
  s.writeLong(byte,generationStamp);
}

QString
Block::javaClassName()
{
  return "org.apache.hadoop.hdfs.protocol.Block";
}


void
Block::toString()
{
  qDebug() <<"[Block:";
  qDebug() <<"BlockID: "<<blockId;
  qDebug() <<"]";
}

qint64 Block::getGenerationStamp() const
{
  return generationStamp;
}

void Block::setGenerationStamp(const qint64 &value)
{
  generationStamp = value;
}

qint64 Block::getNumBytes() const
{
  return numBytes;
}

void Block::setNumBytes(const qint64 &value)
{
  numBytes = value;
}

qint64 Block::getBlockId() const
{
  return blockId;
}

void Block::setBlockId(const qint64 &value)
{
  blockId = value;
}
