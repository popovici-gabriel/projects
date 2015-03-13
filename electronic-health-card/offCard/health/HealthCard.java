/**
 * 
 */
package health;
import opencard.core.event.CTListener;
import opencard.core.event.CardTerminalEvent;
import opencard.core.event.EventGenerator;
import opencard.core.service.SmartCard;
import opencard.core.service.CardService;
//import opencard.core.terminal.CardTerminalRegistry;
import opencard.core.terminal.CardTerminalException;
//import samples.business.BusinessCardException;
//import samples.business.BusinessCardProxy;
//import samples.business.BusinessInfo;

/**
 * HealthCard represents a JavaCard with a HealthCardApplet to applications. 
 * A HealthCard object offers methods to get and set PacientInfo and 
 * DoctorInfo entries on the card.
 *
 * @author Gaby P.  
 * 
 */

/**
 * @author Gaby Popovici
 * event-driven Model 
 */
public class HealthCard implements CTListener{
	/** The object representing the Smart Card and OCF. */
	  SmartCard card_ = null;

	  /** The Applet Proxy for the HealthCardApplet. */
	  HealthCardProxy healthCardProxy_ = null;
	/**
	 * Creates a new HealthCard object.
	 */
	 public HealthCard() throws HealthCardException {
			try {
				// Start the OpenCard Framework.
				SmartCard.start();

				// Register the new SignatureCard as a Card Terminal Event Listener
				EventGenerator.getGenerator().addCTListener(this);

				// Let the registry create events for cards which are already present
				EventGenerator.getGenerator().createEventsForPresentCards(this);
			} catch (Exception e) {
				// Do exception handling
				e.printStackTrace();
			}
		}//
	 /**
	  * React on card insertion events sent by OCF: Get new card and card service
	  *
	  * @param ctEvent The card insertion event.
	  */
	 public void cardInserted(CardTerminalEvent ctEvent) {
	 	try {
	 		// Get a SmartCard object
	 		card_ = SmartCard.getSmartCard(ctEvent, null);

	 		// Get a Proxy instance for the HealthCardProxy.
	 		healthCardProxy_ = (HealthCardProxy) card_.getCardService(HealthCardProxy.class, true);
	 	} catch (Exception e) {
	 		// Exception handling to be inserted here.
	 		e.printStackTrace();
	 	}
	 }
	 /**
	  * React on card removal events sent by OCF: Invalidate card and card service.
	  *
	  * @param ctEvent The card removal event.
	  */
	 public synchronized void cardRemoved(CardTerminalEvent ctEvent) {
	 	card_ = null;
	 	healthCardProxy_ = null;
	 }
	 /**
	  * Close a <tt>BusinessCard</tt> object.
	  */
	 public void close() throws CardTerminalException {
	 	// Shut down the OpenCard Framework.
	 	SmartCard.shutdown();
	 }
	 /**
	  * Gets a <tt>DoctorInfo</tt> object for the entry with the given index.
	  *   0x00 este index pentru Doctor (0) 
	  *
	  * @param index The index of the entry for which a <tt>DoctorInfo</tt>
	  *              object shall be obtained.
	  *
	  * @exception HealthCardException
	  *            Thrown when problems occurr while reading the info from card.
	  */
	 public DoctorInfo getDoctorInfo(int index) throws HealthCardException {
	 	if (card_ == null)
	 		throw new HealthCardException("No card found.");
	 	try {
	 		// We need mutual exclusion here to prevent other Card Services from
	 		// modifying data on the card while we read the information fields.
	 		card_.beginMutex();

	 		// Read the records  using an Card Applet Proxy.
	 		return new DoctorInfo(healthCardProxy_.getDName(index), 
	 								healthCardProxy_.getDType(index), 
	 								healthCardProxy_.getDEmail(index), 
	 								healthCardProxy_.getDPhone(index), 
	 								healthCardProxy_.getDMobile(index));
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		throw new HealthCardException(e.getMessage());
	 	} finally {
	 		// End mutual exclusion. From now on other Card Services may
	 		// access the card again.
	 		card_.endMutex();
	 	}
	 }
	 /**
	  * Sets the entry with the given index to the given <tt>DoctorInfo</tt> object. 
	  *
	  * @param index        The index of the entry for which a
	  *                     <tt>DoctorInfo</tt> object shall be written.
	  * @param doctorInfo The <tt>DoctorInfo</tt> object to be written.
	  *
	  * @see health.DoctorInfo
	  *
	  * @exception health.HealthCardException
	  *            Thrown when problems occurr while writing the info to  card.
	  */
	 public void setDoctorInfo(int index, DoctorInfo doctorInfo) 
	 throws HealthCardException {
	 	// Check for card presence.
		 
	 	if (card_ == null)
	 		throw new HealthCardException("No card found.");
   	 	
		 
	 	try {
	 		// We need mutual exclusion here to prevent other CardServices from
	 		// modifying data on the card while we write the information records.
	 		card_.beginMutex();

	 		// Write the fields to the card using an AppletProxy.
	 		healthCardProxy_.setDName(index, doctorInfo.getName());
	 		healthCardProxy_.setDType(index, doctorInfo.getType());
	 		healthCardProxy_.setDEmail(index, doctorInfo.getEmail());
	 		healthCardProxy_.setDPhone(index, doctorInfo.getPhone());
	 		healthCardProxy_.setDMobile(index, doctorInfo.getMobile());
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		throw new HealthCardException(e.getMessage());
	 	} finally {
	 		// End mutual exclusion. From now on other CardServices may access
	 		// the card again. We make this call in the finally statement to ensure
	 		// that it is performed under all circumstances.
	 		card_.endMutex();
	 	}
	 }	 
	 /**
	  * Gets a <tt>PacientInfo</tt> object for the entry with the given index.
	  *   0x01 este index pentru Pacient (1) 
	  *
	  * @param index The index of the entry for which a <tt>PacientInfo</tt>
	  *              object shall be obtained.
	  *
	  * @exception HealthCardException
	  *            Thrown when problems occurr while reading the info from card.
	  */
	 public PacientInfo getPacientInfo(int index) throws HealthCardException {
	 	if (card_ == null)
	 		throw new HealthCardException("No card found.");
	 	try {
	 		// We need mutual exclusion here to prevent other Card Services from
	 		// modifying data on the card while we read the information fields.
	 		card_.beginMutex();

	 		// Read the records  using an Card Applet Proxy.
	 		return new PacientInfo(healthCardProxy_.getPName(index), 
	 								healthCardProxy_.getPTitle(index), 
	 								healthCardProxy_.getPEmail(index), 
	 								healthCardProxy_.getPPhone(index), 
	 								healthCardProxy_.getPAddress(index),
	 								healthCardProxy_.getPMobile(index),
	 								healthCardProxy_.getPDateIssued(index),
	 								healthCardProxy_.getPDateUpdate(index),
	 								healthCardProxy_.getPGender(index),
	 								healthCardProxy_.getPCNP(index),
	 								healthCardProxy_.getPDob(index),
	 								healthCardProxy_.getPBloodType(index),
	 								healthCardProxy_.getPHName(index),
	 								healthCardProxy_.getPHAdmissionDate(index),
	 								healthCardProxy_.getPHOrganDonor(index),
	 								healthCardProxy_.getPDrugAllergies(index),
	 								healthCardProxy_.getPDrugMedications(index),
	 								healthCardProxy_.getPDiagnose(index),
	 								healthCardProxy_.getPDiseaseImm(index),
	 								healthCardProxy_.getPComments(index),
	 								healthCardProxy_.getPSurgicalProc(index)
	 								);
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		throw new HealthCardException(e.getMessage());
	 	} finally {
	 		// End mutual exclusion. From now on other Card Services may
	 		// access the card again.
	 		card_.endMutex();
	 	}
	 }
	 /**
	  * Sets the entry with the given index to the given <tt>PacientInfo</tt> object. 
	  *
	  * @param index        The index of the entry for which a
	  *                     <tt>PacientInfo</tt> object shall be written.
	  * @param pacientInfo The <tt>PacientInfo</tt> object to be written.
	  *
	  * @see health.PacientInfo
	  *
	  * @exception health.HealthCardException
	  *            Thrown when problems occurr while writing the info to  card.
	  */
	 public void setPacientInfo(int index, PacientInfo pacientInfo) 
	 throws HealthCardException {
	 	// Check for card presence.
	 	if (card_ == null)
	 		throw new HealthCardException("No card found.");
	 	try {
	 		// We need mutual exclusion here to prevent other CardServices from
	 		// modifying data on the card while we write the information records.
	 		card_.beginMutex();

	 		// Write the fields to the card using an AppletProxy.
	 		healthCardProxy_.setPName(index, pacientInfo.getName());
	 		healthCardProxy_.setPTitle(index, pacientInfo.getTitle());
	 		healthCardProxy_.setPEmail(index, pacientInfo.getEmail());
	 		healthCardProxy_.setPPhone(index, pacientInfo.getPhone());
	 		healthCardProxy_.setPMobile(index, pacientInfo.getMobile());
	 		healthCardProxy_.setPDateIssued(index, pacientInfo.getDateIssued());
	 		healthCardProxy_.setPDateUpdate(index, pacientInfo.getDateUpdate());
	 		healthCardProxy_.setPAddress(index, pacientInfo.getAddress());
	 		healthCardProxy_.setPGender(index, pacientInfo.getGender());
	 		healthCardProxy_.setPCNP(index, pacientInfo.getCNP());
	 		healthCardProxy_.setPDob(index, pacientInfo.getDob());
	 		healthCardProxy_.setPBloodType(index, pacientInfo.getBloodType());
	 		healthCardProxy_.setPHName(index, pacientInfo.getHName());
	 		healthCardProxy_.setPHAdmissionDate(index, pacientInfo.getHAdmissionDate());
	 		healthCardProxy_.setPHOrganDonor(index, pacientInfo.getHOrganDonor());
	 		healthCardProxy_.setPDrugAllergies(index, pacientInfo.getDrugAllergies());
	 		healthCardProxy_.setPDrugMedications(index, pacientInfo.getDrugMedications());
	 		healthCardProxy_.setPDiagnose(index, pacientInfo.getDiagnose());
	 		healthCardProxy_.setPDiseaseImm(index, pacientInfo.getDiseaseImm());
	 		healthCardProxy_.setPComments(index, pacientInfo.getComments());
	 		healthCardProxy_.setPSurgicalProc(index, pacientInfo.getSurgicalProc());
	 		
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		throw new HealthCardException(e.getMessage());
	 	} finally {
	 		// End mutual exclusion. From now on other CardServices may access
	 		// the card again. We make this call in the finally statement to ensure
	 		// that it is performed under all circumstances.
	 		card_.endMutex();
	 	}
	 }	 
}
