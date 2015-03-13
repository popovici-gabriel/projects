#ifndef RESPONSE_H
#define RESPONSE_H
#include<QByteArray>

class Response
{
public:
  explicit Response(const QByteArray& data);
  void decode();
private:
  int readInt();
  QByteArray data;
  int pos;
};

#endif // RESPONSE_H
