#include "remoteconnection.h"
#include <QDebug>
#include "packet.h"

RemoteConnection::RemoteConnection(
    string h,
    uint p,
    const uint time):host(h),port(p),timeout(time)
{
  connected = false;
}

RemoteConnection::~RemoteConnection()
{
  //if (socket->isOpen())
  //  socket->close();

  //delete socket;
}

bool
RemoteConnection::connect()
{
  socket = new QTcpSocket();
  socket->setSocketOption(QAbstractSocket::LowDelayOption, true);
  socket->connectToHost(QString::fromStdString(host),port);
  if (socket->waitForConnected())
  {
      this->connected= true;
       qDebug() << "Connected to: "<< QString::fromStdString(host) << " :" << port;
  }
  else
  {
      qWarning() << "Unable to connect to socket " << QString::fromStdString(host) << ":" << port;
   }
  return connected;
}


bool
RemoteConnection::isConnected() const
{
  return connected && QAbstractSocket::ConnectedState == socket->state();
}


void
RemoteConnection::displayError(int socketError, const QString &message)
 {
       switch (socketError)
       {
        case QAbstractSocket::HostNotFoundError:
           qWarning() <<"Host not found: "<<  QString::fromStdString(host);
           break;
        case QAbstractSocket::ConnectionRefusedError:
           qWarning()<< "Connection error: "<< message;
           break;
        default:
           qCritical() << "Connection error: " << message;
           break;
     }
 }

void
RemoteConnection::close()
{
  socket->close();
}

quint64
RemoteConnection::bytesAvailable()
{
  quint64 available = 0;
  do
    {
      if (!socket->waitForReadyRead(timeout))
        displayError(socket->error(),socket->errorString());

      available = socket->bytesAvailable();
    }while(socket->waitForReadyRead(timeout));

  return available;
}

QByteArray
RemoteConnection::read(quint64 maxlen)
{
  return socket->read(maxlen);
}

bool
RemoteConnection::writeBytes(QByteArray bytes)
{
  socket->write(bytes);
  if (!socket->waitForBytesWritten())
    {
      qWarning() <<"Error writing to socket: "<<socket->errorString();
      return false;
    }

  socket->flush();
  return true;
}

void
RemoteConnection::writePacket(QByteArray& input)
{
  PacketWriter pw;
  pw.write(input,input.size(),socket);
}

QTcpSocket *RemoteConnection::getSocket() const
{
  return socket;
}

void RemoteConnection::setSocket(QTcpSocket *value)
{
  socket = value;
}
