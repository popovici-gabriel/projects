#include "request.h"
#include "configuration.h"
#include "serializer.h"

Request::Request()
{
}

Request::~Request()
{

}

 void Request::build()
 {
  buildHeader();
  buildProtocol();
  buildPayload();
 }


 void Request::buildHeader()
 {
   this->header.append(Configuration::readHeader());
   this->header.append(Configuration::readVersion());
   this->header.append(Configuration::readAuthentication());
 }

 void Request::buildProtocol()
{
   QByteArray buffer;
   Serializer serializer;
   serializer.writeVLong(buffer,qstrlen(Configuration::readProtocl()));
   buffer.append(Configuration::readProtocl(),qstrlen(Configuration::readProtocl()));
   buffer.append(QByteArray(1,(signed char) 1));// add flag

   // UTF-8 modified
   buffer.append(QByteArray(1,(signed char) 0));// add flag
   buffer.append(QByteArray(1,(signed char) 7));// add flag
   buffer.append(Configuration::readUser(),qstrlen(Configuration::readUser()));//user name
   //end UTF-8 modified
   buffer.append(QByteArray(1,(signed char) 0));// add flag

   int bufLen = buffer.size();
   serializer.writeInt(this->protocol,bufLen); // append  size
   this->protocol.append(buffer);  //append header
}

 static int counter=0;

 void Request::buildPayload()
 {
    Serializer serializer;
    QByteArray buffer;
    serializer.writeInt(buffer,counter);
    QString method = QString(Configuration::readMethod());
    serializer.writeString(buffer, method);
    serializer.writeInt(buffer, Configuration::readMethodArgs());// number of args in method

    // first argument
    QString arg1= QString("java.lang.String");
    serializer.writeString(buffer,arg1);//type
    QString val1 = QString(Configuration::readProtocl());
    serializer.writeString(buffer,val1);

    // second argument
    QString arg2 = QString("long");
    serializer.writeString(buffer,arg2);
    serializer.writeLong(buffer,(long) 1);

    serializer.writeInt(this->payload,buffer.size());
    this->payload.append(buffer);
 }
