#include "baseresponse.h"
#include<QDebug>
#include "serializer.h"

BaseResponse::BaseResponse()
{
}

BaseResponse::~BaseResponse()
{
}

BaseResponse::BaseResponse(const QByteArray& bytes)
  : data(bytes),pos(0)
{
  qDebug() << "Bytes read: " << this->data.size();
}


bool BaseResponse::status()
{
  if (0 == data.size())
    return false;

  Serializer serializer;
  int id  =serializer.readInt(this->data,this->pos);
  qDebug() << "ID call: " <<id;
  int state = serializer.readInt(this->data,this->pos);

  switch (state)
    {
    case SUCCESS:
      qDebug()<<"RPC Success status received";
      break;
    case ERROR:
      qErrnoWarning(ERROR,"RPC Error received");
      for(int i=0;i<2;++i)
        readError();
      break;
    case FATAL:
      for(int i=0;i<2;++i)
        readError();
      break;

    default:
      break;
    }
  return SUCCESS ==state;
}

int BaseResponse::hasRemainingEntries() const
{
  return this->remainingEntries;
}


void BaseResponse::readError()
{
  Serializer s;
  int length = s.readInt(data,pos);
  if ( -1 == length)
    return;

  char* error = s.readFully(length,data,pos);
  qErrnoWarning(ERROR,error);
  delete[]  error;
}

void
BaseResponse::readHeader()
{
  Serializer serializer;
  char* className = serializer.readString(data,pos);
  char* returnType = serializer.readString(data,pos);

  qDebug() << "RPC class name: " << className;
  qDebug() << "RPC return type:" << returnType;
}
