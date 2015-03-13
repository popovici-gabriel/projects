#include "listingresponse.h"
#include "serializer.h"
#include<iostream>
#include<QDebug>
using namespace std;

static const char * EMPTY_RESPONSE ="org.apache.hadoop.io.ObjectWritable$NullInstance";

ListingResponse::ListingResponse()
{
}

ListingResponse::~ListingResponse()
{
}

ListingResponse::ListingResponse(const QByteArray &bytes)
  :BaseResponse(bytes)
{
}

DirectoryListing*  ListingResponse::parse()
{
  Serializer serializer;
  char* className = serializer.readString(data,pos);
  char* returnType = serializer.readString(data,pos);
  int numEntries =serializer.readInt(this->data,this->pos);

  qDebug() << "RPC class name: " << className;
  qDebug() << "RPC return type:" << returnType;

  cout <<"Found: "<<numEntries<<" entries."<<endl;

  if (0 == numEntries || 0 == qstrcmp(returnType,EMPTY_RESPONSE))
    {
      cout <<"No entries found"<<endl;
      delete[] className;
      delete[] returnType;

       return NULL;
    }


  DirectoryListing *dir = new DirectoryListing;
  dir->setTotal(numEntries);

  for (int var = 0; var < numEntries; ++var)
    {
      HdfsFileStatus* file = readFields(serializer);

      if (0 != file)
          dir->add(file);
    }

  this->remainingEntries = serializer.readInt(data,pos);
  delete[] className;
  delete[] returnType;

  return dir;
}

/**
byte[] lastReturnedName = HdfsFileStatus.EMPTY_NAME;
      DirectoryListing thisListing;
      if (showFiles) {
        out.println(path + " <dir>");
      }
      res.totalDirs++;
      do {
        assert lastReturnedName != null;
        thisListing = namenode.getListing(path, lastReturnedName);
        if (thisListing == null) {
          return;
        }
        HdfsFileStatus[] files = thisListing.getPartialListing();
        for (int i = 0; i < files.length; i++) {
          check(path, files[i], res);
        }
        lastReturnedName = thisListing.getLastName();
      } while (thisListing.hasMore());
*/


HdfsFileStatus* ListingResponse::readFields(Serializer& serializer)
{
  int numOfBytes = serializer.readInt(data,pos);

  if (0 != numOfBytes)
    {
        char* path = serializer.readFully(numOfBytes, data,pos);
        long len = serializer.readLong(8,data,pos);
        bool isdir = serializer.readBoolean(data,pos);
        short block_replication = serializer.readShort(data,pos);
        long blocksize = serializer.readLong(8,data,pos);
        long modification_time = serializer.readLong(8,data,pos);
        long access_time = serializer.readLong(8,data,pos);
        short permission = serializer.readShort(data,pos);
        char* owner = serializer.read0StringCompression(data,pos);
        char* group = serializer.read0StringCompression(data,pos);

        HdfsFileStatus* file = new HdfsFileStatus();
        file->setPath(path);
        file->setLength(len);
        file->setIsdir(isdir);
        file->setBlock_replication(block_replication);
        file->setBlocksize(blocksize);
        file->setModification_time(modification_time);
        file->setAccess_time(access_time);
        file->setPermission(permission);
        file->setOwner(owner);
        file->setGroup(group);
        return file;
    }
  return NULL;
}

