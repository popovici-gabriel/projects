#ifndef TOKEN_H
#define TOKEN_H
#include<QString>
#include "writable.h"
#include<vector>
using namespace std;

typedef signed char byte;
class Token : public Writable
{
public:  
  Token();
  void write(QByteArray &);
  void readFields(QByteArray &, int &pos);

  vector<byte> getIdentifier() const;
  void setIdentifier(const vector<byte> &value);

  vector<byte> getPassword() const;
  void setPassword(const vector<byte> &value);

  QString getService() const;
  void setService(const QString &value);

  QString getKind() const;
  void setKind(const QString &value);

  QString javaClassName();
private:
  vector<byte>  identifier;
  vector<byte> password;
  QString kind;
  QString service;
};

#endif // TOKEN_H
