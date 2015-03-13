#ifndef DATANODEINFO_H
#define DATANODEINFO_H
#include<QString>
#include "writable.h"

class DatanodeID:public Writable
{
public:
   DatanodeID();
   DatanodeID(QString nodeName);
   DatanodeID(QString nodeName, QString storageID, int infoPort, int ipcPort);
   DatanodeID(const DatanodeID&);
   static const DatanodeID EMPTY_ARRAY[];
   virtual ~DatanodeID();
   DatanodeID & operator= (const DatanodeID & other);
   QString name;       // hostname:port (data transfer port)
   QString storageID;  // unique per cluster storageID
   int infoPort;   // info server port
   int ipcPort;       // ipc server port
   QString getName() const;
   void setName(const QString &value);
   int getInfoPort() const;
   void setInfoPort(int value);
   QString getStorageID() const;
   void setStorageID(const QString &value);
   void write(QByteArray&);
   void readFields(QByteArray &, int& pos);
   QString javaClassName();
};

class DatanodeInfo: public DatanodeID
{
public:
  // administrative states of a datanode
  enum AdminStates {NORMAL, DECOMMISSION_INPROGRESS, DECOMMISSIONED };
  DatanodeInfo();
  DatanodeInfo(DatanodeID nodeID);
  DatanodeInfo(DatanodeID nodeID,QString location, QString hostname);
  virtual ~DatanodeInfo();
  //DatanodeInfo(const DatanodeInfo& );
  void write(QByteArray &);
  void readFields(QByteArray &, int &pos);
  QString javaClassName();
  void toString();
protected:
  qint64 capacity;
  qint64 dfsUsed;
  qint64 remaining;
  qint64 lastUpdate;
  int xceiverCount;
  QString location;

  /** HostName as suplied by the datanode during registration as its
      * name. Namenode uses datanode IP address as the name.
      */
  QString hostName;
  AdminStates adminState;
};

#endif // DATANODEINFO_H
