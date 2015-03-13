/* 
 */
package healthcard;

import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.APDU;
import javacard.framework.OwnerPIN;
import javacard.framework.Util;
import javacard.security.KeyBuilder;
import javacard.security.MessageDigest;
import javacard.security.RSAPrivateKey;
import javacard.security.RSAPublicKey;
import javacard.security.Signature;
import javacardx.crypto.Cipher;
import javacard.framework.*;
import javacard.security.*;

/**
 * @author Gaby Popovici
 * 
 */
public class HealthCardApplet extends Applet {
	/**
	 * CLA clasa de instructiuni - 80
	 */
	final static byte MY_CLA = (byte) 0x80;

	/**
	 * INS instructiuni INS
	 */
	final static byte _INS = (byte) 0;

	final static byte LOAD_PUBLIC_M = (byte) 1;

	final static byte LOAD_PUBLIC_E = (byte) 2;

	final static byte STORE_INS = (byte) 3;

	final static byte VERIFY_INS = (byte) 4;

	final static byte ENCRYPT_INS = (byte) 5;

	final static byte SET_DATA_INS = (byte) 6;

	final static byte GET_DATA_INS = (byte) 7;

	final static byte DELETE_INS = (byte) 8;

	final static byte UPDATE_INS = (byte) 9;

	final static byte STORE_PHOTO = (byte) 10;

	final static byte SELECT_INS = (byte) 0xA4;

	private static final byte GET_PHOTO = (byte) 11;

	private static final byte RESET = (byte) 12;

	private static final byte LENGTH_INS = (byte) 13;

	private static final byte LISTA = (byte) 14;

	private static final byte DISPLAY = (byte) 15;

	private static final byte DELETE_KEY = (byte) 16;

	/**
	 * P1 alegerea inregistrarii pacient(1) sau doctor (0) tipul cheii incarcate
	 */
	final static byte D_RECORD = (byte) 0;

	final static byte P_RECORD = (byte) 1;

	final static byte RSA512PUB_ALG = (byte) 2;

	/**
	 * P2 cimpurile Doctorului 00 name,01 type,phone 03... cimpurile Pacientului
	 * 00 name, 01 title,02 phone...0Abloodtype
	 */

	// final static byte PIN_TRY_LIMIT = (byte) 3;
	// final static byte MAX_PIN_SIZE = (byte) 8;
	/**
	 * SW
	 * 
	 */
	final static short SW_SIGNATURE_FAILED = 0x6A85;

	final static short SW_PUBLICKEY_FAILED = 0x6A99;

	final static short SW_DATA_STORE_FAILED = 0x6A98;

	final static short SW_LIST_NOT_IMPLEMENTED = 0x6A96;

	final static short SW_KEY_NOT_FOUND = 0x6A95;

	final static short SW_DELETE_DATA_FAILED = 0x6A97;

	/**
	 * member variables A Healthcard has two records.
	 * 
	 * 
	 */
	private Doctor doctorRecord;

	private Pacient pacientRecord;

	private LinkList listVaccin;// 01

	private LinkList listAlergie;// 02

	private LinkList listCronic;// 03

	private LinkList listTratament;// 04

	public Link current;

	public Link previous;

	private RSAPrivateKey m_priv512_rsakey;

	private RSAPublicKey m_pub512_rsakey;

	private Signature m_signature;

	private Cipher m_rsa_ciph;

	private MessageDigest sha;

	/** Temporary buffer in RAM. */
	byte[] cache;

	short cacheSize = (short) 50;

	short cachelength;

	byte[] digest;

	byte[] photo;

	short offset = 0;

	short offset_read = 0;

	short offset_write = 0;

	/** The applet state (INIT or ISSUED). */
	byte state;

	/**
	 * The OwnerPin must be matched before this applet cooperates private
	 * OwnerPIN pin_ = new OwnerPIN(PIN_TRY_LIMIT, MAX_PIN_SIZE);
	 */

