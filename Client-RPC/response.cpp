#include "response.h"
#include<QDebug>

Response::Response(const QByteArray& bytes): data(bytes),pos(0)
{
  qDebug() << "Bytes read: " << this->data.size();
}


void Response::decode()
{
    int id = this->readInt();
    int state = this->readInt();
    if (state == 0 )
    {//success
        qDebug()<<"RPC Done.State OK.";
    }
    else
    {
        qWarning() <<"RPC Error status !" ;
    }
}

int Response::readInt()
{
  int ch1 = data[pos++];
  int ch2 = data[pos++];
  int ch3 = data[pos++];
  int ch4 = data[pos++];

  if ((ch1 | ch2 | ch3 | ch4) < 0)
    qWarning() <<"Read Error";

  return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
}
