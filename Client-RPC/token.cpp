#include "token.h"
#include "serializer.h"

Token::Token()
{
}

void Token::write(QByteArray &data)
{
  Serializer s;
  s.writeVInt(data,identifier.size());
  for (uint var = 0; var < identifier.size(); ++var) {
      data.append(identifier[var]);
    }
  s.writeVInt(data,password.size());
  for (uint var = 0; var < password.size(); ++var) {
      data.append(password[var]);
    }
  //Z-compressed
   s.writeVInt(data,kind.length());
   data.append(kind.toUtf8());
   s.writeVInt(data,service.length());
   data.append(service.toUtf8());
}

void Token::readFields(QByteArray &data, int &pos)
{
  Serializer s;
  int len = s.readVInt(data,pos);
  identifier = vector<byte>(len);
  for (int var = 0; var < len; ++var) {
      identifier[var] = data[pos++];
    }
  len = s.readVInt(data,pos);
  password = vector<byte>(len);
  for (int var = 0; var < len; ++var) {
      password[var] = data[pos++];
    }
  len  = s.readVInt(data,pos);
  kind = QString::fromStdString(std::string(s.readFully(len,data,pos)));
  len  = s.readVInt(data,pos);
  service = QString::fromStdString(std::string(s.readFully(len,data,pos)));
}

vector<byte> Token::getPassword() const
{
  return password;
}

void Token::setPassword(const vector<byte> &value)
{
  password = value;
}

vector<byte> Token::getIdentifier() const
{
  return identifier;
}

void Token::setIdentifier(const vector<byte> &value)
{
  identifier = value;
}

QString Token::getService() const
{
  return service;
}

void Token::setService(const QString &value)
{
  service = value;
}

QString Token::getKind() const
{
  return kind;
}

void Token::setKind(const QString &value)
{
  kind = value;
}

QString
Token::javaClassName()
{
  return "org.apache.hadoop.security.token.Token";
}
