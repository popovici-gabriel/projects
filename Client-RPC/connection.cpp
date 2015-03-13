#include "connection.h"

Connection::Connection(const QString& hostname)
{
  RPCClient *socket = new RPCClient;
  cache[hostname] = socket;
}

Connection::~Connection()
{
  std::map<QString , RPCClient *>::iterator it;
  for (it = cache.begin(); it!= cache.end(); ++it)
    {
      delete it->second;
    }
  cache.clear();
}

RPCClient*
Connection::getConnection(const QString hostname)
{
  return cache[hostname];
}
