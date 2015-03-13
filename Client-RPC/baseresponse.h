#ifndef BASERESPONSE_H
#define BASERESPONSE_H
#include<QByteArray>
class BaseResponse
{
public:
  enum STATUS
  {
      SUCCESS  = 0,
      ERROR  = 1,
      FATAL = -1
  };

  BaseResponse();
  explicit BaseResponse(const QByteArray& bytes);
  virtual ~BaseResponse();
  bool status();
  int hasRemainingEntries() const ;
protected:
  QByteArray data;
  int pos;
  int remainingEntries;
  /**
   * @brief readHeader reads type of data returned by server
   */
  virtual void readHeader();
private:
  void readError();
};

#endif // BASERESPONSE_H
