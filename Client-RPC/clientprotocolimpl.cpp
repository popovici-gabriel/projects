#include "clientprotocolimpl.h"
#include "rpcclient.h"
#include "addblockcommand.h"
#include "command.h"
#include "addblockrequest.h"
#include "addblockreponse.h"

ClientProtocolImpl::ClientProtocolImpl():
  ClientProtocol()
{
}

ClientProtocolImpl::~ClientProtocolImpl()
{
  delete connection;
  connection = NULL;
}

void ClientProtocolImpl::create(const char* src,
                                                          FsPermission masked,
                                                         const char* clientName,
                                                         bool overwrite,
                                                         bool createParent,
                                                         short replication,
                                                         long blockSize)
{
  if (connection->getConnection()->isConnected())
    {
      qDebug() << "About to create entry: " << src;
      connection->getConnection()->create(src,masked,clientName,overwrite,createParent,replication,blockSize);
    }
}

DirectoryListing* ClientProtocolImpl::getListing(const char* src,  const char* startAfter)
{
  if (connection->getConnection()->isConnected())
    {
      qDebug() << "About to list dir: " << src;
      return connection->getConnection()->getListing(src,startAfter);
    }

  return NULL;
}


LocatedBlock*
ClientProtocolImpl::addBlock(QString src, QString clientName, vector<DatanodeInfo> excludedNodes)
{
  qDebug() << "About to call addBlock  for: " << src;
  AddBlockRequest request (src,clientName,excludedNodes);
  connection->getConnection()->writeToSocket(request);

  AddBlockReponse response (connection->getConnection()->read(connection->getConnection()->bytesAvailable()));
  if (response.status())
      return response.parse();
  return NULL;
}

Connection*
ClientProtocolImpl::connection = NULL;

ClientProtocol*
ClientProtocolImpl::getInstance()
{
  if (NULL == connection)
    connection = new Connection(Configuration::readHost());

  RPCClient* socket = connection->getConnection();
  socket->connect();
  if (socket->isConnected())
    return new ClientProtocolImpl;
  return NULL;
}
