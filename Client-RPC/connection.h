#ifndef CONNECTION_H
#define CONNECTION_H
#include <QString>
#include<iostream>
#include<map>
#include "rpcclient.h"
#include "configuration.h"

class Connection
{
public:
  Connection(const QString& hostname);
  ~Connection();
  RPCClient* getConnection(const QString = Configuration::readHost());
  void addConnection();
private:
  std::map<QString , RPCClient *> cache;
};

#endif // CONNECTION_H
