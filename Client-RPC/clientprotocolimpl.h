#ifndef CLIENTPROTOCOLIMPL_H
#define CLIENTPROTOCOLIMPL_H
#include"clientprotocol.h"
#include"directorylisting.h"
#include "fspermission.h"
#include "connection.h"

/**
   * ClientProtocol implemantion using sockets;
   * @brief The ClientProtocolImpl class
   */
class ClientProtocolImpl: public ClientProtocol
{
public:
   ClientProtocolImpl();
  ~ClientProtocolImpl();
  void create(const char* src,
                        FsPermission masked,
                        const char* clientName,
                        bool overwrite,
                        bool createParent,
                        short replication,
                        long blockSize);
  DirectoryListing*  getListing(const char* src,  const char* startAfter);
  LocatedBlock* addBlock(QString src, QString clientName, vector<DatanodeInfo> excludedNodes);
  static ClientProtocol* getInstance();
private: 
  static Connection *connection;
};

#endif // CLIENTPROTOCOLIMPL_H
