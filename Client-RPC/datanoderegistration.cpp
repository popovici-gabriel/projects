#include "datanoderegistration.h"

DatanodeRegistration::DatanodeRegistration()
{
}

DatanodeRegistration::~DatanodeRegistration()
{

}

void
DatanodeRegistration::readFields(QByteArray &in, int &pos)
{
  DatanodeID::readFields(in,pos);
}

void
DatanodeRegistration::write(QByteArray &out)
{
  DatanodeID::write(out);
}
