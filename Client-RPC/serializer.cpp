#include "serializer.h"
#include<QString>
#include<QDebug>

Serializer::Serializer()
{
}

void Serializer::writeInt(QByteArray& array, int v)
 {
       array.append((v >> 24) & 0xFF);
       array.append((v >> 16) & 0xFF);
       array.append((v >>  8) & 0xFF);
       array.append((v >>  0) & 0xFF);
  }


/**
     * Writes a <code>long</code> to the underlying output stream as eight
     * bytes, high byte first. In no exception is thrown, the counter
     * <code>written</code> is incremented by <code>8</code>.
     *
     * @param      v   a <code>long</code> to be written.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FilterOutputStream#out
     */
void Serializer::writeLong(QByteArray& b, long v)
{
       QByteArray writeBuffer;
        writeBuffer[0] = (byte)(v >> 56);
        writeBuffer[1] = (byte)(v >> 48);
        writeBuffer[2] = (byte)(v >> 40);
        writeBuffer[3] = (byte)(v >> 32);
        writeBuffer[4] = (byte)(v >> 24);
        writeBuffer[5] = (byte)(v >> 16);
        writeBuffer[6] = (byte)(v >>  8);
        writeBuffer[7] = (byte)(v >>  0);
        b.append(writeBuffer);
}


void Serializer::writeVLong(QByteArray& array,  long i)
{
     if (i >= -112 && i <= 127) {
        //stream.writeByte((byte)i);
         array.append((byte)i);
        return;
      }

      int len = -112;
      if (i < 0) {
        i ^= -1L; // take one's complement'
        len = -120;
      }

      long tmp = i;
      while (tmp != 0) {
        tmp = tmp >> 8;
        len--;
      }

      //stream.writeByte((byte)len);
      array.append((byte) len);

      len = (len < -120) ? -(len + 120) : -(len + 112);

      for (int idx = len; idx != 0; idx--) {
        int shiftbits = (idx - 1) * 8;
        long mask = 0xFFL << shiftbits;
        //stream.writeByte((byte)((i & mask) >> shiftbits));
        array.append((byte) ((i & mask) >> shiftbits));
      }
}


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
 int writeUTF(const QByteArray& out ,const QString& str)
 {
    //TODO : should be implemented from JDK
  }

 /** Write a UTF-8 encoded string.
   *
   * @see DataOutput#writeUTF(String)
   */
 int Serializer::writeString(QByteArray& out, QString s)
 {
    if (s.length() > (0xffff/3))
      {
        // maybe too long
        qWarning() << "truncating long string: " + s.length() << " chars, starting with "  << s.left( 20);
        s = s.left((int)0xffff/3);
    }

    //int len = this->utf8Length(s);
    int len = s.toUtf8().length();
    if (len > 0xffff)                             // double-check length
      qWarning() << "string too long!";

    this->writeShort(out,len);
    //out.writeShort(len);
    //writeChars(out, s, 0, s.length());
    //this->writeChars(out,s,0,s.length());
    out.append(s.toUtf8());
    return len;
  }

 /** Returns the number of bytes required to write this. */
int Serializer::utf8Length(const QString& string)
{
     int stringLength = string.length();
     int utf8Length = 0;
     for (int i = 0; i < stringLength; i++)
       {
       int c = string.at(i).digitValue();
       if ((c >= 0x0001) && (c <= 0x007F))
         {
         utf8Length++;
         }
       else if (c > 0x07FF)
         {
         utf8Length += 3;
         }
         else
         {
         utf8Length += 2;
         }
     }
     return utf8Length;
 }

void Serializer::writeShort(QByteArray& out, int v)
{
  out.append((v >> 8) & 0xFF);
  out.append((v >> 0) & 0xFF);
}


void Serializer::writeChars(QByteArray& out, const QString& s, int start, int length)
{
     const int end = start + length;
      for (int i = start; i < end; i++)
        {
        int code = s.at(i).digitValue();
        if (code >= 0x01 && code <= 0x7F)
          {
          out.append((byte) code);
          }
          else if (code <= 0x07FF)
          {
           out.append((byte)(0xC0 | ((code >> 6) & 0x1F)));
           out.append((byte)(0x80 |   code & 0x3F));
          }
          else
          {
          out.append((byte)(0xE0 | ((code >> 12) & 0X0F)));
          out.append((byte)(0x80 | ((code >>  6) & 0x3F)));
          out.append((byte) (0x80 |  (code        & 0x3F)));
          }
      }
}