	/**
	 * Constructor
	 * 
	 */
	protected HealthCardApplet(/* byte[] pin_passed */) {
		super();
		doctorRecord = new Doctor();
		pacientRecord = new Pacient();
		listVaccin = new LinkList();
		listAlergie = new LinkList();
		listCronic = new LinkList();
		listTratament = new LinkList();

		photo = new byte[5120];// creez un tablou de 4 kb in EEPROM
		/*
		 * constructia PIN ului byte[] pin = new byte[pin_passed.length];
		 * Util.arrayCopy(pin_passed, (short)0, pin, (short)0,
		 * (short)pin_passed.length); pin_.update(pin, (short) 0,
		 * (byte)pin.length);
		 */
		/* constructia cheii publice */
		m_pub512_rsakey = (RSAPublicKey) KeyBuilder.buildKey(
				KeyBuilder.TYPE_RSA_PUBLIC, KeyBuilder.LENGTH_RSA_512, false);
		/* constructia semnaturii */
		m_signature = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
		/* constructia cifrului pentru criptare/decriptare */
		m_rsa_ciph = Cipher.getInstance(Cipher.ALG_RSA_PKCS1, false);
		/* constructia amprentei mesajului */
		sha = MessageDigest.getInstance(MessageDigest.ALG_SHA, false);
		/* constrcutia unui tablou de octeti reutilizabil in RAM */
		cache = JCSystem.makeTransientByteArray(cacheSize,
				JCSystem.CLEAR_ON_DESELECT);
		digest = JCSystem.makeTransientByteArray((short) 20,
				JCSystem.CLEAR_ON_DESELECT);
	}

	public static void install(byte[] bArray, short bOffset, byte bLength) {
		// GP-compliant JavaCard applet registration
		new HealthCardApplet().register(bArray, (short) (bOffset + 1),
				bArray[bOffset]);
	}

	/**
	 * Processes incoming APDUs. When a Java Card Applet receives an APDU, it
	 * calls this method to process it.
	 * 
	 * @param apdu
	 *            The APDU to be processed.
	 */
	public void process(APDU apdu) throws ISOException {

		// Good practice: Return 9000 on SELECT
		if (selectingApplet()) {
			return;
		}
		byte[] buffer = apdu.getBuffer();
		if ((buffer[ISO7816.OFFSET_CLA] != (byte) 0x80)
				&& (buffer[ISO7816.OFFSET_CLA] != (byte) 0x00))
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);

