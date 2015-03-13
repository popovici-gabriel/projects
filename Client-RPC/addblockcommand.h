#ifndef ADDBLOCKCOMMAND_H
#define ADDBLOCKCOMMAND_H
#include "command.h"
#include "rpcclient.h"

class AddBlockCommand: public Command
{
public:
  AddBlockCommand();
  ~AddBlockCommand();
  BaseResponse* execute(BaseRequest* request,RPCClient*);
};

#endif // ADDBLOCKCOMMAND_H
