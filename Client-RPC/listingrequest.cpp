#include "listingrequest.h"
#include "serializer.h"
#include "configuration.h"

ListingRequest::ListingRequest() : BaseRequest()
{
  this->method = "getListing";
  this->args = 2;
}

ListingRequest::~ListingRequest()
{

}

void ListingRequest::build(const char* src,  const char* startAfter)
{
  this->buildHeader();
  this->buildProtocol();
  this->buildPayload(src,startAfter);
}

void ListingRequest::buildPayload(const char* src,  const char* startAfter)
{
  dirArg = src;

  static int counter = 0;
  Serializer serializer;
  QByteArray buffer;
  serializer.writeInt(buffer,counter);
  serializer.writeString(buffer, method);
  serializer.writeInt(buffer, this->args);// number of args in method

  // first argument
  QString arg1= QString("java.lang.String");
  serializer.writeString(buffer,arg1);//type
  serializer.writeString(buffer,dirArg); //value

  // second argument
  QString arg2 = QString("[B"); // this is for byte[]
  serializer.writeString(buffer,arg2);
  //array has Length and value
  serializer.writeInt(buffer,qstrlen(startAfter)); //Length

  //FIXME resolve value
  QByteArray arg2Value; //value
  QString type("byte");
  serializer.writeString(arg2Value, type);
  arg2Value.append(QByteArray(1, (signed char) 0));

  buffer.append(arg2Value);

  serializer.writeInt(this->payloadBytes,buffer.size());
  this->payloadBytes.append(buffer);
}
