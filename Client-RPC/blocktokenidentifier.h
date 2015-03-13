#ifndef BLOCKTOKENIDENTIFIER_H
#define BLOCKTOKENIDENTIFIER_H
#include<QString>
#include "writable.h"
#include<vector>
using namespace std;

class BlockTokenIdentifier: public Writable
{
public:
  typedef signed char byte;
  BlockTokenIdentifier();
  enum AccessMode
    {
      READ = 0 , WRITE = 1, COPY = 2, REPLACE =3, _NULL= 4
    };
  void readFields(QByteArray &, int &pos);
  void write(QByteArray &);
private:
    long expiryDate;
    int keyId;
    QString userId;
    vector<long> blockIds;
    vector<AccessMode> modes;
    byte  cache[];
};

#endif // BLOCKTOKENIDENTIFIER_H
