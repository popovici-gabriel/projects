#ifndef CREATECOMMAND_H
#define CREATECOMMAND_H
#include "command.h"

class CreateCommand : public Command
{
public:
  CreateCommand();
  ~CreateCommand();
  BaseResponse* execute(BaseRequest* request,RPCClient*);
};

#endif // CREATECOMMAND_H
