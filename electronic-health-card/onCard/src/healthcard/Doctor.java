/**
 * 
 */
package healthcard;

import javacard.framework.ISO7816;
import javacard.framework.ISOException;

/**
 * @author Gaby Popovici
 * 
 */
/**
 * A Record, as it is stored on the JavaCard. Each record has the same fixed
 * size.
 */

public class Doctor {
	private Record name;// Nume + Penume

	private Record prenume;// tip

	private Record cnp;// telefon fix (birou)

	private Record parafa;// mobil

	private Record unitate;//

	/**
	 * Private function to initialize an empty Record
	 */
	private void init() {
		name = new Record((short) 60);
		prenume = new Record((short) 40);
		cnp = new Record((short) 40);
		parafa = new Record((short) 40);
		unitate = new Record((short) 40);
	}

	/**
	 * Construct an empty Doctor
	 */
	public Doctor() {
		this.init();
	}

	/**
	 * Construct a Doctor from given byte arrays
	 */
	public Doctor(byte[] r0, byte[] r1, byte[] r2, byte[] r3, byte[] r4) {
		this.init();
		name.update(r0);
		prenume.update(r1);
		cnp.update(r2);
		parafa.update(r3);
		unitate.update(r4);
	}

	/**
	 * Return the n-th record. As with arrays, n starts with 0.
	 */
	public Record getRecord(byte n) {
		Record record = null;
		switch (n) {
		case 0x00:
			record = name;
			break;
		case 0x01:
			record = prenume;
			break;
		case 0x02:
			record = cnp;
			break;
		case 0x03:
			record = parafa;
			break;
		case 0x04:
			record = unitate;
			break;
		default:
			ISOException.throwIt(ISO7816.SW_WRONG_P1P2);
		}
		return record;
	}

	/**
	 * Set the n-th record. As with arrays, n starts with 0.
	 */
	public void setRecord(byte n, byte[] bytes) {
		setRecord(n, bytes, (short) 0, (short) bytes.length);
		return;
	}

	/**
	 * Set the n-th record. As with arrays, n starts with 0.
	 */
	public void setRecord(byte n, byte[] bytes, short offset, short length) {
		switch (n) {
		case 0x00:
			name.update(bytes, offset, length);
			break;
		case 0x01:
			prenume.update(bytes, offset, length);
			break;
		case 0x02:
			cnp.update(bytes, offset, length);
			break;
		case 0x03:
			parafa.update(bytes, offset, length);
			break;
		case 0x04:
			unitate.update(bytes, offset, length);
			break;
		default:
			ISOException.throwIt(ISO7816.SW_WRONG_P1P2);
		}
		return;
	}
}
