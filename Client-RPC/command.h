#ifndef COMMAND_H
#define COMMAND_H
#include "baserequest.h"
#include "baseresponse.h"
#include "rpcclient.h"

/**
   * @brief The Command class
   * @see Command Pattern a.k.a one class per command handler
  */
class Command
{
public:
  Command();
  virtual ~Command();
  virtual BaseResponse* execute(BaseRequest* request, RPCClient*) =0;
};

#endif // COMMAND_H
