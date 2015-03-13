#ifndef CLIENTPROTOCOL_H
#define CLIENTPROTOCOL_H
#include "configuration.h"
#include <vector>
using namespace std;

class DirectoryListing;
class FsPermission;
#include "locatedblock.h"
class DatanodeInfo;

/**********************************************************************
 * ClientProtocol is used by user code via
 * {@link org.apache.hadoop.hdfs.DistributedFileSystem} class to communicate
 * with the NameNode.  User code can manipulate the directory namespace,
 * as well as open/close file streams, etc.
 *
 **********************************************************************/
class ClientProtocol
{
public:
  ClientProtocol();

  virtual ~ClientProtocol();

  /**
   * Create a new file entry in the namespace.
   * <p>
   * This will create an empty file specified by the source path.
   * The path should reflect a full path originated at the root.
   * The name-node does not have a notion of "current" directory for a client.
   * <p>
   * Once created, the file is visible and available for read to other clients.
   * Although, other clients cannot {@link #delete(String)}, re-create or
   * {@link #rename(String, String)} it until the file is completed
   * or explicitly as a result of lease expiration.
   * <p>
   * Blocks have a maximum size.  Clients that intend to
   * create multi-block files must also use {@link #addBlock(String, String)}.
   *
   * @param src path of the file being created.
   * @param masked masked permission.
   * @param clientName name of the current client.
   * @param overwrite indicates whether the file should be
   * overwritten if it already exists.
   * @param createParent create missing parent directory if true
   * @param replication block replication factor.
   * @param blockSize maximum block size.
   *
   * @throws AccessControlException if permission to create file is
   * denied by the system. As usually on the client side the exception will
   * be wrapped into {@link org.apache.hadoop.ipc.RemoteException}.
   * @throws QuotaExceededException if the file creation violates
   *                                any quota restriction
   * @throws IOException if other errors occur.
   */
  virtual void create(const char* src,
                                    FsPermission masked,
                                    const char* clientName,
                                    bool overwrite,
                                    bool createParent,
                                    short replication,
                                    long blockSize) = 0 ;
  /**
     * Get a partial listing of the indicated directory
     *
     * @param src the directory name
     * @param startAfter the name of the last entry received by the client
     * @return a partial listing starting after startAfter or NULL
     */
  virtual DirectoryListing* getListing(const char* src,
                                                            const char* startAfter)  = 0 ;

  /**
     * A client that wants to write an additional block to the
     * indicated filename (which must currently be open for writing)
     * should call addBlock().
     *
     * addBlock() allocates a new block and datanodes the block data
     * should be replicated to.
     *
     * @param excludedNodes a list of nodes that should not be allocated
     *
     * @return LocatedBlock allocated block information.
     */
    virtual LocatedBlock* addBlock(QString src,
                                                         QString clientName,
                                                         vector<DatanodeInfo> excludedNodes) = 0;
};

#endif // CLIENTPROTOCOL_H
