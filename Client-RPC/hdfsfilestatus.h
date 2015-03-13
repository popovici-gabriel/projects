#ifndef HDFSFILESTATUS_H
#define HDFSFILESTATUS_H
#include<iostream>
using namespace std;

class HdfsFileStatus
{
public:
  HdfsFileStatus();
  ~HdfsFileStatus();
  HdfsFileStatus(const HdfsFileStatus& file);
  static char* EMPTY_NAME;
  inline string getLocalNameInBytes()  const
  {
    return path;
  }
  void print();

  string getPath() const;
  void setPath(const string &value);

  long getLength() const;
  void setLength(long value);

  bool getIsdir() const;
  void setIsdir(bool value);

  short getBlock_replication() const;
  void setBlock_replication(short value);

  long getBlocksize() const;
  void setBlocksize(long value);

  long getModification_time() const;
  void setModification_time(long value);

  long getAccess_time() const;
  void setAccess_time(long value);

  short getPermission() const;
  void setPermission(short value);

  string getOwner() const;
  void setOwner(const string &value);

  string getGroup() const;
  void setGroup(const string &value);
private:
   string  path;  // local name of the inode that's encoded in java UTF8
   long length;
   bool isdir;
   short block_replication;
   long blocksize;
   long modification_time;
   long access_time;
   short permission;
   string owner;
   string group;
};

#endif // HDFSFILESTATUS_H
