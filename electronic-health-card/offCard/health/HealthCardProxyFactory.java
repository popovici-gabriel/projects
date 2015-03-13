/**
 * 
 */
package health;

import java.util.Enumeration;
import java.util.Vector;

import opencard.core.service.CardServiceFactory;
import opencard.core.service.CardServiceScheduler;
import opencard.core.service.CardType;
import opencard.core.terminal.CardID;
import opencard.core.terminal.CardTerminalException;
import opencard.core.util.Tracer;


/**
 * @author gabrielp
 * Creates instances of the CardService class named: <tt>HealthCardProxy</tt> 
 * for JavaCards.
 * 
 */
public class HealthCardProxyFactory extends CardServiceFactory {
	/** Card type that identifies the cards for which this factory can create services. */
	public final static int JC_TYPE_2   = 0x02;
        public final static int JCOP_TYPE_1 = 0x01;

	/** Tracer for logging. */
	private Tracer iTracer = new Tracer(this, HealthCardProxyFactory.class);

	/** Holds the services which are available. */
	private static Vector services_ = new Vector();

	// This factory can create instances of HealthCardProxy.
	static {
		services_.addElement(HealthCardProxy.class);
	}
/**
 * Constructs an object of this class. Default Constructor 
 */
	public HealthCardProxyFactory() {
	}
	/**
	 * Indicate whether this <tt>CardServiceFactory</tt> supports cards of this type and/or
	 * installed card applications.  Would it be able to instantiate a <tt>CardService</tt>s 
	 * for cards of this type?
	 * <p>
	 * This factory does not read the card, rather it only judges from the card's ATR.
	 *
	 * @param cid       A <tt>CardID</tt> received from a <tt>Slot</tt>.
	 * @param scheduler A <tt>CardServiceScheduler</tt> that could be used to communicate with
	 *                  the card to determine its type.  It is not used by this factory.
	 *
	 * @return A valid CardType if the factory can instantiate services for this card.
	 *         CardType.UNSUPPORTED if the factory does not support the card.
	 *
	 * @see ##getClassFor
	 */
	protected CardType getCardType(CardID cid, CardServiceScheduler scheduler) throws CardTerminalException {
		byte[] historicalBytes = cid.getHistoricals();

		byte[] IBM_JC_2_HIST = {(byte) 0x80, (byte) 0x31, (byte) 0xC0, (byte) 0x6B, (byte) 0x49, 
					(byte) 0x42, (byte) 0x4D, (byte) 0x20, (byte) 0x4A, (byte) 0x65,
					(byte) 0x74, (byte) 0x5A, (byte) 0x20, (byte) 0x4D, (byte) 0x32}; //verficat 
		
	        byte[] IBM_JCOP_1_HIST = {(byte) 0x4A, (byte) 0x43,
					  (byte) 0x4F, (byte) 0x50, (byte) 0x31, (byte) 0x30};//verificat          

	        if (historicalsEqual(historicalBytes, IBM_JC_2_HIST, IBM_JC_2_HIST.length)) {
	            iTracer.debug("getCardType", "JC_TYPE_2");
	            return new CardType(JC_TYPE_2);
	        }
	        if (historicalsEqual(historicalBytes, IBM_JCOP_1_HIST, IBM_JCOP_1_HIST.length)) {
	            iTracer.debug("getCardType", "JCOP_TYPE_1");
	            return new CardType(JCOP_TYPE_1);
	        }
	        iTracer.debug("getCardType", "UNSUPPORTED");
		return CardType.UNSUPPORTED;
	}	
	/**
	 * Returns an enumeration of known <tt>CardService</tt> classes.
	 *
	 * @param type The <tt>CardType</tt> for which to enumerate.
	 *
	 * @return An <tt>Enumeration</tt> of class objects.
	 */
	protected Enumeration getClasses(CardType type) {
		return services_.elements();
	}

	/**
	 * Compare two byte arrays with historical bytes.  
	 * @return true if the byte arrays are equal, false otherwise.
	 */
	private boolean historicalsEqual(byte[] lhs, byte[] rhs, int length) {

	    int s;
	    for (s = 0; s <= length-1; s++) {
	        if (lhs[s] != rhs[s]) {
	              return false;
	        }
	    }
	    return true;
	}	
}
