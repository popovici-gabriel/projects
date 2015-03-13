package health;
/*
 * Copyright © 1998 - 2002 IBM Corporation.
 *
 * Redistribution and use in source (source code) and binary (object code)
 * forms, with or without modification, are permitted provided that the
 * following conditions are met:
 * 1. Redistributed source code must retain the above copyright notice, this
 * list of conditions and the disclaimer below.
 * 2. Redistributed object code must reproduce the above copyright notice,
 * this list of conditions and the disclaimer below in the documentation
 * and/or other materials provided with the distribution.
 * 3. The name of IBM may not be used to endorse or promote products derived
 * from this software or in any other form without specific prior written
 * permission from IBM.
 * 4. Redistribution of any modified code must be labeled "Code derived from
 * the original OpenCard Framework".
 *
 * THIS SOFTWARE IS PROVIDED BY IBM "AS IS" FREE OF CHARGE. IBM SHALL NOT BE
 * LIABLE FOR INFRINGEMENTS OF THIRD PARTIES RIGHTS BASED ON THIS SOFTWARE.  ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IBM DOES NOT WARRANT THAT THE FUNCTIONS CONTAINED IN THIS
 * SOFTWARE WILL MEET THE USER'S REQUIREMENTS OR THAT THE OPERATION OF IT WILL
 * BE UNINTERRUPTED OR ERROR-FREE.  IN NO EVENT, UNLESS REQUIRED BY APPLICABLE
 * LAW, SHALL IBM BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  ALSO, IBM IS UNDER NO OBLIGATION
 * TO MAINTAIN, CORRECT, UPDATE, CHANGE, MODIFY, OR OTHERWISE SUPPORT THIS
 * SOFTWARE.
 */

import opencard.core.service.SmartCard;
import opencard.core.service.CardRequest;
import opencard.core.service.CardServiceRegistry;

/**
 * Test is a little test program for <tt>Card</tt>,
 * <tt>CardProxy</tt> and <tt>CardApplet</tt>.
 *
 * <tt>main</tt> creates and starts some instances of <tt>HealthTest</tt>
 * which create their own <tt>SmartCard</tt> and <tt>HealthCardProxy</tt>
 * object and concurrently access the data stored by <tt>HealthCardApplet</tt>
 * on the JavaCard.
 *
 */
public class HealthTest implements Runnable {
	/** The number of threads to be created. */
	final static int NUM_THREADS = 1;

	/** The object used for accessing data stored by the <tt>CardApplet</tt>
	  on the card. */
	HealthCard healthCard = null;
	
	/** The thread running this instance. */
	protected Thread thread_ = null;

/**
 * Creates a new Test instance.
 */
public HealthTest(boolean setInfo) {
	super();
	try {
		healthCard = new HealthCard();
		if (setInfo == true) {
			//businessCard.setBusinessInfo(0, new BusinessInfo("Thomas Schaeck", "Dipl. Inf.", "schaeck@de.ibm.com", "+49 7031 16 3479", "D-71032 Boeblingen"));
			//businessCard.setBusinessInfo(1, new BusinessInfo("Frank Seliger", "Dipl. Phys.", "seliger@de.ibm.com", "+49 7031 16 3142", "D-71032 Boeblingen"));
			  healthCard.setDoctorInfo(0,new DoctorInfo("Test Geo ","Inginer","geo@yahoo.com ","+40 0899","0789"));
		}	
	} catch (HealthCardException e) {
		e.printStackTrace();
		System.exit(0);
	}
	thread_ = new Thread(this);
}
/**
 * Starts the application.
 * @param args an array of command-line arguments
 */
public static void main(java.lang.String[] args) {
	CardServiceRegistry.getRegistry().add(new HealthCardProxyFactory());
	//CardServiceRegistry.getRegistry().add(new AnyAppletProxyFactory());

	HealthTest[] healthTests = new HealthTest[NUM_THREADS];
	boolean setInfo = true; // Default: set new info on card at first
	if (args.length > 0)
		setInfo = false; // Any argument passed: Do not init card

	// Create the test objects.
	for (int i = 0; i < NUM_THREADS; i++) {
		System.out.println("Creating thread " + i);
		if (i > 0)
			setInfo = false; // only on first thread
		if (setInfo == true)
			System.out.println("The card info is set in this thread");
		healthTests[i] = new HealthTest(setInfo);
	}

	// Start the test objects.
	for (int i = 0; i < NUM_THREADS; i++) {
		System.out.println("Starting thread " + i);
		healthTests[i].start();
	}
}
/**
 * Print the given string s followed by the thread of this object.
 *
 * @param s The string to be printed.
 */
public void println(String s)
{
  String blanks = new String("                                                         ");
  int length = blanks.length() - s.length();
  if (length > 0) {
      System.out.println(s + blanks.substring(0, length) + thread_);
  }
  else  {
      println("-----");       // Print the thread on its own line and ...
      System.out.println(s);  // .. the given string on a new line
  }
}

public void run() {
	try {
		SmartCard card = SmartCard.waitForCard(new CardRequest(CardRequest.ANYCARD, null, null));

        //?? Start of section for testing of selection change
		//AnyAppletProxy otherAppletProxy = (AnyAppletProxy) card.getCardService(AnyAppletProxy.class, true);
		HealthCardProxy healthCardProxy = (HealthCardProxy) card.getCardService(HealthCardProxy.class,true);
		println("Retrieving info from HealthCardApplet now ...");
		println("Phone[1]: >" + healthCardProxy.getDPhone(1) + "<");
		//?? End of section for testing of selection change

		println("Reading doctor info 0 ...");
		DoctorInfo doctorInfo = healthCard.getDoctorInfo(0);
		println("DoctorInfo 0 = \n" + doctorInfo);
		println("Reading pacient info 1 ...");
		PacientInfo pacientInfo = healthCard.getPacientInfo(1);
		println("Pacient Info 1 = \n" + pacientInfo);

		// long name to force buffer extension
		//businessInfo0.setName("Thomas [no-middle-initial] Schaeck");
		//businessInfo0.setPhone("+49 7031 / 16 - 3479");
		//println("Business Info 0 = \n" + businessInfo0);

		//?? Start of section for testing of selection change
		// Force another pair of selection changes
		//println("Storing info to AnyCardApplet now ...");
		//healthCardProxy.setPhone(1, "+49 7034 63 909");
		//?? End of section for testing of selection change

		//println("Writing back business info 0 ...");
		//businessCard.setBusinessInfo(0, businessInfo0);
		//println("Re-reading business info 0 ...");
		//businessInfo0 = businessCard.getBusinessInfo(0);
		//println("Business Info 0 = \n" + businessInfo0);
		println("------------  DONE");
	} catch (HealthCardException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
/**
 * Start the test.
 */
public void start() {
	thread_.start();
}
}
