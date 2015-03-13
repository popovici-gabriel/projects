package healthcard;

import javacard.framework.*;
public class Link {
	/**
	 * O lista care contine obiecte Record
	 */
	public Record iData;
	/**
	 * next Link
	 */		
	public Link next;	
	public Link(){						
		iData = new Record((short)50);
		next = null;
	}
}
