/**
 * 
 */
package health;

/**
 * @author Gaby Popovici
 *
 */
public class PacientInfo {

	/**
	 * PacientInfo encapsulates  information entry as can be stored by 
	 * the HealthCardApplet.
	 *
	 * @author  Gaby Popovici 
	 * @version 
	 */	
		/** The name. */
		protected String name_ = null;

		/** The type. */
		protected String title_ = null;

		/** The e-mail address. */
		protected String email_ = null;

		/** The phone number. */
		protected String phone_ = null;

		/** The mobile. */
		protected String mobile_ = null;
		
		/** the date issue */
		protected String dateIssued_ = null;
		
		/** the date update */
		protected String dateUpdate_ = null;
		
		/** the addres */
		protected String address_ = null;
		
		/** the gender */
		protected String gender_ = null;
		
		/** the CNP  cod numeric personal */ 
		protected String CNP_ = null;
		
		/** dob - date of birthday */ 
		protected String dob_ = null;
		
		/** bloodType */ 
		protected String bloodType_ = null;
		
		/** hName - Hospital name */ 
		protected String hName_ = null;
		
		/** hAdmissionDate - data intrarii in spital */
		protected String hAdmissionDate_ = null;
		
		/** hOrganDonor  - info pentru spital daca este sau nu donor de organe */ 
		protected String hOrganDonor_ = null;
		
		/** drugAllergies  - alergii la medicamente */ 
		protected String drugAllergies_ = null;
		
		/** drugMedications - medicatia prescrisa de Medic */ 
		protected String drugMedications_ = null;
		
		/** diagnose - diagnosa Medic */ 
		protected String diagnose_ = null;
		
		/** diseaseImm - imunizari contra boli */ 
		protected String diseaseImm_ = null;
		
		/** comments - comentarii */ 
		protected String comments_ = null;
		
		/** operarii efectuate */
		protected String surgicalProc_ = null;
		  
		 

