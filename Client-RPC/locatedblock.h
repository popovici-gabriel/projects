#ifndef LOCATEDBLOCK_H
#define LOCATEDBLOCK_H

#include "writable.h"
#include "block.h"
#include "datanodeinfo.h"
#include "token.h"
#include <vector>
using namespace std;

class LocatedBlock: public Writable
{
public:
  LocatedBlock();
  void readFields(QByteArray &, int &pos);
  void write(QByteArray &);
  void toString();
  Block getB() const;
  void setB(const Block &value);
  vector<DatanodeInfo> getLocs() const;
  void setLocs(const vector<DatanodeInfo> &value);
private:
  Block b;
  long offset;  // offset of the first byte of the block in the file
  vector<DatanodeInfo>  locs;
  // corrupt flag is true if all of the replicas of a block are corrupt.
  // else false. If block has few corrupt replicas, they are filtered and
  // their locations are not part of this object
  bool corrupt;
  Token blockToken;
  QString javaClassName();
};

#endif // LOCATEDBLOCK_H
