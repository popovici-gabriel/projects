#include "directorylisting.h"

DirectoryListing::DirectoryListing() :remainingEntries(0),count(0),total(0)
{
}

DirectoryListing::~DirectoryListing()
{
  foreach (HdfsFileStatus* file, partialListing)
    {
      delete file;
    }
}

string DirectoryListing::getLastName() const
{
  if (0 == partialListing.size())
    {
      return HdfsFileStatus::EMPTY_NAME;
    }

  return partialListing[partialListing.size()-1]->getLocalNameInBytes();
}

void DirectoryListing::add(HdfsFileStatus* file)
{
  partialListing.push_back(file);
  count.ref();
}

void DirectoryListing::print()
{
  int var = 0;
  foreach (HdfsFileStatus * item, partialListing)
    {
      cout<< "Reading entry:" << (1+var) <<endl;
      item->print();
      cout<< "#######"<<endl;
      var++;
    }
}

int DirectoryListing::getTotal() const
{
  return total;
}

void DirectoryListing::setTotal(int value)
{
  total = value;
}
