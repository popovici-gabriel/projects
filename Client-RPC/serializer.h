#ifndef SERIALIZER_H
#define SERIALIZER_H
#include<QByteArray>
#include<QString>
#include "global-types.h"
class Serializer
{
public:
  Serializer();

  /**
    * Serializes a long to a binary stream with zero-compressed encoding.
    * For -112 <= i <= 127, only one byte is used with the actual value.
    * For other values of i, the first byte value indicates whether the
    * long is positive or negative, and the number of bytes that follow.
    * If the first byte value v is between -113 and -120, the following long
    * is positive, with number of bytes that follow are -(v+112).
    * If the first byte value v is between -121 and -128, the following long
    * is negative, with number of bytes that follow are -(v+120). Bytes are
    * stored in the high-non-zero-byte-first order.
    *
    * @param stream Binary output stream
    * @param i Long to be serialized
    * @throws java.io.IOException
    */
   void writeVLong(QByteArray& array,  long i);

    /** WritableUtils.java
     *
      * Serializes an integer to a binary stream with zero-compressed encoding.
      * For -120 <= i <= 127, only one byte is used with the actual value.
      * For other values of i, the first byte value indicates whether the
      * integer is positive or negative, and the number of bytes that follow.
      * If the first byte value v is between -121 and -124, the following integer
      * is positive, with number of bytes that follow are -(v+120).
      * If the first byte value v is between -125 and -128, the following integer
      * is negative, with number of bytes that follow are -(v+124). Bytes are
      * stored in the high-non-zero-byte-first order.
      *
      * @param stream Binary output stream
      * @param i Integer to be serialized
      * @throws java.io.IOException
      */
   void writeVInt(QByteArray &array, int i);

   /**
        * Writes an <code>int</code> to the underlying output stream as four
        * bytes, high byte first. If no exception is thrown, the counter
        * <code>written</code> is incremented by <code>4</code>.
        *
        * @param      v   an <code>int</code> to be written.
        * @exception  IOException  if an I/O error occurs.
        * @see        java.io.FilterOutputStream#out
        */
  void writeInt(QByteArray& array, int v);
  void writeInt(QByteArray& b,int& pos, quint32 v);

  /**
       * Writes a <code>long</code> to the underlying output stream as eight
       * bytes, high byte first. In no exception is thrown, the counter
       * <code>written</code> is incremented by <code>8</code>.
       *
       * @param      v   a <code>long</code> to be written.
       * @exception  IOException  if an I/O error occurs.
       * @see        java.io.FilterOutputStream#out
       */
  void writeLong(QByteArray& byte, long v);
  void writeLong(QByteArray& b,int& pos, quint64 v);


  /**
      * Writes a string to the specified DataOutput using
      * <a href="DataInput.html#modified-utf-8">modified UTF-8</a>
      * encoding in a machine-independent manner.
      * <p>
      * First, two bytes are written to out as if by the <code>writeShort</code>
      * method giving the number of bytes to follow. This value is the number of
      * bytes actually written out, not the length of the string. Following the
      * length, each character of the string is output, in sequence, using the
      * modified UTF-8 encoding for the character. If no exception is thrown, the
      * counter <code>written</code> is incremented by the total number of
      * bytes written to the output stream. This will be at least two
      * plus the length of <code>str</code>, and at most two plus
      * thrice the length of <code>str</code>.
      *
      * @param      str   a string to be written.
      * @param      out   destination to write to
      * @return     The number of bytes written out.
      * @exception  IOException  if an I/O error occurs.
      */
  int writeUTF(const QByteArray& out ,const QString& str);

  /** Returns the number of bytes required to write this. */
 int utf8Length(const QString& string);

 /** Write a UTF-8 encoded string.
   *
   * @see DataOutput#writeUTF(String)
   */
 int writeString(QByteArray& out, QString s);

 int writeTextString(QByteArray& out, QString s);

 /**
      * Writes a <code>short</code> to the underlying output stream as two
      * bytes, high byte first. If no exception is thrown, the counter
      * <code>written</code> is incremented by <code>2</code>.
      *
      * @param      v   a <code>short</code> to be written.
      * @exception  IOException  if an I/O error occurs.
      * @see        java.io.FilterOutputStream#out
      */
      void writeShort(QByteArray& out, int v);

