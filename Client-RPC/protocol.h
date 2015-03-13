#ifndef PROTOCOL_H
#define PROTOCOL_H
class BaseResponse;
class BaseRequest;
class Protocol
{
public:
  Protocol();
  virtual const BaseResponse& readRequest() = 0;
  virtual void writeRequest(const BaseRequest&) =0;
};

#endif // PROTOCOL_H
