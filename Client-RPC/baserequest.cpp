#include "baserequest.h"
#include "configuration.h"
#include "serializer.h"
BaseRequest::BaseRequest()
{
  protocol= "org.apache.hadoop.hdfs.protocol.ClientProtocol";
  name = "BASE_REQUEST";
}

BaseRequest::~BaseRequest()
{
  name.clear();
  protocol.clear();
  headerBytes.clear();
  protocolBytes.clear();
  payloadBytes.clear();
}

BaseRequest::BaseRequest(const QString &n):name(n)
{
}

void BaseRequest::buildProtocol()
{
  QByteArray buffer;
  Serializer serializer;
  serializer.writeVLong(buffer,this->protocol.size());
  buffer.append(this->protocol);
  serializer.writeBoolean(buffer,true);
  // UTF-8 modified @DataOutputStream modified UTF-8
  buffer.append(QByteArray(1,(signed char) 0));// add flag
  buffer.append(QByteArray(1,(signed char)  qstrlen(Configuration::readUser())));// add flag
  buffer.append(Configuration::readUser(),qstrlen(Configuration::readUser()));//user name
  //end UTF-8 modified
  serializer.writeBoolean(buffer,false);
  int bufLen = buffer.size();
  serializer.writeInt(this->protocolBytes,bufLen); // append  size
  this->protocolBytes.append(buffer);  //append header
}

void BaseRequest::buildHeader()
{
  this->headerBytes.append(Configuration::readHeader());
  this->headerBytes.append(Configuration::readVersion());
  this->headerBytes.append(Configuration::readAuthentication());
}

QByteArray BaseRequest::getHeaderBytes() const
{
  return headerBytes;
}

void BaseRequest::setHeaderBytes(const QByteArray &value)
{
  headerBytes = value;
}

QByteArray BaseRequest::getProtocolBytes() const
{
  return protocolBytes;
}

void BaseRequest::setProtocolBytes(const QByteArray &value)
{
  protocolBytes = value;
}

QByteArray BaseRequest::getPayloadBytes() const
{
  return payloadBytes;
}

void BaseRequest::setPayloadBytes(const QByteArray &value)
{
  payloadBytes = value;
}

QString BaseRequest::getName() const
{
    return name;
}

void BaseRequest::setName(const QString &value)
{
    name = value;
}

int BaseRequest::getArgs() const
{
  return args;
}

void BaseRequest::setArgs(int value)
{
  args = value;
}

QString BaseRequest::getMethod() const
{
  return method;
}

void BaseRequest::setMethod(const QString &value)
{
  method = value;
}
