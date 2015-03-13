/**
 * 
 */
package health;

import java.util.Hashtable;
import opencard.core.terminal.CommandAPDU;
import opencard.core.terminal.ResponseAPDU;
import opencard.core.terminal.CardTerminalIOControl;
import opencard.core.terminal.CHVControl;
import opencard.core.terminal.CHVEncoder;
import opencard.core.terminal.CardTerminalException;
import opencard.core.service.SmartCard;
import opencard.core.service.CardChannel;
import opencard.core.service.CardServiceInvalidParameterException;
import opencard.core.service.CardServiceInvalidCredentialException;
import opencard.core.service.CardServiceOperationFailedException;
import opencard.core.service.CardServiceException;
import opencard.core.service.CardServiceScheduler;
import opencard.opt.service.CardServiceUnexpectedResponseException;
import opencard.opt.applet.AppletID;
import opencard.opt.applet.AppletProxy;
import opencard.core.util.Tracer;




/**
 * <tt>HealthCardProxy</tt> is a Card Applet Proxy for the HealthCard Applet 
 * for the JavaCard.
 *
 * It offers methods for convenient access to the information securely stored 
 * in the HealthCardApplet on the card. Before accessing data, it automatically asks
 * for the password using the OpenCard Framework's card holder verification mechanism.
 *
 * @see health.HealthCardApplet
 *
 *
 */
public class HealthCardProxy extends AppletProxy{
	
	  // Return codes.
	  protected final static int OK = 0x9000;
	  protected final static int INVALID_PASSWORD = 0x6A80;
	  protected final static int INDEX_OUT_OF_RANGE = 0x6A83;
	  // When the card holder verification fails, the card applet will return 
	  // javacard.framework.ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED = 0x6982
	  protected final static int CHV_FAILED = 0x6982;

	  // Field identifiers for Doctor.
	  protected final static int dNAME = 0;
	  protected final static int dTYPE = 1;
	  protected final static int dEMAIL = 4;
	  protected final static int dPHONE = 2;
	  protected final static int dMOBILE = 3;
	  
	  //Field identifiers for Pacient.
	  protected final static int pNAME = 0;
	  protected final static int pTITLE = 1;
	  protected final static int pEMAIL = 4;
	  protected final static int pPHONE = 2;
	  protected final static int pMOBILE = 3;
	  protected final static int pDATEISSUED = 5;
	  protected final static int pDATEUPDATE = 6;
	  protected final static int pADDRESS = 7;
	  protected final static int pGENDER = 8;
	  protected final static int pCNP = 9;
	  protected final static int pDOB = 10;
	  protected final static int pBLOODTYPE = 11;
	  protected final static int pHNAME = 12;
	  protected final static int pHADMISSIONDATE = 13;
	  protected final static int pHORGANDONOR = 14;
	  protected final static int pDRUGALLERGIES = 16;
	  protected final static int pDRUGMEDICATIONS = 15;
	  protected final static int pDIAGNOSE = 17;
	  protected final static int pDISEASEIMM = 18;
	  protected final static int pCOMMENTS = 20;
	  protected final static int pSURGICALPROC = 19;
	  
	  
	  
	  /** Reusable command APDU for getting a field. */
	  private CommandAPDU getFieldAPDU = new CommandAPDU(14);

	  /** Reusable command APDU for setting a field. */
	  private CommandAPDU setFieldAPDU = new CommandAPDU(255);

	  /** Application identifier of the HealthCard applet */
	  // VERIFICA 
	  private static final AppletID HEALTH_CARD_AID
		 = new AppletID(new byte[] {(byte) 0x42, (byte) 0x69, (byte) 0x7A,
	                                    (byte) 0x43, (byte) 0x61, (byte) 0x72, (byte) 0x64});

	  /** Tracer for logging. */
	  private Tracer iTracer = new Tracer(this, HealthCardProxy.class); 

	  /**
	   * Gets the state object of the health card Applet Proxy. Or, to be more exact, 
	   * the state object representing the state of the card applet to which the health
	   * card Applet Proxy is associated.
	   *
	   * @return The state object representing the state of the halth card Applet Proxy.
	   */
	  protected HealthCardState getHealthCardState(CardChannel channel) {
	  	return (HealthCardState) ((Hashtable) channel.getState()).get(HEALTH_CARD_AID);
	  }	  
	  
