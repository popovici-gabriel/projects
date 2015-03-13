#include "hdfsfilestatus.h"
#include<QString>

HdfsFileStatus::HdfsFileStatus()
{
}

char* HdfsFileStatus::EMPTY_NAME = {0};

HdfsFileStatus::~HdfsFileStatus()
{
}

HdfsFileStatus::HdfsFileStatus(const HdfsFileStatus& file):
  path(file.path),
  length(file.length),
  isdir(file.isdir),
  block_replication(file.block_replication),
  blocksize(file.blocksize),
  modification_time(file.modification_time),
  access_time(file.access_time),
  permission(file.permission),
  owner(file.owner),
  group(file.group)
{
}

void HdfsFileStatus::print()
{

  QString type = isdir ? "Dir":"File";
  cout<< type.toStdString()<<":" << path<<":"<<length<<" bytes :"<<blocksize<<" bytes :"<<owner<<":"<<group<<endl;
}


string HdfsFileStatus::getPath() const
{
  return path;
}

void HdfsFileStatus::setPath(const string &value)
{
  path = value;
}

string HdfsFileStatus::getGroup() const
{
  return group;
}

void HdfsFileStatus::setGroup(const string &value)
{
  group = value;
}

string HdfsFileStatus::getOwner() const
{
  return owner;
}

void HdfsFileStatus::setOwner(const string &value)
{
  owner = value;
}

short HdfsFileStatus::getPermission() const
{
  return permission;
}

void HdfsFileStatus::setPermission(short value)
{
  permission = value;
}

long HdfsFileStatus::getAccess_time() const
{
  return access_time;
}

void HdfsFileStatus::setAccess_time(long value)
{
  access_time = value;
}

long HdfsFileStatus::getModification_time() const
{
  return modification_time;
}

void HdfsFileStatus::setModification_time(long value)
{
  modification_time = value;
}

long HdfsFileStatus::getBlocksize() const
{
  return blocksize;
}

void HdfsFileStatus::setBlocksize(long value)
{
  blocksize = value;
}

short HdfsFileStatus::getBlock_replication() const
{
  return block_replication;
}

void HdfsFileStatus::setBlock_replication(short value)
{
  block_replication = value;
}

bool HdfsFileStatus::getIsdir() const
{
  return isdir;
}

void HdfsFileStatus::setIsdir(bool value)
{
  isdir = value;
}

long HdfsFileStatus::getLength() const
{
  return length;
}

void HdfsFileStatus::setLength(long value)
{
  length = value;
}
