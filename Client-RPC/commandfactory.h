#ifndef COMMANDFACTORY_H
#define COMMANDFACTORY_H
#include<istream>
#include<map>
#include<QString>
using namespace std;

class Command;
class CommandFactory
{
public:
  CommandFactory();
  void addCommand(QString , Command *);
  std::map<QString, Command *> getHandlers() const;
  void setHandlers(const std::map<QString, Command *> &value);
  Command* getCommand(QString name);
private:
  std::map<QString, Command *> handlers;
};

#endif // COMMANDFACTORY_H
