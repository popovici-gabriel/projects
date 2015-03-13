#include "locatedblock.h"
#include "serializer.h"
#include <QDebug>
LocatedBlock::LocatedBlock()
{
}

void LocatedBlock::write(QByteArray & out)
{
  Serializer s;
  blockToken.write(out);
  s.writeBoolean(out, corrupt);
  s.writeLong(out,offset);
  b.write(out);
  s.writeInt(out,locs.size());
  for (uint i = 0; i < locs.size(); i++)
    {
        locs[i].write(out);
    }
}

void LocatedBlock::readFields(QByteArray & in, int &pos)
{
  Serializer s;
  blockToken.readFields(in,pos);
  corrupt = s.readBoolean(in,pos);
  offset = s.readLong(8,in,pos);
  b = Block();
  b.readFields(in,pos);
  int count = s.readInt(in,pos);
  locs = vector<DatanodeInfo>(count);
  for (uint i = 0; i < locs.size(); i++)
    {
      locs[i] = DatanodeInfo();
      locs[i].readFields(in,pos);
   }
}

QString
LocatedBlock::javaClassName()
{
  return "org.apache.hadoop.hdfs.protocol.LocatedBlock";
}


void
LocatedBlock::toString()
{
  qDebug() << "[LocatedBlock: ";
  b.toString();
  if (!locs.empty())
    {
      foreach (DatanodeInfo info, locs)
          info.toString();
    }
  qDebug() <<"]";
}

Block LocatedBlock::getB() const
{
    return b;
}

void LocatedBlock::setB(const Block &value)
{
    b = value;
}

vector<DatanodeInfo> LocatedBlock::getLocs() const
{
    return locs;
}

void LocatedBlock::setLocs(const vector<DatanodeInfo> &value)
{
    locs = value;
}