	/**
	 * Creates a new PacientInfo instance
	 *
	 * @param name    The name of the pacient
	 * @param type    The title of the pacient.
	 * @param email   The E-mail address of the pacient.
	 * @param phone   The phone number of the pacient.
	 * @param address The address of the pacient.
	 * @param  .................................
	 * 
	 */
	public PacientInfo(String name, String title, String email, String phone, String mobile,
			String dateIssued, String dateUpdate, String address, String gender, String CNP, String dob,
			String bloodType, String hName, String hAdmissionDate, String hOrganDonor, String drugAllergies, 
			String drugMedications, String diagnose, String diseaseImm, String comments, String surgicalProc) {
		name_ = name;
		title_ = title;
		email_ = email;
		phone_ = phone;
		mobile_ = mobile;
		dateIssued_ = dateIssued;
		dateUpdate_ = dateUpdate; 
		address_ = address; 
		gender_ = gender; 
		CNP_= CNP;
		dob_= dob; 
		bloodType_ = bloodType; 
		hName_ = hName;
		hAdmissionDate_ = hAdmissionDate; 
		hOrganDonor_ = hOrganDonor; 
		drugAllergies_ = drugAllergies; 
		drugMedications_ = drugMedications; 
		diagnose_ = diagnose; 
		diseaseImm_ = diseaseImm; 
		comments_ = comments; 
		surgicalProc_ = surgicalProc; 
			
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
	 * Gets the title
	 *
	 * @return The title
	 */
	public String getTitle() {
		return title_;
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
	 * Sets the title
	 *
	 * @param tyep The title
	 */
	public void setTitle(String title) {
		title_ = title;
	}
	
	public String getDateIssued() {
		return dateIssued_;
	}
	public void setDateIssued(String dateIssued) {
		dateIssued_ = dateIssued;  
	}
	
	public String getDateUpdate() {
		return dateUpdate_;
	}
	public void setDateUpdate(String dateUpdate) {
		dateUpdate_ = dateUpdate;  
	}
	
	public String getAddress() {
		return address_;
	}
	public void setAddress(String address) {
		address_ = address;  
	}
	
	public String getGender() {
		return gender_;
	}
	public void setGender(String gender) {
		gender_ = gender;  
	}
	
	public String getCNP() {
		return CNP_;
	}
	public void setCNP(String CNP) {
		CNP_ = CNP;  
	}
	
	public String getDob() {
		return dob_;
	}
	public void setDob(String dob) {
		dob_ = dob;  
	}
	
	public String getBloodType() {
		return bloodType_;
	}
	public void setBloodType(String bloodType) {
		bloodType_ = bloodType;  
	}
	
	public String getHName() {
		return hName_;
	}
	public void setHName(String hName) {
		hName_ = hName;  
	}
	
	public String getHAdmissionDate() {
		return hAdmissionDate_;
	}
	public void setHAdmissionDate(String hAdmissionDate) {
		hAdmissionDate_ = hAdmissionDate;  
	}
	
	public String getHOrganDonor() {
		return dateIssued_;
	}
	public void setHOrganDonor(String hOrganDonor) {
		hOrganDonor_ = hOrganDonor;  
	}
	
	public String getDrugAllergies() {
		return drugAllergies_;
	}
	public void setDrugAllergies(String drugAllergies) {
		drugAllergies_ = drugAllergies;  
	}
	
	public String getDrugMedications() {
		return drugMedications_;
	}
	public void setDrugMedications(String drugMedications) {
		drugMedications_ = drugMedications;  
	}
	
	public String getDiagnose() {
		return diagnose_;
	}
	public void setDiagnose(String diagnose) {
		diagnose_ = diagnose;  
	}
	
	public String getDiseaseImm() {
		return diseaseImm_;
	}
	public void setDiseaseImm (String diseaseImm) {
		diseaseImm_ = diseaseImm;  
	}
	
	public String getComments() {
		return comments_;
	}
	public void setComments(String comments) {
		comments_ = comments;  
	}
	
	public String getSurgicalProc() {
		return surgicalProc_;
	}
	public void setSurgicalProc(String surgicalProc) {
		surgicalProc_ = surgicalProc;  
	}
	
	/**
	 * Creates a string representing this PacientInfo object.
	 *
	 * @return The string representing this object.
	 */
	public String toString() {
	  StringBuffer sb = new StringBuffer();

	  sb.append(">");
	  sb.append(getTitle());
	  sb.append(" ");
	  sb.append(getName());
	  sb.append("<\n Email: >");
	  sb.append(getEmail());
	  sb.append("<\n Phone: >");
	  sb.append(getPhone());
	  sb.append("<\n Mobile: >");
	  sb.append(getMobile());
	  sb.append("<\n Address: >");
	  sb.append(getAddress());
	  sb.append("<\n Gender: >");
	  sb.append(getGender());
	  sb.append("<\n CNP: >");
	  sb.append(getCNP());
	  sb.append("<\n DOB: >");
	  sb.append(getDob());
	  sb.append("<\n bloodType: >");
	  sb.append(getBloodType());
	  sb.append("<\n Hospital Name: >");
	  sb.append(getHName());
	  sb.append("<\n Admission Date: >");
	  sb.append(getHAdmissionDate());
	  sb.append("<\n Organ Donor: >");
	  sb.append(getHOrganDonor());
	  sb.append("<\n Drug Allergies: >");
	  sb.append(getDrugAllergies());
	  sb.append("<\n Drug Medications: >");
	  sb.append(getDrugMedications());
	  sb.append("<\n Diagnose: >");
	  sb.append(getDiagnose());
	  sb.append("<\n Disease Immnunizations: >");
	  sb.append(getDiseaseImm());
	  sb.append("<\n Comments: >");
	  sb.append(getComments());
	  sb.append("<\n Surgical Operations: >");
	  sb.append(getSurgicalProc());
	  sb.append("<");

	  return sb.toString();
	}
}
