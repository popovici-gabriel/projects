#ifndef RPCCLIENT_H
#define RPCCLIENT_H
#include <QObject>
#include<QTcpSocket>
#include "baserequest.h"
#include<unistd.h>
#include "fspermission.h"

class DirectoryListing;
class RPCClient : public QObject
{
  Q_OBJECT
public:
  explicit RPCClient(const int TIMEOUT = 3 *1000 , QObject *parent = 0);
  void connect();
  virtual ~RPCClient();
  void list(const char* src,  const char* startAfter);
  DirectoryListing * getListing(const char* src,  const char* startAfter);
  int getTimeout() const;
  void setTimeout(int value);
  void create(const char* src,
                        FsPermission masked,
                        const char* clientName,
                        bool overwrite,
                        bool createParent,
                        short replication,
                        long blockSize);
  bool isConnected() const;
  void writeToSocket(const BaseRequest& request);
signals:
public slots:
  void  displayError(int socketError, const QString &message);
  void close();
  void readyRead();
  QByteArray read(qint64 maxlen);
  quint64 bytesAvailable();
private:
  int waitForInput() const;
  QTcpSocket* socket;
  bool connected;
  int timeout;
  bool send_header;
  bool send_authentication;
};

#endif // RPCCLIENT_H
