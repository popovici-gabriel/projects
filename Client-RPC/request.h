#ifndef REQUEST_H
#define REQUEST_H
#include<QByteArray>

class Request
{
public:
  Request();
  virtual ~Request();
  Request(const Request& req);
  void build();
  inline const QByteArray& readHeader() const
  {
    return header;
  }

  inline const QByteArray& readProtocol() const
  {
    return protocol;
  }

  inline const QByteArray& readPayload() const
  {
    return payload;
  }
private:
  QByteArray header;
  QByteArray protocol;
  QByteArray payload;

  void buildHeader();
  void buildProtocol();
  void buildPayload();
};

#endif // REQUEST_H
