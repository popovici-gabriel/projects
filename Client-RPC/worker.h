#ifndef WORKER_H
#define WORKER_H
#include "command.h"

class RPCClient;
class Worker
{
public:
  Worker();
  /**
   * @brief handleRequest
   * @brief RPCClient socket
   */
  void handleRequest( const BaseRequest&, const RPCClient*);
private:
};

#endif // WORKER_H
