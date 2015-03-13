#ifndef GLOBAL_TYPES_H
#define GLOBAL_TYPES_H
#include <QtGlobal>
#include <iostream>
#include <string>
using namespace std;

typedef signed char byte;
typedef quint32 lzo_uint;
typedef quint32  lzo_uint32;
typedef quint32* lzo_uint32p;
typedef  char* lzo_bytep;
// Bit twiddling
    /**
     * The number of bits used to represent an <tt>int</tt> value in two's
     * complement binary form.
     *
     * @since 1.5
     */
const static int INT_SIZE = 32;

 /**
     * The number of bits used to represent a <tt>byte</tt> value in two's
     * complement binary form.
     *
     * @since 1.5
     */
const static int BYTE_SIZE = 8;

/**
 *  Used in Packet
 * @brief SIZE_OF_INTEGER
 */
const static int SIZE_OF_INTEGER = INT_SIZE/BYTE_SIZE;
const static string CLIENT = "cplussplus";
#endif
