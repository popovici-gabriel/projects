#ifndef DATANODEREGISTRATION_H
#define DATANODEREGISTRATION_H
#include"datanodeinfo.h"

class DatanodeRegistration:public DatanodeID
{
public:
  DatanodeRegistration();
  ~DatanodeRegistration();
  void write(QByteArray &);
  void readFields(QByteArray &, int &pos);
};

#endif // DATANODEREGISTRATION_H
