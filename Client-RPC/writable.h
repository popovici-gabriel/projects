#ifndef WRITABLE_H
#define WRITABLE_H
#include<QByteArray>

class Writable
{
public:
  Writable();
  virtual  ~Writable();
  /**
     * Serialize the fields of this object to <code>out</code>.
     *
     * @param out <code>QByteArray</code> to serialize this object into.
     * @throws IOException
     */
  virtual void write(QByteArray&) = 0;
  /**
     * Deserialize the fields of this object from <code>in</code>.
     *
     * <p>For efficiency, implementations should attempt to re-use storage in the
     * existing object where possible.</p>
     *
     * @param in <code>QByteArray</code> to deseriablize this object from.
     * @throws IOException
     */
  virtual void readFields(QByteArray&,int& pos) =0;
  /**
    * Full java class name
   * @brief javaLangType package java name
   * @return QString
   */
  virtual QString javaClassName() =0;
};

#endif // WRITABLE_H
