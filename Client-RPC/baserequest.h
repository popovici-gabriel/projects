#ifndef BASEREQUEST_H
#define BASEREQUEST_H
#include<QByteArray>
#include<QString>

class BaseRequest
{
public:
  BaseRequest();
  virtual ~BaseRequest();
  BaseRequest(const QString &);

  QByteArray getHeaderBytes() const;
  void setHeaderBytes(const QByteArray &value);

  QByteArray getProtocolBytes() const;
  void setProtocolBytes(const QByteArray &value);

  QByteArray getPayloadBytes() const;
  void setPayloadBytes(const QByteArray &value);

  QString getName() const;
  void setName(const QString &value);

  int getArgs() const;
  void setArgs(int value);

  QString getMethod() const;
  void setMethod(const QString &value);

protected:
  virtual void buildHeader();
  virtual void buildProtocol();

  QByteArray headerBytes;
  QByteArray protocolBytes;
  QByteArray payloadBytes;

  QString protocol;
  QString name;
  int args;
  QString method;
};

#endif // BASEREQUEST_H
