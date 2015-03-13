#ifndef BLOCK_H
#define BLOCK_H
#include "writable.h"

/**************************************************
 * A Block is a Hadoop FS primitive, identified by a
 * long.
 *
 **************************************************/
class Block: public Writable
{
public:
  Block();
  void readFields(QByteArray &, int &pos);
  void write(QByteArray &);
  QString javaClassName();
  void toString();
  qint64 getBlockId() const;
  void setBlockId(const qint64 &value);

  qint64 getNumBytes() const;
  void setNumBytes(const qint64 &value);

  qint64 getGenerationStamp() const;
  void setGenerationStamp(const qint64 &value);

private:
  qint64 blockId;
  qint64 numBytes;
  qint64 generationStamp;

};

#endif // BLOCK_H
