#-------------------------------------------------
#
# Project created by QtCreator 2013-05-23T12:36:35
#
#-------------------------------------------------

QT       += core
QT       += network
QT       -= gui
TARGET = Client-RPC
CONFIG   += console
CONFIG   -= app_bundle

TEMPLATE = app


SOURCES += main.cpp \
    rpcclient.cpp \
    configuration.cpp \
    request.cpp \
    serializer.cpp \
    response.cpp \
    baserequest.cpp \
    baseresponse.cpp \
    listingrequest.cpp \
    listingresponse.cpp \
    directorylisting.cpp \
    hdfsfilestatus.cpp \
    clientprotocol.cpp \
    clientprotocolimpl.cpp \
    fspermission.cpp \
    createrequest.cpp \
    createresponse.cpp \
    writable.cpp \
    datanodeinfo.cpp \
    block.cpp \
    blocktokenidentifier.cpp \
    token.cpp \
    locatedblock.cpp \
    protocol.cpp \
    command.cpp \
    commandfactory.cpp \
    worker.cpp \
    addblockrequest.cpp \
    addblockreponse.cpp \
    addblockcommand.cpp \
    createcommand.cpp \
    connection.cpp \
    datatransferprotocol.cpp \
    crc32.cpp \
    packet.cpp \
    writefile.cpp \
    remoteconnection.cpp \
    datanoderegistration.cpp

HEADERS += \
    rpcclient.h \
    configuration.h \
    request.h \
    serializer.h \
    response.h \
    baserequest.h \
    baseresponse.h \
    listingrequest.h \
    listingresponse.h \
    directorylisting.h \
    hdfsfilestatus.h \
    clientprotocol.h \
    clientprotocolimpl.h \
    fspermission.h \
    createrequest.h \
    createresponse.h \
    writable.h \
    datanodeinfo.h \
    block.h \
    blocktokenidentifier.h \
    token.h \
    locatedblock.h \
    protocol.h \
    command.h \
    commandfactory.h \
    worker.h \
    addblockrequest.h \
    addblockreponse.h \
    addblockcommand.h \
    createcommand.h \
    connection.h \
    datatransferprotocol.h \
    crc32.h \
    packet.h \
    global-types.h \
    writefile.h \
    remoteconnection.h \
    datanoderegistration.h

OTHER_FILES += \
    backup.txt \
    README.txt \
    rpc.qdocconf
