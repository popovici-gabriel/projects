#include "datanodeinfo.h"
#include "serializer.h"
#include<QDebug>

const DatanodeID DatanodeID::EMPTY_ARRAY[] = {};

DatanodeID::DatanodeID():
  name(""),storageID(""),infoPort(-1),ipcPort(-1)
{
}

DatanodeID::DatanodeID(QString nodename):
  name(nodename),storageID(""),infoPort(-1),ipcPort(-1)
{
}

DatanodeID::DatanodeID(QString nodeName, QString storageID, int infoPort, int ipcPort)
{
  this->name = nodeName;
  this->storageID = storageID;
  this->infoPort = infoPort;
  this->ipcPort = ipcPort;
}

DatanodeID::DatanodeID(const DatanodeID& id)
{
  this->name =id.name;
  this->storageID = id.storageID;
  this->infoPort = id.infoPort;
  this->ipcPort = id.ipcPort;
}
DatanodeID::~DatanodeID(){}

QString
DatanodeID::javaClassName()
{
  return "org.apache.hadoop.hdfs.protocol.DatanodeID";
}

DatanodeID & DatanodeID::operator= (const DatanodeID & other)
{
   if (this != &other)
     {
       this->name =other.name;
       this->storageID = other.storageID;
       this->infoPort = other.infoPort;
       this->ipcPort = other.ipcPort;
     }

  return *this;
}

QString DatanodeID::getName() const
{
  return name;
}

void DatanodeID::setName(const QString &value)
{
  name = value;
}

int DatanodeID::getInfoPort() const
{
  return infoPort;
}

void DatanodeID::setInfoPort(int value)
{
  infoPort = value;
}

QString DatanodeID::getStorageID() const
{
  return storageID;
}

void DatanodeID::setStorageID(const QString &value)
{
  storageID = value;
}

void DatanodeID::write(QByteArray& byte)
{
  Serializer s;
  s.writeString(byte,name);
  s.writeString(byte,storageID);
  s.writeShort(byte,infoPort);
}

void DatanodeID::readFields(QByteArray &data, int &pos)
{
  Serializer s;
  this->name = QString::fromStdString(std::string(s.readString(data,pos)));
  this->storageID = QString::fromStdString(std::string(s.readString(data,pos)));
  this->infoPort  = s.readShort(data,pos) & 0x0000ffff;
}

DatanodeInfo::DatanodeInfo()
  : DatanodeID(),
    adminState(NORMAL)
{
}

DatanodeInfo::DatanodeInfo(DatanodeID nodeID):
  DatanodeID(nodeID),capacity(0l), dfsUsed(0l), remaining(0l), lastUpdate(0l),xceiverCount(0),adminState(NORMAL)
{
}

DatanodeInfo::DatanodeInfo(DatanodeID nodeID,QString location, QString hostname):
  DatanodeID(nodeID),location(location),hostName(hostname)
{

}

DatanodeInfo::~DatanodeInfo(){}


void DatanodeInfo::readFields(QByteArray &byte, int &pos)
{
    Serializer  s;
    DatanodeID::readFields(byte,pos);
    DatanodeID::ipcPort = s.readShort(byte,pos) & 0x0000ffff;
    capacity = s.readLong(8,byte,pos);
    dfsUsed = s.readLong(8,byte,pos);
    remaining = s.readLong(8,byte,pos);
    lastUpdate = s.readLong(8,byte,pos);
    xceiverCount = s.readInt(byte,pos);
    location = QString::fromStdString(std::string(s.read0StringCompression(byte,pos)));
    hostName = QString::fromStdString(std::string(s.read0StringCompression(byte,pos)));
    const char * enumAdminStates  = s.read0StringCompression(byte,pos);
    if (strcmp("NORMAL",enumAdminStates))
      adminState = NORMAL;
    else if (strcmp("DECOMMISSION_INPROGRESS",enumAdminStates))
      adminState = DECOMMISSION_INPROGRESS;
    else if (strcmp("DECOMMISSIONED",enumAdminStates))
      adminState = DECOMMISSIONED;
}

void DatanodeInfo::write(QByteArray & byte)
{
  Serializer  s;
  DatanodeID::write(byte);
  s.writeShort(byte,ipcPort);
  s.writeLong(byte,capacity);
  s.writeLong(byte,dfsUsed);
  s.writeLong(byte,remaining);
  s.writeLong(byte,lastUpdate);
  s.writeInt(byte,xceiverCount);
  s.writeTextString(byte,location);
  s.writeTextString(byte,hostName);
  QString enumString;
  switch (adminState)
    {
    case NORMAL:
      enumString = "NORMAL";
      break;
    case DECOMMISSIONED:
      enumString = "DECOMMISSIONED";
      break;
    case DECOMMISSION_INPROGRESS:
      enumString = "DECOMMISSION_INPROGRESS";
      break;
    default:
      break;
    }
  s.writeTextString(byte,enumString);
}

QString
DatanodeInfo::javaClassName()
{
  return "org.apache.hadoop.hdfs.protocol.DatanodeInfo";
}


void
DatanodeInfo::toString()
{
  qDebug() <<"[DatanodeInfo: ";
  qDebug() <<"Name: " <<name;
  qDebug() << "StorageID: " << storageID;
  qDebug() << "Location: "<< location;
  qDebug() << "Host: "<<hostName;
  qDebug() <<"]";
}
