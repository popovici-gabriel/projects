#include "rpcclient.h"
#include "configuration.h"
#include "request.h"
#include"response.h"
#include "listingrequest.h"
#include "listingresponse.h"
#include "createrequest.h"
#include "createresponse.h"

RPCClient::RPCClient(const int time, QObject *parent) :
  QObject(parent),
  connected(false),
  timeout(time),
  send_header(true),
  send_authentication(true)
{
}

RPCClient::~RPCClient()
{
  if (socket->isOpen())
    socket->close();
  delete socket;
}

bool RPCClient::isConnected() const
{
  return connected && QAbstractSocket::ConnectedState == socket->state();
}

void RPCClient::connect()
{
  socket = new QTcpSocket();
  socket->setSocketOption(QAbstractSocket::LowDelayOption, true);
  socket->connectToHost(Configuration::readHost(),Configuration::readPort());
  if (socket->waitForConnected())
  {
      this->connected= true;
       qDebug() << "Connected to: "<< Configuration::readHost() << " :" <<Configuration::readPort();
  }
  else
  {
      qWarning() << "Unable to connect to socket " << Configuration::readHost() << ":" <<Configuration::readPort();
   }
}

void RPCClient::list(const char* src,  const char* startAfter)
{
  if (QAbstractSocket::ConnectedState == socket->state())
    this->getListing(src,startAfter);
  else
    qWarning() <<"Connection not Established";
}

void RPCClient::displayError(int socketError, const QString &message)
 {
       switch (socketError)
       {
        case QAbstractSocket::HostNotFoundError:
           qWarning() <<"Host not found: "<<  Configuration::readHost();
           break;
        case QAbstractSocket::ConnectionRefusedError:
           qWarning()<< "Connection error: "<< message;
           break;
        default:
           qCritical() << "Connection error: " << message;
           break;
     }
 }

DirectoryListing * RPCClient::getListing(const char* src,  const char* startAfter)
{
      ListingRequest request;
      request.build(src,startAfter);
      writeToSocket(request);

      ListingResponse response(socket->read(bytesAvailable()));

      if (response.status())
        return response.parse();

      return NULL;
}

void RPCClient::close()
{
  socket->close();
}


void RPCClient::readyRead()
{

}

int RPCClient::getTimeout() const
{
  return timeout;
}

void RPCClient::setTimeout(int value)
{
  timeout = value;
}


void RPCClient::create(const char* src,
                                          FsPermission masked,
                                          const char* clientName,
                                          bool overwrite,
                                          bool createParent,
                                          short replication,
                                          long blockSize)
{
  CreateRequest request;
  request.build(src,masked,clientName,overwrite,createParent,replication,blockSize);
  writeToSocket(request);

  CreateResponse response(socket->read(bytesAvailable()));
  response.status();
}


void RPCClient::writeToSocket(const BaseRequest& request)
{
  if (send_header)
    {
      socket->write(request.getHeaderBytes());
      if (!socket->waitForBytesWritten())
        this->displayError(socket->error(),socket->errorString());
      socket->flush();
      send_header = false;
    }

  QByteArray data;
  if (send_authentication)
    {
      data.append(request.getProtocolBytes());
      send_authentication = false;
    }
  data.append(request.getPayloadBytes());
  socket->write(data);

  if (!socket->waitForBytesWritten())
    this->displayError(socket->error(),socket->errorString());
  socket->flush();
}


quint64 RPCClient::bytesAvailable()
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

QByteArray RPCClient::read(qint64 maxlen)
{
  return socket->read(maxlen);
}
