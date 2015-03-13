/**
 * 
 */
package health;

/**
 * @author Gaby Popovici
 *
 */
public class DoctorInfo {

	/**
	 * DoctorInfo encapsulates a information entry as can be stored by 
	 * the HealthCardApplet.
	 *
	 * @author  Gaby Popovici 
	 * @version 
	 */	
		/** The name. */
		protected String name_ = null;

		/** The type. */
		protected String type_ = null;

		/** The e-mail address. */
		protected String email_ = null;

		/** The phone number. */
		protected String phone_ = null;

		/** The mobile. */
		protected String mobile_ = null;

	/**
	 * Creates a new DoctorInfo instance
	 *
	 * @param name    The name of the doctor.
	 * @param type    The title of the doctor.
	 * @param email   The E-mail address of the doctor.
	 * @param phone   The phone number of the doctor.
	 * @param address The address of the doctor.
	 */
	public DoctorInfo(String name, String type, String email, String phone, String mobile) {
		name_ = name;
		type_ = type;
		email_ = email;
		phone_ = phone;
		mobile_ = mobile;
	}
	/**
	 * Gets the mobile
	 *
	 * @return The mobile.
	 */
	public String getMobile() {
		return mobile_;
	}
	/**
	 * Gets the e-mail address.
	 *
	 * @return The e-mail address.
	 */
	public String getEmail() {
		return email_;
	}
	/**
	 * Gets the name.
	 *
	 * @return The name.
	 */
	public String getName() {
		return name_;
	}
	/**
	 * Gets the phone number.
	 *
	 * @return The phone number.
	 */
	public String getPhone() {
		return phone_;
	}
	/**
	 * Gets the tyep
	 *
	 * @return The type
	 */
	public String getType() {
		return type_;
	}
	/**
	 * Sets the address.
	 *
	 * @param mobile The Mobile.
	 */
	public void setMobile(String mobile) {
		mobile_ = mobile;
	}
	/**
	 * Sets the E-mail address.
	 *
	 * @param email The E-mail address.
	 */
	public void setEmail(String email) {
		email_ = email;
	}
	/**
	 * Sets the name.
	 *
	 * @param name The name.
	 */
	public void setName(String name) {
		name_ = name;
	}
	/**
	 * Sets the phone number.
	 *
	 * @param phone The phone number.
	 */
	public void setPhone(String phone) {
		phone_ = phone;
	}
	/**
	 * Sets the type
	 *
	 * @param tyep The type
	 */
	public void setType(String type) {
		type_ = type;
	}
	/**
	 * Creates a string representing this DoctorInfo object.
	 *
	 * @return The string representing this object.
	 */
	public String toString() {
	  StringBuffer sb = new StringBuffer();

	  sb.append(">");
	  sb.append(getType());
	  sb.append(" ");
	  sb.append(getName());
	  sb.append("<\n Email: >");
	  sb.append(getEmail());
	  sb.append("<\n Phone: >");
	  sb.append(getPhone());
	  sb.append("<\n Mobile: >");
	  sb.append(getMobile());
	  sb.append("<");

	  return sb.toString();
	}
	}

