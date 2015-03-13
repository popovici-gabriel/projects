#ifndef CREATERESPONSE_H
#define CREATERESPONSE_H
#include "baseresponse.h"

class CreateResponse:public BaseResponse
{
public:
  CreateResponse();

  explicit CreateResponse(const QByteArray& bytes);

  ~CreateResponse();

};

#endif // CREATERESPONSE_H
