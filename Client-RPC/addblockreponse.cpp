#include "addblockreponse.h"
#include "serializer.h"

AddBlockReponse::AddBlockReponse(const QByteArray& r):
  BaseResponse(r)
{
}


AddBlockReponse::~AddBlockReponse()
{
}

LocatedBlock*
AddBlockReponse::parse()
{
  readHeader();
  LocatedBlock* block = new LocatedBlock;
  block->readFields(data,pos);
  return block;
}