		// Dispatch commands depending on
		// .. class and instruction bytes.
		switch (buffer[ISO7816.OFFSET_INS]) {

		case LOAD_PUBLIC_M:
			modulus(apdu);
			break;
		case LOAD_PUBLIC_E:
			exponent(apdu);
			break;
		case STORE_INS: // stochez temporar date
			cache(apdu);
			break;
		case ENCRYPT_INS:
			encrypt(apdu);
			break;
		case VERIFY_INS:
			short result = verify(apdu, cache);
			apdu.setOutgoingAndSend((short) 0, (short) result);
			break;
		case SELECT_INS:
			selectApplet(apdu);
			break;
		case SET_DATA_INS:
			setRecord(apdu);
			break;
		case GET_DATA_INS:
			getRecord(apdu);
			break;
		case DELETE_INS:
			delete(apdu);
			break;
		case UPDATE_INS:
			update(apdu);
			break;
		case STORE_PHOTO:
			storePhoto(apdu);
			break;
		case GET_PHOTO:
			getPhoto(apdu);
			break;
		case RESET:
			reset(apdu);
			break;
		case LENGTH_INS:
			getLength(apdu);
			break;
		case LISTA:
			insert(apdu);
			break;
		case DISPLAY:
			display(apdu);
			break;
		case DELETE_KEY:
			deletekey(apdu);
			break;
		default:
			// good practice: If you don't know the INStruction, say so:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
			break;
		}
	}
	/**
	 * criptez datele cu cheia publica 
	 */
	private void encrypt(APDU apdu) {
		// TODO Auto-generated method stub
		byte[] buffer = apdu.getBuffer();
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x80)
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		if (buffer[ISO7816.OFFSET_INS] != ENCRYPT_INS) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		short Lc = (short) ((short) 0x00FF & buffer[4]); // LC
		short read_count = apdu.setIncomingAndReceive();
		short d0 = (short) ISO7816.OFFSET_CDATA;
		if (!m_pub512_rsakey.isInitialized()) {// cheia publica nu este
												// initializata
			ISOException.throwIt(SW_PUBLICKEY_FAILED);
		}
		//m_signature.init(m_pub512_rsakey, Signature.MODE_SIGN);
		m_rsa_ciph.init(m_pub512_rsakey, Cipher.MODE_ENCRYPT);
		short Licc = m_rsa_ciph.doFinal(buffer, d0, Lc, // Input for RSA sign
				buffer, (short) 0);
		apdu.setOutgoingAndSend((short) 0, Licc);
	}

	private void deletekey(APDU apdu) {
		// TODO Auto-generated method stub
		byte[] buffer = apdu.getBuffer();
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x80)
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		if (buffer[ISO7816.OFFSET_INS] != DELETE_KEY) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		short lc = (short) ((short) 0x00FF & buffer[4]); // LC
		short read_count = apdu.setIncomingAndReceive();
		/*
		 * byte[] tmp = new byte[read_count];
		 * Util.arrayCopy(buffer,(short)ISO7816.OFFSET_CDATA,tmp,(short)0,(short)read_count);
		 */

		LinkList list = null;
		switch (buffer[ISO7816.OFFSET_P1]) {
		case (byte) 1:
			list = listVaccin;
			break;
		case (byte) 2:
			list = listAlergie;
			break;
		case (byte) 3:
			list = listCronic;
			break;
		case (byte) 4:
			list = listTratament;
			break;
		default: // Index is out of bounds
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		if (verify(apdu, cache) == 0) {
			current = list.first;
			previous = list.first;
			while (Util.arrayCompare(cache, (short) 0, current.iData.data,
					(short) 0, cachelength) != 0) {
				if (current.next == null) {
					ISOException.throwIt(SW_KEY_NOT_FOUND);
				} else {// avanseaza la urmatoarea legatura
					previous = current;
					current = current.next;
				}
			} // elementul a fost gasit
			if (current == list.first) {// daca este primul
				list.first = list.first.next; // capul listei se modifica
			} else {
				previous.next = current.next; // se ocoleste elementul sters
			}
		} else {
			ISOException.throwIt(SW_DELETE_DATA_FAILED);
		}

		apdu.setOutgoingAndSend((short) 0, (short) 0);
	}
	/**
	 * Procedua de citire a listelor simplu inlantuite 
	 * @param apdu
	 * @throws APDUException
	 * @throws SecurityException
	 */
	private void display(APDU apdu) throws APDUException, SecurityException {
		// TODO Auto-generated method stub
		byte[] buffer = apdu.getBuffer();
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x80)
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		if (buffer[ISO7816.OFFSET_INS] != DISPLAY) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		short le = apdu.setOutgoing();
		LinkList list = null;
		switch (buffer[ISO7816.OFFSET_P1]) {
		case (byte) 1:
			list = listVaccin;
			break;
		case (byte) 2:
			list = listAlergie;
			break;
		case (byte) 3:
			list = listCronic;
			break;
		case (byte) 4:
			list = listTratament;
			break;
		default: // Index is out of bounds
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		current = list.first;
		short offset = 0;
		while (current != null) {
			Util.arrayCopy(current.iData.data, (short) 0, buffer,
					(short) offset, current.iData.length);
			offset = (short) (offset + current.iData.length);
			current = current.next;// avansez la urmatoarea legatura
		}
		apdu.setOutgoingLength(offset);
		apdu.sendBytes((short) 0, (short) offset);
	}
	/**
	 * Insertia unei noi inregistrati se face 
	 * in capul listei 
	 * @param apdu
	 */
	private void insert(APDU apdu) {
		// TODO Auto-generated method stub
		byte[] buffer = apdu.getBuffer();
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x80)
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		if (buffer[ISO7816.OFFSET_INS] != LISTA) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		short lc = (short) ((short) 0x00FF & buffer[4]); // dimensiunea datei
		short read_count = apdu.setIncomingAndReceive();
		/*
		 * byte[] tmp = new byte[read_count];
		 * Util.arrayCopy(buffer,(short)ISO7816.OFFSET_CDATA,tmp,(short)0,read_count) ;
		 */

		LinkList list = null;
		switch (buffer[ISO7816.OFFSET_P1]) {
		case (byte) 1:
			list = listVaccin;
			break;
		case (byte) 2:
			list = listAlergie;
			break;
		case (byte) 3:
			list = listCronic;
			break;
		case (byte) 4:
			list = listTratament;
			break;
		default: // Index is out of bounds
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		if (verify(apdu, cache) == 0) {
			list.insert(cache, cachelength);
		} else {
			ISOException.throwIt(SW_DATA_STORE_FAILED);
		}

		apdu.setOutgoingAndSend((short) 0, (short) 0);// send OK
	}
	/**
	 * intoarce ultima pozitie inserata in tabloul poza
	 * poza nu depaseste 4KB  
	 * @param apdu
	 */
	private void getLength(APDU apdu) {
		// TODO Auto-generated method stub
		byte[] buffer = apdu.getBuffer();
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x80)
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		if (buffer[ISO7816.OFFSET_INS] != LENGTH_INS) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		if (offset_write != 0) {
			short le = apdu.setOutgoing();
			apdu.setOutgoingLength((byte) 2);
			Util.setShort(buffer, (short) 0, offset_write);
			apdu.sendBytes((short) 0, (short) 2);
		} else {
			apdu.setOutgoingAndSend((short) 0, (short) 0);
		}
	}

	private void reset(APDU apdu) {
		// TODO Auto-generated method stub
		byte[] buffer = apdu.getBuffer();
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x80)
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		if (buffer[ISO7816.OFFSET_INS] != RESET) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		offset = 0;
		offset_read = 0;
		apdu.setOutgoingAndSend((short) 0, (short) 0);
	}
	/**
	 * Procedura de intoarcere a pozei
	 * @param apdu
	 */
	private void getPhoto(APDU apdu) {
		// TODO Auto-generated method stub
		byte[] buffer = apdu.getBuffer();
		// Check class byte. Only 0x80 is allowed here.
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x80)
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		if (buffer[ISO7816.OFFSET_INS] != GET_PHOTO) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}

		// Le currently
		short le = apdu.setOutgoing();
		apdu.setOutgoingLength((short) le);
		apdu.sendBytesLong(photo, (short) offset_read, (short) le);
		offset_read = (short) (offset_read + le);
	}
	/**
	 * Stocheaza bucati de 256 de octeti in tanloul photo
	 * offset - pozitia curenta in tablou
	 * @param apdu
	 */
	private void storePhoto(APDU apdu) {
		// TODO Auto-generated method stub
		byte[] buffer = apdu.getBuffer();
		short lc = (short) ((short) 0x00FF & buffer[4]);
		// Check class byte. Only 0x80 is allowed here.
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x80) {
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		}
		if (buffer[ISO7816.OFFSET_INS] != STORE_PHOTO) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		// pacientRecord.setRecord(buffer[ISO7816.OFFSET_P2], buffer,
		// (short)ISO7816.OFFSET_CDATA, (short)lc);
		// copiaza cimpul data pe pozitia 5 din buffer
		// in offset photo
		Util.arrayCopy(buffer, (short) ISO7816.OFFSET_CDATA, photo, offset, lc);
		// noul offset este vechiul offset la care se aduna lc
		offset = (short) (offset + lc); // 0 +255 = 255 , 255 + 255 ,
		offset_write = offset;
		// Return successfully, passing no data:
		apdu.setOutgoingAndSend((short) 0, (short) 0);
	}

	/**
	 * Seteaza modulul cheii publice
	 * 
	 * @author gabrielp
	 * @param apdu
	 * @throws CryptoException @
	 */
	private void modulus(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short Lc = (short) (buffer[ISO7816.OFFSET_LC] & 0xff);
		short d0 = (short) ISO7816.OFFSET_CDATA;
		// INS:01
		if (buffer[ISO7816.OFFSET_INS] != LOAD_PUBLIC_M) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		// P1:02
		if (buffer[ISO7816.OFFSET_P1] != RSA512PUB_ALG) {
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		// P2:00
		if (buffer[ISO7816.OFFSET_P2] != (byte) 0) {
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		// Lc(dimensiunea Modulului)||Data
		m_pub512_rsakey.setModulus(buffer, d0, Lc);
		// return;
	}// end Modulus

	/**
	 * Seteaza exponentul cheii publice
	 * 
	 * @author gabrielp
	 * @param apdu
	 * @throws CryptoException
	 */
	private void exponent(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short Lc = (short) (buffer[ISO7816.OFFSET_LC] & 0xff);
		short d0 = (short) ISO7816.OFFSET_CDATA;
		// INS:02
		if (buffer[ISO7816.OFFSET_INS] != LOAD_PUBLIC_E) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		// P1:02
		if (buffer[ISO7816.OFFSET_P1] != RSA512PUB_ALG) {
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		// P2:00
		if (buffer[ISO7816.OFFSET_P2] != (byte) 0) {
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		// Lc(dimensiunea Modulului)||Data
		m_pub512_rsakey.setExponent(buffer, d0, Lc);
	}// end exponent

	/**
	 * Fiecare mesaj sosit este stocat temporar in RAM
	 * 
	 * @see verify()
	 * @author gabrielp
	 * @param apdu
	 *            INS 03
	 */
	public void cache(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short Lc = (short) (buffer[ISO7816.OFFSET_LC] & 0xff);
		short d0 = (short) ISO7816.OFFSET_CDATA;
		// INS:03
		if (buffer[ISO7816.OFFSET_INS] != STORE_INS) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		// P1:00
		if (buffer[ISO7816.OFFSET_P1] != (byte) 0) {
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		// P2:00
		if (buffer[ISO7816.OFFSET_P2] != (byte) 0) {
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		cachelength = Lc;
		Util.arrayCopy(buffer, d0, cache, (short) 0, (short) cache.length);
	}// END store message to cache byte array

	/**
	 * Se verifica semnatura generata de aplicatia client in 3 pasi Pasul 1: se
	 * aplica cheia publica Pasul 2: se verifica SHA1 Pasul 3: se verifica
	 * amprentele celor 2 mesaje
	 * primii 16 octeti din decriptarea semnaturii
	 * sunt 
	 * @param apdu
	 * @param cache
	 * @see cahe()
	 * @return result or SW_SIGNATURE_FAILED INS 04
	 */
	public short verify(APDU apdu, byte[] cache) {
		/* variabile locale */
		short result = 0;
		short dResult = 0;
		final byte[] sha_der = { (byte) 0x30, (byte) 0x21, (byte) 0x30,
				(byte) 0x09, (byte) 0x06, (byte) 0x05, (byte) 0x2b,
				(byte) 0x0e, (byte) 0x03, (byte) 0x02, (byte) 0x1a,
				(byte) 0x05, (byte) 0x00, (byte) 0x04, (byte) 0x14 };

		byte[] buffer = apdu.getBuffer();
		short Lc = (short) (buffer[ISO7816.OFFSET_LC] & 0xff);
		short d0 = (short) ISO7816.OFFSET_CDATA;

		byte[] desig = new byte[Lc];
		if (!m_pub512_rsakey.isInitialized()) {
			ISOException.throwIt(SW_PUBLICKEY_FAILED);
		}
		// se aplica decriptarea semnaturii cu cheia publica
		m_rsa_ciph.init(m_pub512_rsakey, Cipher.MODE_DECRYPT);

		// decriptam semnatura: rezultatul il pastram in desig
		m_rsa_ciph.doFinal(buffer, d0, Lc, desig, (short) 0);
		// se calculeaza amprenata mesaj cache stocat anterior
		dResult = sha.doFinal(cache, (short) 0, (short) (cachelength), digest,
				(short) 0);
		// *** BEGIN VERIF
		// verifica primii 16 octeti - 128 de biti - SHA1
		byte verif16 = Util.arrayCompare(desig, (short) 0, sha_der, (short) 0,
				(short) 15);
		short hash_offset;
		if (verif16 == (byte) 0) { // daca sunt identice
			hash_offset = (short) 15;
		} else {
			hash_offset = (short) 0;
		}
		// rezultat final
		result = Util.arrayCompare(desig, hash_offset, digest, (short) 0,
				(short) digest.length);
		if (result != (short) 0) {
			ISOException.throwIt(SW_SIGNATURE_FAILED);
		}
		// *** END Verif
		return result;
	}

	/**
	 * Seteaza o inregistrare INS 06
	 * 
	 * @param apdu
	 *            The SetRecord Command APDU to be processed.
	 */
	private void setRecord(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short lc = (short) ((short) 0x00FF & buffer[4]);

		// Check class byte. Only 0x80 is allowed here.
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x80) {
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		}
		if (buffer[ISO7816.OFFSET_INS] != SET_DATA_INS) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		// Check if the CHV has already been verified.
		/*
		 * if (pin_.isValidated() != true)
		 * ISOException.throwIt(ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED);
		 */

		// Determine which information to update
		Doctor dRecord = null;
		Pacient pRecord = null;

		switch (buffer[ISO7816.OFFSET_P1]) {
		case D_RECORD:
			dRecord = doctorRecord;
			break;
		case P_RECORD:
			pRecord = pacientRecord;
			break;
		default: // Index is out of bounds
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}

		if (verify(apdu, cache) == (short) 0) {
			// Set the Record to the value transmitted in the APDU.
			if (dRecord != null) {
				dRecord.setRecord(buffer[ISO7816.OFFSET_P2], cache, (short) 0,
						(short) cachelength);
			}
			if (pRecord != null) {
				pRecord.setRecord(buffer[ISO7816.OFFSET_P2], cache, (short) 0,
						(short) cachelength);
			}
		} else {
			ISOException.throwIt(SW_DATA_STORE_FAILED);
		}
		// Return successfully, passing no data:
		apdu.setOutgoingAndSend((short) 0, (short) 0);
	}

	/**
	 * Intorce o inregistrare avind ca parametru de intrare bufferul 
	 *  APDU
	 * 
	 * @param apdu
	 *            The APDU to be processed.
	 */
	private void getRecord(APDU apdu) {
		byte[] buffer = apdu.getBuffer();

		// Check class byte. Only 0x80 is allowed here.
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x80)
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		if (buffer[ISO7816.OFFSET_INS] != GET_DATA_INS) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		// Retrieve the information to send outgoing
		Doctor dRecord = null;
		Pacient pRecord = null;
		Record record = null;

		switch (buffer[ISO7816.OFFSET_P1]) {
		case D_RECORD:
			dRecord = doctorRecord;
			break;
		case P_RECORD:
			pRecord = pacientRecord;
			break;
		default: // Index is out of bounds
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		if (dRecord != null) {
			record = dRecord.getRecord(buffer[ISO7816.OFFSET_P2]);
		}

		if (pRecord != null) {
			record = pRecord.getRecord(buffer[ISO7816.OFFSET_P2]);
		}

		// Le currently
		short le = apdu.setOutgoing();
		apdu.setOutgoingLength(record.length);

		// Send the response.
		apdu.sendBytesLong(record.data, (short) 0, record.length);
	}

	/**
	 * Return the select response when the applet is selected.
	 * 
	 * @param apdu
	 *            The select APDU that was sent to the card.
	 */
	private void selectApplet(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short lc = (short) ((short) 0x00FF & buffer[4]);
		short offset = ISO7816.OFFSET_CDATA;

		// Check class byte. Only 0x00 is allowed here.
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x00)
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);

		// Check P1. Only SELECT by name is supported.
		if (buffer[ISO7816.OFFSET_P1] != (byte) 0x04)
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);

		// Check P2. Only 0x00 (return FCI template) is allowed here.
		if (buffer[ISO7816.OFFSET_P2] != (byte) 0x00)
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);

		// Check AID length.
		if ((lc < ISO7816.OFFSET_CDATA) || (lc > (short) 0x10))
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

		// Calculate length to make SELECT processing general
		short proLen = (short) 0x00; // 13 bytes proprietary data
		short start = (offset -= 4); // to use aid in APDU buffer

		// -> command ok, now prepare response
		buffer[offset++] = (byte) 0x6F;
		buffer[offset++] = (byte) ((byte) 0xFF & (4 + proLen + lc));
		buffer[offset++] = (byte) 0x84;
		buffer[offset++] = (byte) ((byte) 0xFF & (lc));
		offset += lc;
		buffer[offset++] = (byte) 0xA5;
		buffer[offset++] = (byte) ((byte) 0xFF & (proLen));

		// Send the response.
		apdu.setOutgoingAndSend(start,
				(short) (start + 1 + (short) ((byte) 0xFF & buffer[2])));
	}

	/**
	 * Sterge cimpul dat in APDU de P2 din inregistrarea doctor sau pacient
	 * 
	 * @param apdu
	 *            input
	 * @author Gaby Popovici
	 */
	public void delete(APDU apdu) {
		byte[] buffer = apdu.getBuffer();

		// Check class byte. Only 0x80 is allowed here.
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x80)
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		if (buffer[ISO7816.OFFSET_INS] != DELETE_INS) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		// Retrieve the information to send outgoing
		Doctor dRecord = null;
		Pacient pRecord = null;
		Record record = null;
		switch (buffer[ISO7816.OFFSET_P1]) {
		case D_RECORD:
			dRecord = doctorRecord;
			break;
		case P_RECORD:
			pRecord = pacientRecord;
			break;
		default: // Index is out of bounds
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		if (dRecord != null) {
			record = dRecord.getRecord(buffer[ISO7816.OFFSET_P2]);
		}
		if (pRecord != null) {
			record = pRecord.getRecord(buffer[ISO7816.OFFSET_P2]);
		}
		// resetez cimpul la valoarea initiala
		record.length = (short) 1;
		record.data = new byte[record.length];
		record.data[0] = (byte) '?';
		// apdu.setOutgoingAndSend((short)0,(short)0);
	}

	/**
	 * Update in cimpul APDU(P2) din inregistrarea APDU(P1)
	 * 
	 * @author Gaby Popovici
	 * 
	 */
	public void update(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short lc = (short) ((short) 0x00FF & buffer[4]);

		// Check class byte. Only 0x80 is allowed here.
		if (buffer[ISO7816.OFFSET_CLA] != (byte) 0x80) {
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		}
		if (buffer[ISO7816.OFFSET_INS] != UPDATE_INS) {
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		// Check if the CHV has already been verified.
		/*
		 * if (pin_.isValidated() != true)
		 * ISOException.throwIt(ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED);
		 */

		// Determine which information to update
		Doctor dRecord = null;
		Pacient pRecord = null;
		Record record = null;
		switch (buffer[ISO7816.OFFSET_P1]) {
		case D_RECORD:
			dRecord = doctorRecord;
			break;
		case P_RECORD:
			pRecord = pacientRecord;
			break;
		default: // Index is out of bounds
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		if (dRecord != null) {
			record = dRecord.getRecord(buffer[ISO7816.OFFSET_P2]);
		}
		if (pRecord != null) {
			record = pRecord.getRecord(buffer[ISO7816.OFFSET_P2]);
		}
		short oldLength = (short) record.length;
		record.length = (short) (oldLength + lc);
		record.data[oldLength + 1] = (byte) ' ';// introduce un spatiu
		Util.arrayCopy(buffer, (short) ISO7816.OFFSET_CDATA, record.data,
				(short) (oldLength + 1), (short) lc);

	}
}

/*
 * STORE_PHOTO /send
 * 800A0000FFFFD8FFE000104A46494600010101006000600000FFDB004300080606070605080707070909080A0C140D0C0B0B0C1912130F141D1A1F1E1D1A1C1C20242E2720222C231C1C2837292C30313434341F27393D38323C2E333432FFDB0043010909090C0B0C180D0D1832211C213232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232FFC000110800C300AC03012200021101031101FFC4001F0000010501010101010100000000000000000102030405060708090A0BFFC400B5100002010303020403050504040000017D01020300041105122131410613516107227114328191A108
 * 9000
 * 
 * STORE_PHOTO /send
 * 800A0000FF2342B1C11552D1F02433627282090A161718191A25262728292A3435363738393A434445464748494A535455565758595A636465666768696A737475767778797A838485868788898A92939495969798999AA2A3A4A5A6A7A8A9AAB2B3B4B5B6B7B8B9BAC2C3C4C5C6C7C8C9CAD2D3D4D5D6D7D8D9DAE1E2E3E4E5E6E7E8E9EAF1F2F3F4F5F6F7F8F9FAFFC4001F0100030101010101010101010000000000000102030405060708090A0BFFC400B51100020102040403040705040400010277000102031104052131061241510761711322328108144291A1B1C109233352F0156272D10A162434E125F11718191A262728292A35363738393A4344454647
 * 9000
 * 
 * STORE_PHOTO /send
 * 800A0000FF48494A535455565758595A636465666768696A737475767778797A82838485868788898A92939495969798999AA2A3A4A5A6A7A8A9AAB2B3B4B5B6B7B8B9BAC2C3C4C5C6C7C8C9CAD2D3D4D5D6D7D8D9DAE2E3E4E5E6E7E8E9EAF2F3F4F5F6F7F8F9FAFFDA000C03010002110311003F00F7FA28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A
 * 9000
 * 
 * STORE_PHOTO /send
 * 800A0000FF28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28AF2FD03C55AEB683A6EA02EEFB50FB6DA5AADC4BA9580823B6BA9E58238CC5B638BCD8FF007B2B100B6446837A6ECB007A8515E7FE27D4F5BF0DC7169B06AB7D7F35EF952457262B61731E2EEDA1745F91626DCB71F2EE51B48392411B2BCDA9F881BC1BE26BF8F55D56CDF4A8A592237B1599BB13C51BB3248B1A347E491E532F01CE49CEC642403D228AE1DF53D5B4DF1DE9BA1B6AB3DDDABFD9FCC79E2883C9BE2D41CE4A2281CC10F403EE7BB669DFF8B757D3BC67A8219A39349B079E4B985A3195B748B4F667561CE53ED1
 * 9000
 * 
 * STORE_PHOTO /send
 * 800A0000FF3498F98B00540C952A01E8945797DBEB7E25D535DBFB686E35C961B6F376AE94960BB717D7910DE6E00CFC90C606DFEE9279393D66B6750BAF15695A5DA6AF77A7412D95DDC4AD6B1C2CCED1BDBAA83E6C6E00C4AFD00ED401D2515E669AFF0088B58D72E2DE06D6425A5B84917465B10A655BABA81E46FB56480DE4290AA4EDE4127A9F4CA0028A28A0028A28A0028A28A0028A28A0028A28A0028A28A002A9C1A558DB68D168E96D19D3E3B716AB6F27CEA620BB761DD9DC36F1CE73DEAE579BE97AA5EFFC251178965B1BE4D235497C84B9792330982510A5A3001CC872E9908D1AEC37921246D62E01D841E17D26081E2F2679B7CB0CCD25CDDCB3C85A
 * 9000
 * 
 * STORE_PHOTO /send
 * 800A0000FF2712463CC762DB55C6E0B9DB92DC7CC7362E744D3AEE2D4A29ADF31EA717937881D944ABB4A12403C3153B4B0C310AA33855C799E81E1D874DF875A0EA4D69A32CF74FA2949ACF4D104FB5AEADD984B26F63212769270BC8CE39E2E68B7DE1AD0353128934ABC6B78A596E351B3568F518512366737D10CBBF0A77BB1199590794AD82003BCBFF000F699A95C497373049F6891234F3E29E48A4511990AEC746050FEF6404A9048720E41C5161E1ED334DB88EE6DA093ED11A489E7CB3C92C8C243196DEEEC4B9FDD460162480800C018AF33D426D5B4DF0F789FF00B674DBEB2FEDAD2AF7FE3E268A4417412E2608A6391CB7EE4ECDEC13E5B58C775558F5
 * 9000
 * 
 * STORE_PHOTO /send
 * 800A0000FFFD0E1D1A0D4AD2FD7C3F609BEC193ECBA60B5B3B985AF600EF711990F9A6265C11901526E5BF7B8500F408FC07E1E8083676D776202140B61A85C5B2EDF31E4C6D8DD46034B2103B6EC0C0C0AD0D57C3FA7EB3716F7177F6B59EDD1D2292D6F66B660AE54B0262752412887073D0563F862E745D1FC2B7F7D6F7FE1F974FB679279E6D0AD0430205405B7223C9970A3279C91B463D72FC2936ADA6F884FF006CE9B7D65FDB5BFF00E3E268A441741E6982298E472DFB93B37B04F96D631DD55403A4B9F08E8F73711CEB1DDDABC76F1DAA8B0BE9ED1444858A26D89D46177B638E335B94514005145140051451400514514005145140051451400514514005
 * 9000
 * 
 * STORE_PHOTO /send
 * 800A0000FF61E9BE2FD0F55B76B882EE48A016E6E849776F2DB2BC200264532AA86400A92CB90372E71919DCAF3FD2BC0B7B1F843474BBBD9E6D5EC6D2D0430DDB4623B731490CCF0868907CACD022EF6F3080A08CF21803ACB3F1069F7D6F75343F6B06D537CB04B65347385C120889903B03B580214EE2A40C9045678F1D684C1F0DA96F4B85B5311D26E849E6B46650813CBDC4EC52C703818271919AF712EB13DE9F10C5E1EBB59EC6CA6B6B6D366B88165B9795E2624B2BB2222F94BC9624E5BE51B577E1CDA5EA53E8BA1DAC9E1DD719AC35037578D1DDDBDB4D72F24371E64A863B9F9332CA18AEF5C07C0C804000EE34ED6ACB5495E2B669D664892678A7B692
 * 9000
 * 
 * STORE_PHOTO /send
 * 800A0000FF075476755256450464C4FDBB67A104E8572FE1BD36E2DB5ABCBC6B1BEB4B5974FB68634BFBA17130749AE99833F99213C48847CC78603B103A8A0028A28A0028A28A0028A28A0028A28A0028A28A0028A28A0028A28A0028A28A002B97F0D78A6F359FECDFB7E99059FF006A69E750B4F22ECCFF00BB1E5EE126634DADFBE8F006E07E6E46067A8AE4EC7C05A6E9BE0E5D02CE3B4B7335BC16DA85CC368B1B5EC6981207C1CE5D7CC5DD9257CC241C8A00934AF1BD9EA7E1E9B5736B3C6A92C31242086795A7489E051C801984F103921558B0DC546F35F5BF17EA9E1BD2DAEB55D0A0127EF4A0B6BFF3237096D3CF8DC63560DFB8DA414C00EA41620A8B0D
 * 9000
 * 
 * STORE_PHOTO /send
 * 800A0000FFE07B21752490DE5F2473448B2F9D7525CC8658A5596DE457999F6F96C242170558BFCC081835F5BF086A9E24D2DAD755D760327EF421B6B0F2E340F6D3C19DA64662DFBFDC497C108A00524B100A63E24430431DC5C8D1AEADFED02299F47D585E1B78C4334AD238F2D3002C24E06491BB009015B4355F15EA568F7C2C349B4B94B3D4E0D39CCF7CD092D32DBEC60044FC6EB8C1F409919CE05C4D1B58BABED3AE756D52C67FB05DFDA635B4B078776619A22A4B4CFF00F3D41C8FEE91CE7229D9781E1D3F4BB9D3ADAFA4FB3C9A9DA5EC21E304C31DBFD9F6C39C8DC36DB850C79C119DC412C01625F144D6BA1F892FAE2C2313E848C64863B82CB2B2DAC7
 * 9000
 * 
 * STORE_PHOTO /send
 * 800A0000FF39018A020664DB9DBDB38E702493C4FE5F84357D7FEC79FECEFB77EE3CDFF59F669244FBD8E377979E8719EF8AAFAA785AF2F9B58B7B7D4E0834DD6B3F6F8A4B4324DF344B0B7952091427C88B8DC8F86C9E41DA234F0A6A4D61A968F71AB5A3E8BA83DE99228EC592E156E1E47204A652B953275F2F903A77A00B9AFF0089FF00B0F54D3ACBEC7E7FDB31F3F9BB7666E6DA0E9839FF008F9DDFF00C77C8E82B93BAF0A6A5AB5DDB5DEAFAB5A493DABC460FB258B42A145C413B860D2B9624DBA28208DB96243718EB2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800
 * 9000
 * 
 * STORE_PHOTO
 * 800A0000B6A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2800A28A2803FFD9
 * 9000
 */
