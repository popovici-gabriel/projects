#include "fspermission.h"

const char* FsPermission::FsAction[] =
{
    // POSIX style
    "---",
    "--x",
     "-w-",
     "-wx",
    "r--",
    "r-x",
     "rw-",
    "rwx"
};

const char* FsPermission::className = "org.apache.hadoop.fs.permission.FsPermission";

FsPermission::FsPermission()
{
}

FsPermission::FsPermission(Permission u, Permission g, Permission o):
  useraction(u),
  groupaction(g),
  otheraction(o)
{

}

short FsPermission::toShort()
{
    int s = (useraction << 6) | (groupaction << 3) | otheraction;
     return (short)s;
}
