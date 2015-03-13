#include "blocktokenidentifier.h"
#include "serializer.h"


static BlockTokenIdentifier::AccessMode getEnum(char* str)
{
      if ( 0 == qstrcmp("READ",str))
        return BlockTokenIdentifier::READ;
      else if (0 == qstrcmp("WRITE",str))
        return BlockTokenIdentifier::WRITE;
      else if (0 == qstrcmp("COPY",str))
        return BlockTokenIdentifier::COPY;
      else if (0 ==qstrcmp("REPLACE",str))
        return BlockTokenIdentifier::REPLACE;
      else return BlockTokenIdentifier::_NULL;
}

const static char *AccessModes[] = {"READ", "WRITE", "COPY", "REPLACE"};

BlockTokenIdentifier::BlockTokenIdentifier()
{
}


void BlockTokenIdentifier::write(QByteArray &byte)
{
  Serializer s;
  s.writeVLong(byte,expiryDate);
  s.writeVInt(byte,keyId);
  s.writeStringFromWritableUtils(byte,userId);

  s.writeVInt(byte,blockIds.size());
  for(uint i = 0; i < blockIds.size(); i++)
        s.writeVLong(byte, blockIds[i]);

  s.writeVInt(byte, modes.size());
  for(uint i = 0; i < modes.size(); i++)
    s.writeString(byte, AccessModes[modes.at(i)]);
}

void BlockTokenIdentifier::readFields(QByteArray &byte, int &pos)
{
  Serializer s;
  expiryDate = s.readVLong(byte,pos);
  keyId = s.readVInt(byte,pos);
  userId = QString::fromStdString(std::string(s.readStringFromWritableUtils(byte,pos)));
  int blockLen = s.readInt(byte,pos);
  blockIds = vector<long>(blockLen);
  for (int var = 0; var < blockLen; ++var)
    {
      blockIds[var] = s.readVLong(byte,pos);
    }
  int length = s.readVInt(byte,pos);
  modes = vector<AccessMode>(length);
  for (int var = 0; var < length; ++var)
    {
      modes[var] = getEnum(s.readStringFromText(byte,pos));
    }
}


