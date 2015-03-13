#include "commandfactory.h"

CommandFactory::CommandFactory()
{
}

std::map<QString, Command *> CommandFactory::getHandlers() const
{
  return handlers;
}

void CommandFactory::setHandlers(const std::map<QString, Command *> &value)
{
  handlers = value;
}

Command* CommandFactory::getCommand(QString name)
{
  return handlers[name];
}

void CommandFactory::addCommand(QString name, Command *c)
{
  handlers[name] =c;
}
