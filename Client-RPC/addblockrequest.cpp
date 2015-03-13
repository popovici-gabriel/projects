#include "addblockrequest.h"
#include "serializer.h"

AddBlockRequest::AddBlockRequest(QString src, QString clientName, vector<DatanodeInfo> excludedNodes)
  :BaseRequest()
{
  name = "ADD_BLOCK_REQUEST";
  method= "addBlock";
  args = 3;
  buildHeader();
  buildProtocol();
  buildPayload(src,clientName, excludedNodes);
}

AddBlockRequest::~AddBlockRequest()
{
}

void AddBlockRequest::buildPayload(QString src, QString clientName, vector<DatanodeInfo> excludedNodes)
{
  const int counter = 0;
  Serializer s;
  QByteArray buffer;
  s.writeInt(buffer,counter);
  s.writeString(buffer,method);
  s.writeInt(buffer,args);

  s.writeString(buffer,"java.lang.String");
  s.writeString(buffer,src);

  s.writeString(buffer,"java.lang.String");
  s.writeString(buffer,clientName);

   DatanodeInfo d;
  if (excludedNodes.empty())
    {
      s.writeString(buffer,"org.apache.hadoop.io.Writable");
      s.writeString(buffer,"org.apache.hadoop.io.ObjectWritable$NullInstance");
      s.writeString(buffer, "[L" + d.javaClassName() + ";");//type
    }
  else
    {
        s.writeString(buffer, "[L" + d.javaClassName() + ";");//type
        s.writeInt(buffer,excludedNodes.size());//length
        foreach (d , excludedNodes)
        {
          s.writeString(buffer,d.javaClassName());
          s.writeString(buffer,d.javaClassName());
          d.write(buffer);
        }
    }

  s.writeInt(payloadBytes,buffer.size());
  payloadBytes.append(buffer);
}