int Serializer::readInt(const QByteArray& data,int& pos)
{
  int ch1 = (data[pos++] & 0xff);
  int ch2 = (data[pos++] & 0xff);
  int ch3 = (data[pos++] & 0xff);
  int ch4 = (data[pos++] & 0xff);

  if ((ch1 | ch2 | ch3 | ch4) < 0)
    {
       qWarning() <<"Read Error";
       return 0;
    }


  return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
}


  char* Serializer::readFully(int size, const QByteArray& data,int& pos)
  {
    char *path = new char[size+1];
    for (int var = 0; var < size; ++var)
      {
        path[var] = data[pos++];
      }
    path[size]='\0';

    return path;
  }


  long Serializer::readLong(int size,QByteArray& data,int& pos)
  {
    char *readBuffer = readFully(size,data,pos);
    long value  =    (((long) readBuffer[0] << 56) +
                              (  (long)(readBuffer[1] & 255) << 48) +
                              (  (long)(readBuffer[2] & 255) << 40) +
                              (  (long)(readBuffer[3] & 255) << 32) +
                              (  (long)(readBuffer[4] & 255) << 24) +
                              (             (readBuffer[5] & 255) << 16) +
                              (             (readBuffer[6] & 255) <<  8) +
                              (             (readBuffer[7] & 255) <<  0));
    delete[] readBuffer;
    return value;
  }



  bool Serializer::readBoolean(QByteArray& data, int& pos)
  {
    int ch = data[pos++];
    if (ch < 0 )
      {
        qWarning() <<"Parse error";
        return false;
      }
    return (ch !=0);
  }


  short Serializer::readShort(QByteArray& data, int& pos)
  {
    int ch1 = data[pos++];
    int ch2 = data[pos++];
          if ((ch1 | ch2) < 0)
            {
              qWarning() <<"Parse error";
              return 0;
            }

     return (short)((ch1 << 8) + (ch2 << 0));
  }



  char* Serializer::readString(QByteArray& data, int&pos)
  {
      int bytes  = readShort(data,pos);
      return readFully(bytes, data, pos);
  }


  char* Serializer::read0StringCompression(QByteArray& data, int&pos)
  {
    int length = (int) readVLong(data,pos);
    return readFully(length,data,pos);
  }


  long Serializer::readVLong(QByteArray& data, int&pos)
  {
    byte firstByte = data[pos++];
    int len = decodeVIntSize(firstByte);
    if (1 == len)
      return firstByte;
    long i = 0;
    for (int idx = 0; idx < len-1; idx++)
      {
        byte  b = data[pos++];
          i = i << 8;
          i = i | (b & 0xFF);
       }
    return (isNegativeVInt(firstByte) ? (i ^ -1L) : i);
  }


  int Serializer::decodeVIntSize(byte value)
  {
        if (value >= -112)
          {
            return 1;
          }
        else if (value < -120)
          {
          return -119 - value;
          }

        return (-111 - value);
  }

  bool Serializer::isNegativeVInt(byte value)
  {
      return value < -120 || (value >= -112 && value < 0);
  }


  void Serializer::writeBoolean(QByteArray& out, bool v)
  {
    int val = v ? 1:0;
    out.append(val);
  }


  int Serializer::writeTextString(QByteArray& out, QString s)
  {
    long length = (long) s.size();
    writeVLong(out,length);
    out.append(s.toUtf8());
    return length;
  }

  int Serializer::readVInt(QByteArray &data, int &pos)
  {
    return (int) readVLong(data,pos);
  }


  char* Serializer::readStringFromWritableUtils(QByteArray &data, int &pos)
  {
      int len = readInt(data,pos);
      if (-1 == len)
        return NULL;

      return readFully(len,data,pos);
  }


  char* Serializer::readStringFromText(QByteArray &data, int &pos)
  {
    int length = readVInt(data,pos);
    return readFully(length,data,pos);
  }

  void Serializer::writeVInt(QByteArray &array, int i)
  {
    writeVLong(array,(long)i);
  }


  void Serializer::writeStringFromWritableUtils(QByteArray &data, QString s)
  {
    writeInt(data,s.length());
    data.append(s.toUtf8());
  }

  void
  Serializer::writeByte(QByteArray &data, byte n)
  {
    data.append(n);
  }

 void
  Serializer::writeInt(QByteArray &b, int& pos, quint32 v)
  {
    b[pos++] = ((v >> 24) & 0xFF);
    b[pos++] = ((v >> 16) & 0xFF);
    b[pos++] = ((v >>  8) & 0xFF);
    b[pos++] = ((v >>  0) & 0xFF);
  }

 void
 Serializer::writeLong(QByteArray &b, int& pos, quint64 v)
  {
   b[pos++] = (byte)(v >> 56);
   b[pos++] = (byte)(v >> 48);
   b[pos++] = (byte)(v >> 40);
   b[pos++] = (byte)(v >> 32);
   b[pos++] = (byte)(v >> 24);
   b[pos++] = (byte)(v >> 16);
   b[pos++] = (byte)(v >>  8);
   b[pos++] = (byte)(v >>  0);
  }
