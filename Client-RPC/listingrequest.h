#ifndef LISTSING_H
#define LISTSING_H
#include<QString>
#include "baserequest.h"

class ListingRequest:public BaseRequest
{
public:
  ListingRequest();
  ~ListingRequest();
  void build(const char* src,  const char* startAfter);
private:
  QString method;
  int args;
  QString dirArg;
  void buildPayload(const char* src,  const char* startAfter);
};

#endif // LISTSING_H
