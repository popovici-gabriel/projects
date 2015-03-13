#ifndef DIRECTORYLISTING_H
#define DIRECTORYLISTING_H
#include"hdfsfilestatus.h"
#include<QAtomicInt>
#include <iostream>
#include <vector>
using namespace std;

class DirectoryListing
{
public:
  DirectoryListing();

  ~DirectoryListing();

  /**
     * Check if there are more entries that are left to be listed
     * @return true if there are more entries that are left to be listed;
     *         return false otherwise.
     */
    inline bool hasMore()  const
    {
      return remainingEntries != 0;
    }

    /**
       * Get the number of remaining entries that are left to be listed
       * @return the number of remaining entries that are left to be listed
       */
      inline int getRemainingEntries()  const
      {
        return remainingEntries;
      }

      /**
         * Get the partial listing of file status
         * @return the partial listing of file status
         */
        const vector<HdfsFileStatus *>& getPartialListing() const
        {
          return partialListing;
        }

        std::string getLastName() const;
        void add(HdfsFileStatus* file);
        void print();
        int getTotal() const;
        void setTotal(int value);

private:
        int remainingEntries;
        vector<HdfsFileStatus* > partialListing;
  QAtomicInt count;
  int total;
};

#endif // DIRECTORYLISTING_H
