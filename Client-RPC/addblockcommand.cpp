#include "addblockcommand.h"
#include"addblockreponse.h"

AddBlockCommand::AddBlockCommand()
{
}

AddBlockCommand::~AddBlockCommand()
{
}

BaseResponse* AddBlockCommand::execute(BaseRequest* request, RPCClient* socket)
{
  socket->writeToSocket(*request);
  AddBlockReponse *response = new AddBlockReponse(socket->read(socket->bytesAvailable()));
  return response;
}
