/**
 * 
 */
package healthcard;

import javacard.framework.Util;

/**
 * @author Gaby Popovici
 *
 */
public class Record {
	byte[] data;
    short  length;


/**
* Construct an empty Record class
* @param bufferSize - short specifying the size of the reusable buffer
*/
public Record ( short bufferSize) {
    data = new byte[bufferSize];
    data[0] = (byte)'?';
    length = 1;
}

/**
* Construct a Record object from a byte array (deep copy).
* @param bytes  - byte[] containing the data contents
* @param bufferSize - short specifying the size of the reusable buffer
*/
public Record  ( byte[] bytes, short bufferSize) {
    data = new byte[bufferSize];
    this.update(bytes);
}

/**
* Update a Record from a byte array (deep copy)
* @param bytes - byte[] containing the new data
*/
public void update( byte[] bytes) {
    update(bytes, (short)0, (short)bytes.length);
}

/**
* Update a Record from a byte array (deep copy)
* @param bytes - byte[] containing the new data
* @param offset - offset in the source bytes
* @param lng - length of update data
*/
public void update( byte[] bytes, short offset, short lng) {
    length = lng;
    if (length > data.length) {     // Get a larger buffer.  This is not
        byte [] oldData = data;     // ... always a good strategy on a
        data = new byte[length];    // ... Java Card.  It could fail when
                                    // ... the memory is filled up.
    }
    Util.arrayCopy(bytes, offset, data, (short)0, length);
}
/**
 * 
 */
public void update(byte[] bytes,short lng){
	length = lng;
	if (length > data.length){
		byte[] oldData = data;
		data = new byte[length];
	}
	Util.arrayCopy(bytes, (short)0,data,(short)0,length);
}
}

