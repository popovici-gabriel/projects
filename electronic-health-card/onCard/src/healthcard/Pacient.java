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
public class Pacient {
	
	
	
	/*
	 * info pacient 
	 */
	private Record name;
	private Record prenume;
	private Record CNP;
	private Record address;
	private Record grupa; 
	private Record rh;
	private Record photo;
	
			
	
	
	/**
	 * Private function to initialize an empty Record 
	 */
	private void init() {
	     	name = new Record( (short)20);
	     	prenume = new Record( (short)20);
	     	CNP = new Record ((short) 15);
	     	address  = new Record ((short) 50);
	     	grupa = new Record( (short)27);
	     	rh = new Record ((short)20);
	     	photo = new Record ((short)4000);	     	     		     		     		     		     		     		  
	}

	/**
	 * Construct an empty Pacient  
	 */
	public Pacient() {
	       this.init();
	}

	/**
	 * Construct a Pacient from given byte arrays 
	 */
	public Pacient ( byte[] r0, byte[] r1, byte[] r2, byte[] r3, byte[] r4, byte[] r5,byte[] r6) {
	       this.init();	
	       name.update(r0);
	       prenume.update(r1);
	       CNP.update(r2);
	       address.update(r3);
	       grupa.update(r4);		       	
	       rh.update(r5);
	       photo.update(r6);
		}

	/**
	 * Return the n-th record.  As with arrays, n starts with 0.
	 */
	public Record getRecord(byte n) {
	       Record record = null;
	       switch (n) {
	           case 0x00 : record = name; break;
	           case 0x01 : record = prenume; break;
	           case 0x02 : record = CNP; break;
	           case 0x03 : record = address; break;
	           case 0x04 : record = grupa; break;	           	           
	           case 0x05 : record = rh; break;	           
	           case 0x06 : record = photo; break; 
	           default   : ISOException.throwIt(ISO7816.SW_WRONG_P1P2);
	       }
	       return record;
	}

	/**
	 * Set the n-th record.  As with arrays, n starts with 0.
	 */
	public void setRecord(byte n, byte[] bytes) {
	       setRecord(n, bytes, (short)0, (short)bytes.length);
	       return;
	}

	/**
	 * Set the n-th record.  As with arrays, n starts with 0.
	 */
	public void setRecord(byte n, byte[] bytes, short offset, short length) {
	       switch (n) {
	           case 0x00 : name.update(bytes, offset, length); break;
	           case 0x01 : prenume.update(bytes, offset, length); break;
	           case 0x02 : CNP.update(bytes, offset, length); break;
	           case 0x03 : address.update(bytes, offset, length); break;
	           case 0x04 : grupa.update(bytes, offset, length); break;	           	           
	           case 0x05 : rh.update(bytes,offset,length);break;	           
	           case 0x06 : photo.update(bytes,offset,length);break;	           
	           default   : ISOException.throwIt(ISO7816.SW_WRONG_P1P2);
	       }
	       return;
	}
}	

	
	

