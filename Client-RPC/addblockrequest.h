#ifndef ADDBLOCKREQUEST_H
#define ADDBLOCKREQUEST_H
#include"baserequest.h"
#include <vector>
#include "datanodeinfo.h"
using namespace std;

class AddBlockRequest:public BaseRequest
{
public:
  AddBlockRequest(QString src, QString clientName, vector<DatanodeInfo> excludedNodes);
  ~AddBlockRequest();
private :
  void buildPayload(QString src, QString clientName, vector<DatanodeInfo> excludedNodes);
};

#endif // ADDBLOCKREQUEST_H