      void writeChars(QByteArray& out, const QString& s, int start, int length);

      void writeBoolean(QByteArray& out, bool v);

      /**
           * See the general contract of the <code>readInt</code>
           * method of <code>DataInput</code>.
           * <p>
           * Bytes
           * for this operation are read from the contained
           * input stream.
           *
           *
           * @return     the next four bytes of this input stream, interpreted as an
           *             <code>int</code>.
           * @exception  EOFException  if this input stream reaches the end before
           *               reading four bytes.
           * @exception  IOException   the stream has been closed and the contained
           * 		   input stream does not support reading after close, or
           * 		   another I/O error occurs.
           * @see        java.io.FilterInputStream#in
           */
      /**
           * Reads the next byte of data from the input stream. The value byte is
           * returned as an <code>int</code> in the range <code>0</code> to
           * <code>255</code>. If no byte is available because the end of the stream
           * has been reached, the value <code>-1</code> is returned. This method
           * blocks until input data is available, the end of the stream is detected,
           * or an exception is thrown.
           *
           * <p> A subclass must provide an implementation of this method.
           *
           * @return     the next byte of data, or <code>-1</code> if the end of the
           *             stream is reached.
           * @exception  IOException  if an I/O error occurs.
           */
      int readInt(const QByteArray& data,int& pos);

      char* readFully(int size, const QByteArray& data,int& pos);


      /**
           * See the general contract of the <code>readLong</code>
           * method of <code>DataInput</code>.
           * <p>
           * Bytes
           * for this operation are read from the contained
           * input stream.
           *
           * @return     the next eight bytes of this input stream, interpreted as a
           *             <code>long</code>.
           * @exception  EOFException  if this input stream reaches the end before
           *               reading eight bytes.
           * @exception  IOException   the stream has been closed and the contained
           * 		   input stream does not support reading after close, or
           * 		   another I/O error occurs.
           * @see        java.io.FilterInputStream#in
           */
          long readLong(int size,QByteArray& data,int& pos);


          /**
               * Reads one input byte and returns
               * <code>true</code> if that byte is nonzero,
               * <code>false</code> if that byte is zero.
               * This method is suitable for reading
               * the byte written by the <code>writeBoolean</code>
               * method of interface <code>DataOutput</code>.
               *
               * @return     the <code>boolean</code> value read.
               * @exception  EOFException  if this stream reaches the end before reading
               *               all the bytes.
               * @exception  IOException   if an I/O error occurs.
               */
              bool readBoolean(QByteArray& data, int& pos);

              /**
                   * See the general contract of the <code>readShort</code>
                   * method of <code>DataInput</code>.
                   * <p>
                   * Bytes
                   * for this operation are read from the contained
                   * input stream.
                   *
                   * @return     the next two bytes of this input stream, interpreted as a
                   *             signed 16-bit number.
                   * @exception  EOFException  if this input stream reaches the end before
                   *               reading two bytes.
                   * @exception  IOException   the stream has been closed and the contained
                   * 		   input stream does not support reading after close, or
                   * 		   another I/O error occurs.
                   * @see        java.io.FilterInputStream#in
                   */
              short readShort(QByteArray& data, int& pos);

              char* readString(QByteArray& data, int&pos);

              char* read0StringCompression(QByteArray& data, int&pos);

              long readVLong(QByteArray& data, int&pos);

              int decodeVIntSize(signed char value);

              bool isNegativeVInt(signed char  value);

              int readVInt(QByteArray& data, int& pos);

              /**
                 * Read a String as a Network Int n, followed by n Bytes
                 * Alternative to 16 bit read/writeUTF.
                 * Encoding standard is... ?
                 *
                 */
              char* readStringFromWritableUtils(QByteArray& data, int& pos);

              /** Read a UTF8 encoded string from in
                 */
              char* readStringFromText(QByteArray& data, int& pos);

              /**
                 * WritableUtils.java
                 * Write a String as a Network Int n, followed by n Bytes
                 * Alternative to 16 bit read/writeUTF.
                 * Encoding standard is... ?
                 *
                 */
              void writeStringFromWritableUtils(QByteArray& data, QString s);

              void writeByte(QByteArray& data, byte n);
};

#endif // SERIALIZER_H
