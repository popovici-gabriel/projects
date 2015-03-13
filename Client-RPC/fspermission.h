#ifndef FSPERMISSION_H
#define FSPERMISSION_H

class FsPermission
{
public:
  enum Permission
  {
      NONE,
      EXECUTE,
      WRITE,
      WRITE_EXECUTE,
      READ,
      READ_EXECUTE,
      READ_WRITE,
      ALL
  };
  static const char* FsAction[];
  static const char* className;
  FsPermission();
  explicit FsPermission(Permission u, Permission g, Permission o);
  short toShort();
private:
  Permission useraction;
  Permission groupaction;
  Permission otheraction;
};

#endif // FSPERMISSION_H
