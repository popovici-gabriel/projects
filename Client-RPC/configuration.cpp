#include "configuration.h"

Configuration::Configuration()
{
}

//SOCKET
//const QString  Configuration::HOST ="127.0.0.1";
//const QString  Configuration::HOST ="10.105.2.143";
const QString  Configuration::HOST ="172.28.35.152";
//const int Configuration::PORT =9000;
//const int Configuration::PORT = 46593;
const int Configuration::PORT = 8020;

//RPC
const  char* Configuration:: HEADER =  "hrpc";
const  char  Configuration::CURRENT_VERSION = 4;
const  char  Configuration::SIMPLE = 80;
// RPC header protocol
const  char*  Configuration::PROTOCOL =  "org.apache.hadoop.ipc.TestRPC$TestProtocol";
const  char*  Configuration::METHOD = "getProtocolVersion";
const  int  Configuration::ARGS_LENGTH = 2;
//const  char* Configuration::USER = "root";
//const  char* Configuration::USER = "gabriel";
const  char* Configuration::USER = "mclaw";
