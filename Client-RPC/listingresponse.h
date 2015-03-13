#ifndef LISTINGRESPONSE_H
#define LISTINGRESPONSE_H
#include "baseresponse.h"
#include "directorylisting.h"

class Serializer;
class ListingResponse:public BaseResponse
{
public:
  ListingResponse();

  explicit ListingResponse(const QByteArray& bytes);

  ~ListingResponse();

  /**
   * @brief parse
   * @return DirectoryListing
   */
  DirectoryListing* parse();
private:
  HdfsFileStatus* readFields(Serializer& serializer);
};

#endif // LISTINGRESPONSE_H