	  /**
	   * Create a <tt>HealthCardProxy</tt> instance.
	   *
	   * @param scheduler The Scheduler from which channels have to be obtained.
	   * @param card      The SmartCard object to which this service belongs.
	   * @param blocking  Currently not used.
	   *
	   * @throws opencard.core.service.CardServiceException
	   *         Thrown when instantiation fails.
	   */
	  protected void initialize(CardServiceScheduler scheduler, SmartCard card, boolean blocking) throws CardServiceException {
	  	super.initialize(HEALTH_CARD_AID, scheduler, card, blocking);
	  	System.out.println("HealthCardProxy.initialize");
	  	try {
	  		// Allocate the card channel. This gives us exclusive access to the card until we 
	  		// release the channel.
	  		allocateCardChannel();

	  		// Get the card state.
	  		Hashtable cardState = (Hashtable) getCardChannel().getState();

	  		// Get the health card applet state. If not already there, create it.
	  		HealthCardState state = (HealthCardState) cardState.get(HEALTH_CARD_AID);
	  		if (state == null) {
	  			state = new HealthCardState();
	  			state.setCHVPerformed(false);
	  			cardState.put(HEALTH_CARD_AID, state);
	  			System.out.println("HealthCardProxy.initialize - created HealthCardState");
	  		}
	  	} finally {
	  		releaseCardChannel();
	  	}
	  }
	  /**
	   * Performs Card Holder Verification.
	   *
	   * @param channel The card channel to be used.
	   * @param numCHV  The number of the CHV to be given to the card.
	   */
	  protected void performCHV(CardChannel channel, int numCHV) 
	  throws CardServiceInvalidCredentialException, CardServiceOperationFailedException, 
	  	   CardServiceUnexpectedResponseException, CardServiceException, CardTerminalException {
	  	// Class and instruction for VerifyCHV command.
	  	final byte[] DO_CHV_COMMAND_PREFIX = {(byte) 0x80, (byte) 0x01}; //0x8001 
	  	final byte[] PLACEHOLDER = new byte[8];

	  	// Set up the command APDU.
	  	CommandAPDU doCHVAPDU = new CommandAPDU(13);
	  	doCHVAPDU.append(DO_CHV_COMMAND_PREFIX); // Class, Instruction - CLA + INS : 80 + 01 
	  	doCHVAPDU.append((byte) numCHV); // CHV number P1-0x01
	  	doCHVAPDU.append((byte) 0x00); // Reserved P2 - 0x00
	  	doCHVAPDU.append((byte) 0x08); // Reserved LC 0x08
	  	doCHVAPDU.append(PLACEHOLDER); // Placeholder for password to be filled in.

	  	System.out.println("---------- Health Card CHV ----------");

	  	// Let the card terminal ask for the password. If it is unable to do it,
	  	// OpenCard will use the CHVDialog set in the service.
	  	CardTerminalIOControl ioctrl = new CardTerminalIOControl(8, 30, null, null);
	  	CHVControl chvctrl = new CHVControl("Enter your Card password", numCHV, CHVEncoder.STRING_ENCODING, 0, ioctrl);
	  	ResponseAPDU response = sendVerifiedAPDU(channel, doCHVAPDU, chvctrl, -1);
	  	switch (response.sw() & 0xFFFF) {
	  		case OK :
	  			// The card applet keeps in mind that we now have performed
	  			// the CHV. Our state must reflect this.
	  			getHealthCardState(getCardChannel()).setCHVPerformed(true);
	  			return;
	                  case INVALID_PASSWORD :
	                  case CHV_FAILED :
	  			throw new CardServiceInvalidCredentialException("Card Holder Verification failed.");
	  		default :
	  			throw new CardServiceUnexpectedResponseException("RC = " + response.sw());
	  	}
	  }
	  /*************************************************************
	   *************************DOCTOR*****************************/	   
	  
