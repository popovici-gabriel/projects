#include "createrequest.h"
#include "serializer.h"

CreateRequest::CreateRequest()
  :BaseRequest()
{
  this->method = "create";
  this->args = 7;
}



void  CreateRequest::build(const char* src,
                                                  FsPermission masked,
                                                  const char* clientName,
                                                  bool overwrite,
                                                  bool createParent,
                                                  short replication,
                                                  long blockSize)
 {
  buildHeader();
  buildProtocol();

  const int counter = 0;
  Serializer serializer;
  QByteArray buffer;
  serializer.writeInt(buffer,counter);
  serializer.writeString(buffer, method);
  serializer.writeInt(buffer, args);// number of args in method

  // 1st arg
  QString arg1Type= QString("java.lang.String");
  serializer.writeString(buffer,arg1Type);//type
  QString arg1Value = QString(src);
  serializer.writeString(buffer,arg1Value); //value

  // 2nd arg
  QString arg2Type= QString(FsPermission::className);
  serializer.writeString(buffer,arg2Type);//type
  QString arg2Value = QString(FsPermission::className);
  serializer.writeString(buffer,arg2Value);
  serializer.writeShort(buffer,masked.toShort());

  //3rd arg
  QString arg3Type= QString("java.lang.String");
  serializer.writeString(buffer,arg3Type);//type
  QString arg3Value = QString(clientName);
  serializer.writeString(buffer,arg3Value); //value

  //4th arg
  serializer.writeString(buffer,"boolean");
  serializer.writeBoolean(buffer,overwrite);

  //5th arg
  serializer.writeString(buffer,"boolean");
  serializer.writeBoolean(buffer,createParent );

  //6th arg
  serializer.writeString(buffer,"short");
  serializer.writeShort(buffer,replication);

  //7th argument
  serializer.writeString(buffer,"long");
  serializer.writeLong(buffer, blockSize);

  serializer.writeInt(payloadBytes,buffer.size());
  payloadBytes.append(buffer);
}
