#include "createcommand.h"
#include "createresponse.h"

CreateCommand::CreateCommand()
{
}

CreateCommand::~CreateCommand()
{
}


BaseResponse*
CreateCommand::execute(BaseRequest* request,  RPCClient* socket)
{
  socket->writeToSocket(*request);
  CreateResponse response(socket->read(socket->bytesAvailable()));
  response.status();
  return NULL;
}