	  /**
	  * Gets the E-mail address field from entry with the given index.
	  *
	  * @param index The index of the entry, from which the E-mail address shall be obtained.
	  *
	  * @return The E-mail address as a string.
	  */
	 public String getDEmail(int index) throws CardServiceException, CardTerminalException {
	 	return getField(index, dEMAIL);
	 }	  
	 /**
	  * Set the E-mail address field in the entry with the given index.
	  *
	  * @param index   The index of the entry in which the e-mail address shall be set.
	  * @param address The new E-mail address.
	  */
	 public void setDEmail(int index, String email) throws CardServiceException, CardTerminalException {
	 	setField(index, dEMAIL, email);
	 }
	 /**
	  * Gets the name field from entry with the given index.
	  *
	  * @param index The index of the entry, from which the name shall be obtained.
	  *
	  * @return The name as a string.
	  */
	 public String getDName(int index) throws CardServiceException, CardTerminalException {
	 	return getField(index, dNAME);
	 }
	 /**
	  * Gets the phone number field from entry with the given index.
	  *
	  * @param index The index of the entry, from which the phone number shall be obtained.
	  *
	  * @return The phone number as a string.
	  */
	 public String getDPhone(int index) throws CardServiceException, CardTerminalException {
	 	return getField(index, dPHONE);
	 }
	 /**
	  * Gets the title field from entry with the given index.
	  *
	  * @param index The index of the entry, from which the title shall be obtained.
	  *
	  * @return The title as a string.
	  */
	 public String getDType(int index) throws CardServiceException, CardTerminalException {
	 	return getField(index, dTYPE);
	 }
	 /**
	  * Set the name field in the entry with the given index.
	  *
	  * @param index   The index of the entry in which the name shall be set.
	  * @param name    The new name.
	  */
	 public void setDName(int index, String name) throws CardServiceException, CardTerminalException {
	 	setField(index, dNAME, name);
	 }
	 /**
	  * Set the phone field in the entry with the given index.
	  *
	  * @param index The index of the entry in which the phone number shall be set.
	  * @param phone The phone number address.
	  */
	 public void setDPhone(int index, String phone) throws CardServiceException, CardTerminalException {
	 	setField(index, dPHONE, phone);
	 }
	 /**
	  * Set the title field in the entry with the given index.
	  *
	  * @param index   The index of the entry in which the title shall be set.
	  * @param address The new title.
	  */
	 public void setDType(int index, String type) throws CardServiceException, CardTerminalException {
	 	setField(index, dTYPE, type);
	 }
	 
	 public String getDMobile (int index) throws CardServiceException, CardTerminalException {
		 return getField(index,dMOBILE);
	 }
	 public void setDMobile (int index, String mobile ) throws CardServiceException, CardTerminalException { 
		 setField (index,dMOBILE, mobile);
	 }
	 /*************************************************************
	   *************************PACIENT***************************/
	 /**
	  * Gets the E-mail address field from entry with the given index.
	  *
	  * @param index The index of the entry, from which the E-mail address shall be obtained.
	  *
	  * @return The E-mail address as a string.
	  */
	 public String getPEmail(int index) throws CardServiceException, CardTerminalException {
	 	return getField(index, pEMAIL);
	 }	  
	 /**
	  * Set the E-mail address field in the entry with the given index.
	  *
	  * @param index   The index of the entry in which the e-mail address shall be set.
	  * @param address The new E-mail address.
	  */
	 public void setPEmail(int index, String email) throws CardServiceException, CardTerminalException {
	 	setField(index, pEMAIL, email);
	 }
	 /**
	  * Gets the name field from entry with the given index.
	  *
	  * @param index The index of the entry, from which the name shall be obtained.
	  *
	  * @return The name as a string.
	  */
	 public String getPName(int index) throws CardServiceException, CardTerminalException {
	 	return getField(index, pNAME);
	 }
	 /**
	  * Gets the phone number field from entry with the given index.
	  *
	  * @param index The index of the entry, from which the phone number shall be obtained.
	  *
	  * @return The phone number as a string.
	  */
	 public String getPPhone(int index) throws CardServiceException, CardTerminalException {
	 	return getField(index, pPHONE);
	 }
	 /**
	  * Gets the title field from entry with the given index.
	  *
	  * @param index The index of the entry, from which the title shall be obtained.
	  *
	  * @return The title as a string.
	  */
	 public String getPTitle(int index) throws CardServiceException, CardTerminalException {
	 	return getField(index, pTITLE);
	 }
	 /**
	  * Set the name field in the entry with the given index.
	  *
	  * @param index   The index of the entry in which the name shall be set.
	  * @param name    The new name.
	  */
	 public void setPName(int index, String name) throws CardServiceException, CardTerminalException {
	 	setField(index, pNAME, name);
	 }
	 /**
	  * Set the phone field in the entry with the given index.
	  *
	  * @param index The index of the entry in which the phone number shall be set.
	  * @param phone The phone number address.
	  */
	 public void setPPhone(int index, String phone) throws CardServiceException, CardTerminalException {
	 	setField(index, pPHONE, phone);
	 }
	 /**
	  * Set the title field in the entry with the given index.
	  *
	  * @param index   The index of the entry in which the title shall be set.
	  * @param address The new title.
	  */
	 public void setPTitle(int index, String title) throws CardServiceException, CardTerminalException {
	 	setField(index, pTITLE, title);
	 }
	 
