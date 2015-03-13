#ifndef CREATEREQUEST_H
#define CREATEREQUEST_H
#include "baserequest.h"
#include <QString>
#include "fspermission.h"

class CreateRequest:public BaseRequest
{
public:
  CreateRequest();
  void  build(const char* src,
                      FsPermission masked,
                      const char* clientName,
                      bool overwrite,
                      bool createParent,
                      short replication,
                      long blockSize);
};

#endif // CREATEREQUEST_H
