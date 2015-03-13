#ifndef REMOTECONNECTION_H
#define REMOTECONNECTION_H
#include<QTcpSocket>
#include <iostream>
#include <string>
using namespace std;

class RemoteConnection
{
public:
  RemoteConnection(string remote,
                                 uint port,
                                 const uint TIMEOUT= 3 * 1000);
  virtual ~RemoteConnection();
  bool connect();
  bool isConnected() const;
  void displayError(int socketError, const QString &message);
  void close();
  bool writeBytes(QByteArray bytes);
  quint64 bytesAvailable();
  QByteArray read(quint64 len);
  void writePacket(QByteArray& input);
  QTcpSocket *getSocket() const;
  void setSocket(QTcpSocket *value);
private:
  QTcpSocket *socket;
  string host;
  uint port;
  uint timeout;
  bool connected;
};

#endif // REMOTECONNECTION_H
