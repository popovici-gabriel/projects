#ifndef WRITEFILE_H
#define WRITEFILE_H
#include "packet.h"
#include<QString>
#include <istream>
#include <string>
#include "clientprotocol.h"
using namespace std;

static const bool OK = true;
static const bool ERROR = false;
static string root ="/";

class WriteFile
{
public:
  explicit WriteFile(ClientProtocol * p,
                              string absFileName,
                              quint64  bloksize  = DFS_BLOCK_SIZE);
  virtual ~WriteFile();
  void write();
  void setReplication(short r);
  void setBlockSize(quint64 b);
  void setHdfsLocation(string root);
private:
  bool write(QByteArray& input);
  short replication;
  void createFile();
  bool created;
  ClientProtocol *protocol;
  QByteArray input;
  void readFile();
  quint64 read(quint64 offset, quint64 remain);
  quint64 blockSize;
  quint64 remain;
  string fileAbsName;
  string fileName;
  string hdfsFileName;
  string root;
  quint64 currentSeqno;
};

#endif // WRITEFILE_H
