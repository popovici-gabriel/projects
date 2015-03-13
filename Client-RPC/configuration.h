#ifndef CONFIGURATION_H
#define CONFIGURATION_H
#include<QString>

class Configuration
{
public:
  Configuration();

  static const QString readHost()
  {
    return HOST;
  }

   static int readPort()
  {
    return PORT;
  }

   static const char* readHeader()
   {
     return HEADER;
   }

   static char readVersion()
   {
     return CURRENT_VERSION;
   }

   static char readAuthentication()
   {
     return SIMPLE;
   }
   static const char* readProtocl()
   {
     return PROTOCOL;
   }
   static const char* readUser()
   {
     return USER;
   }
   static const char* readMethod()
   {
     return METHOD;
   }
   static int readMethodArgs()
    {
     return ARGS_LENGTH;
   }
private:
  // socket
  const static QString  HOST;
  const static int PORT;
  //RPC header IPC
  const  static char*  HEADER;
  const  static char  CURRENT_VERSION;
  const  static char  SIMPLE;
  // RPC header protocol
  const  static char*  PROTOCOL;
  const  static char*  METHOD;
  const  static int  ARGS_LENGTH;
  /** modified JDK UTF-8 version format */
  const  static char* USER;
};

#endif // CONFIGURATION_H
