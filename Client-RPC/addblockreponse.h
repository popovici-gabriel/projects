#ifndef ADDBLOCKREPONSE_H
#define ADDBLOCKREPONSE_H
#include"baseresponse.h"
#include "locatedblock.h"
class AddBlockReponse:public BaseResponse
{
public:
  explicit AddBlockReponse(const QByteArray&);
  ~AddBlockReponse();
  LocatedBlock* parse();
};

#endif // ADDBLOCKREPONSE_H
