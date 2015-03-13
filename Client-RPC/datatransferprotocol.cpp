#include "datatransferprotocol.h"


/** Version for data transfers between clients and datanodes
   * This should change when serialization of DatanodeInfo, not just
   * when protocol changes. It is not very obvious.
   */
  /*
   * Version 18:
   *    Change the block packet ack protocol to include seqno,
   *    numberOfReplies, reply0, reply1, ...
   */
    const int DataTransferProtocol::DATA_TRANSFER_VERSION = 17;

  // Processed at datanode stream-handler
    const byte DataTransferProtocol::OP_WRITE_BLOCK = (byte) 80;
    const byte DataTransferProtocol::OP_READ_BLOCK = (byte) 81;
  /**
   * @deprecated As of version 15, OP_READ_METADATA is no longer supported
   */
   const byte DataTransferProtocol::OP_READ_METADATA = (byte) 82;
    const byte DataTransferProtocol::OP_REPLACE_BLOCK = (byte) 83;
    const byte DataTransferProtocol::OP_COPY_BLOCK = (byte) 84;
    const byte DataTransferProtocol::OP_BLOCK_CHECKSUM = (byte) 85;

    const int DataTransferProtocol::OP_STATUS_SUCCESS = 0;
    const int DataTransferProtocol::OP_STATUS_ERROR = 1;
    const int DataTransferProtocol::OP_STATUS_ERROR_CHECKSUM = 2;
    const int DataTransferProtocol::OP_STATUS_ERROR_INVALID = 3;
    const int DataTransferProtocol::OP_STATUS_ERROR_EXISTS = 4;
    const int DataTransferProtocol::OP_STATUS_ERROR_ACCESS_TOKEN = 5;
    const int DataTransferProtocol::OP_STATUS_CHECKSUM_OK = 6;

DataTransferProtocol::DataTransferProtocol()
{
}
DataTransferProtocol::~ DataTransferProtocol()
{
}