	 public String getPMobile (int index) throws CardServiceException, CardTerminalException {
		 return getField(index,pMOBILE);
	 }
	 public void setPMobile (int index, String mobile ) throws CardServiceException, CardTerminalException { 
		 setField (index,pMOBILE, mobile);
	 }
	 /** dateIssued */ 
	 public String getPDateIssued (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pDATEISSUED);
	 }
	 /** dateIssued */
	 public void setPDateIssued (int index, String dateIssued) throws CardServiceException, CardTerminalException {
		 setField(index,pDATEISSUED,dateIssued );
	 }
	 /** dateUpdate */ 
	 public String getPDateUpdate (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pDATEUPDATE);
	 }
	 /** dateUpdate */
	 public void setPDateUpdate (int index, String dateUpdate) throws CardServiceException, CardTerminalException {
		 setField(index,pDATEUPDATE,dateUpdate );
	 }
	 /** address */ 
	 public String getPAddress (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pADDRESS);
	 }
	 /** address */
	 public void setPAddress (int index, String address) throws CardServiceException, CardTerminalException {
		 setField(index,pADDRESS,address );
	 }
	 /** gender */ 
	 public String getPGender (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pGENDER);
	 }
	 /** gender */
	 public void setPGender (int index, String gender) throws CardServiceException, CardTerminalException {
		 setField(index,pGENDER,gender);
	 }
	 /** CNP */ 
	 public String getPCNP (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pCNP);
	 }
	 /** CNP */
	 public void setPCNP (int index, String cnp) throws CardServiceException, CardTerminalException {
		 setField(index,pCNP,cnp);
	 }
	 /** dob */ 
	 public String getPDob  (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pDOB);
	 }
	 /** dob */
	 public void setPDob (int index, String dob) throws CardServiceException, CardTerminalException {
		 setField(index,pDOB,dob );
	 }
	 /** bloodType */ 
	 public String getPBloodType (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pBLOODTYPE);
	 }
	 /** bloodType*/
	 public void setPBloodType (int index, String bloodType) throws CardServiceException, CardTerminalException {
		 setField(index,pBLOODTYPE,bloodType );
	 }
	 /** hName */ 
	 public String getPHName (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pHNAME);
	 }
	 /** hNAME */
	 public void setPHName (int index, String hName) throws CardServiceException, CardTerminalException {
		 setField(index,pHNAME, hName );
	 }
	 /**  */ 
	 public String getPHAdmissionDate (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pHADMISSIONDATE);
	 }
	 /**  */
	 public void setPHAdmissionDate (int index, String hAdmissionDate) throws CardServiceException, CardTerminalException {
		 setField(index,pHADMISSIONDATE,hAdmissionDate );
	 }
	 /**  */ 
	 public String getPHOrganDonor (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pHORGANDONOR);
	 }
	 /**  */
	 public void setPHOrganDonor (int index, String hOrganDonor) throws CardServiceException, CardTerminalException {
		 setField(index,pHORGANDONOR, hOrganDonor );
	 }
	 /**  */ 
	 public String getPDrugMedications (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pDRUGMEDICATIONS);
	 }
	 /**  */
	 public void setPDrugMedications (int index, String drugMedications) throws CardServiceException, CardTerminalException {
		 setField(index,pDRUGMEDICATIONS,drugMedications );
	 }
	 
	 public String getPDrugAllergies (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pDRUGALLERGIES);
	 }
	 /**  */
	 public void setPDrugAllergies (int index, String drugAllergies) throws CardServiceException, CardTerminalException {
		 setField(index,pDRUGALLERGIES,drugAllergies );
	 }
	 
	 public String getPDiagnose (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pDIAGNOSE);
	 }
	 /**  */
	 public void setPDiagnose (int index, String diagnose) throws CardServiceException, CardTerminalException {
		 setField(index,pDIAGNOSE,diagnose );
	 }
	 
	 public String getPComments (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pCOMMENTS);
	 }
	 /**  */
	 public void setPComments (int index, String comments) throws CardServiceException, CardTerminalException {
		 setField(index,pCOMMENTS,comments );
	 }
	 
	 public String getPSurgicalProc (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pSURGICALPROC);
	 }
	 /**  */
	 public void setPSurgicalProc (int index, String surgicalProc) throws CardServiceException, CardTerminalException {
		 setField(index,pSURGICALPROC, surgicalProc );
	 }
	 
	 public String getPDiseaseImm (int index) throws CardServiceException, CardTerminalException {
		 return getField (index,pDISEASEIMM);
	 }
	 /**  */
	 public void setPDiseaseImm (int index, String diseaseImm) throws CardServiceException, CardTerminalException {
		 setField(index,pDISEASEIMM,diseaseImm );
	 }
	 /****** end pacient get + set  */

	 /**
	  * Gets a field from entry with the given index.
	  *
	  * @param index  The index of the entry from which a field shall be obtained.
	  * @param field  The field ID of the field to be obtained.
	  *
	  * @return The field contents.
	  */
	 protected String getField(int index, int field) 
	 throws CardServiceInvalidCredentialException, CardServiceOperationFailedException, 
	 	   CardServiceInvalidParameterException, CardServiceUnexpectedResponseException, 
	 	   CardServiceException, CardTerminalException {
	 	// Class and instruction byte for the get field command.
	 	final byte[] GET_FIELD_COMMAND_PREFIX = {(byte) 0x80, (byte) 0x02};
	 	try {
	 		allocateCardChannel();
	                 iTracer.debug("getField", "CardChannel allocated");

	 		// Perform Card Holder Verification if necessary
	 		CardChannel cc = getCardChannel();
	 		if (!getHealthCardState(cc).isCHVPerformed()) {
	 			performCHV(cc, 1);
	 		}

	 		// Set up the command APDU and send it to the card.
	 		getFieldAPDU.setLength(0);
	 		getFieldAPDU.append(GET_FIELD_COMMAND_PREFIX); //CLA+INS
	 		getFieldAPDU.append((byte) index); // Info Set Index : 00 sau 01 
	 		getFieldAPDU.append((byte) field); // Field identifier
	 		getFieldAPDU.append((byte) 0x00); // Lc
	 		getFieldAPDU.append((byte) 0x00); // Le

	 		// Send command APDU and check the response.
	 		ResponseAPDU response = sendCommandAPDU(getCardChannel(), HEALTH_CARD_AID, 
	 												getFieldAPDU);
	 		switch (response.sw() & 0xFFFF) {
	 			case OK :
	 				return new String(response.data());
	 			case INDEX_OUT_OF_RANGE :
	 				throw new CardServiceInvalidParameterException("Index out of range");
	 			default :
	 				throw new CardServiceUnexpectedResponseException("sw = 0x" + 
	 					Integer.toHexString((short) (response.sw() & 0xFFFF)));
	 		}
	 	} finally {     
	             releaseCardChannel();
	             iTracer.debug("getField", "CardChannel released");
	 	}
	 }	 
	 /**
	  * Set a field in the entry with the given index.
	  *
	  * @param index The index of the entry in which a field shall be set.
	  * @param field The field ID of the field to be set.
	  * @param value The new value.
	  */
	 protected void setField(int index, int field, String value)
	 throws CardServiceInvalidCredentialException, CardServiceOperationFailedException,
	 	   CardServiceInvalidParameterException, CardServiceUnexpectedResponseException,
	 	   CardTerminalException, CardServiceException {

	   // Class and instruction for the SetField Command
	   final byte[] SET_NAME_COMMAND_PREFIX = {(byte) 0x80, (byte) 0x03};
	   try {
	 	allocateCardChannel();
	         iTracer.debug("setField", "CardChannel allocated");

	 	// Perform Card Holder Verification if necessary
	 	CardChannel cc = getCardChannel();
	 	if (!getHealthCardState(cc).isCHVPerformed()) {
	 	  performCHV(cc, 1);
	 	}

	 	// Set up the command APDU and send it to the card.
	 	setFieldAPDU.setLength(0);
	 	setFieldAPDU.append(SET_NAME_COMMAND_PREFIX); // Class, Instruction
	 	setFieldAPDU.append((byte) index); // Pacient or Doctor Info Index
	 	setFieldAPDU.append((byte) field); // Field identifier
	 	setFieldAPDU.append((byte) value.length()); // Lc
	 	setFieldAPDU.append(value.getBytes()); // Data

	 	ResponseAPDU response = sendCommandAPDU(cc,	HEALTH_CARD_AID, setFieldAPDU);

	 	switch (response.sw() & 0xFFFF) {
	 	  case OK :
	 		return;
	 	  case INDEX_OUT_OF_RANGE :
	 		throw new CardServiceInvalidParameterException("Index out of range");
	 	  default :
	 		 throw new CardServiceUnexpectedResponseException("RC=" + response.sw());
	 	}
	   } finally {
	 	releaseCardChannel();
	         iTracer.debug("setField", "CardChannel released");
	   }
	 }	 
	 
}
