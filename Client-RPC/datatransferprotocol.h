#ifndef DATATRANSFERPROTOCOL_H
#define DATATRANSFERPROTOCOL_H

/**
 *
 * The Client transfers data to/from datanode using a streaming protocol.
 *
 */
typedef signed char byte;
class DataTransferProtocol
{
public:
  DataTransferProtocol();
  virtual ~DataTransferProtocol();

  /** Version for data transfers between clients and datanodes
     * This should change when serialization of DatanodeInfo, not just
     * when protocol changes. It is not very obvious.
     */
    /*
     * Version 18:
     *    Change the block packet ack protocol to include seqno,
     *    numberOfReplies, reply0, reply1, ...
     */
     static const int DATA_TRANSFER_VERSION;

    // Processed at datanode stream-handler
     static const byte OP_WRITE_BLOCK ;
     static const byte OP_READ_BLOCK;
    /**
     * @deprecated As of version 15, OP_READ_METADATA is no longer supported
     */
     static const byte OP_READ_METADATA ;
     static const byte OP_REPLACE_BLOCK ;
     static const byte OP_COPY_BLOCK ;
     static const byte OP_BLOCK_CHECKSUM;

     static const int OP_STATUS_SUCCESS;
     static const int OP_STATUS_ERROR;
     static const int OP_STATUS_ERROR_CHECKSUM ;
     static const int OP_STATUS_ERROR_INVALID;
     static const int OP_STATUS_ERROR_EXISTS;
     static const int OP_STATUS_ERROR_ACCESS_TOKEN;
     static const int OP_STATUS_CHECKSUM_OK;



};

#endif // DATATRANSFERPROTOCOL_H
