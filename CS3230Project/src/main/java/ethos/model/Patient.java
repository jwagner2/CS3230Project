package main.java.ethos.model;

import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Patient.
 */
public class Patient {

	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The ssn. */
	private String ssn;
	
	/** The birth date. */
	private Date birthDate;
	
	/** The is active. */
	private boolean isActive;
	
	/** The contact number. */
	private String contactNumber;
	
	/** The address one. */
	private String addressOne;
	
	/** The address two. */
	private String addressTwo;
	
	/** The address zip. */
	private String addressZip;
	
	/** The address state. */
	private String addressState;
	
	/** The gender. */
	private char gender;
	
	/** The patient id. */
	private int patientId;

	/** The person id. */
	private int personId;
	
	/**
	 * Instantiates a new patient.
	 *
	 * @param fname the fname
	 * @param lname the lname
	 * @param ssn the ssn
	 * @param birthDate the birth date
	 * @param isActive the is active
	 * @param contactNumber the contact number
	 * @param addressOne the address one
	 * @param addressTwo the address two
	 * @param addressZip the address zip
	 * @param addressState the address state
	 * @param gender the gender
	 */
	public Patient(String fname, String lname, String ssn, Date birthDate, boolean isActive, String contactNumber,
			String addressOne, String addressTwo, String addressZip, String addressState, char gender) {
		this.firstName = fname;
		this.lastName = lname;
		this.ssn = ssn;
		this.birthDate = birthDate;
		this.isActive = isActive;
		this.contactNumber = contactNumber;
		this.setAddress(addressOne, addressTwo, addressZip, addressState);
		this.gender = gender;
		
	}

    /**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public char getGender() {
        return this.gender;
    }

    /**
     * Sets the gender.
     *
     * @param gender the new gender
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
	 * Sets the address.
	 *
	 * @param addressOne the address one
	 * @param addressTwo the address two
	 * @param addressZip the address zip
	 * @param addressState the address state
	 */
	public void setAddress(String addressOne, String addressTwo, String addressZip, String addressState) {
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
		this.addressZip = addressZip;
		this.addressState = addressState;
	}

	/**
	 * Sets the birth date.
	 *
	 * @param birthDate the new birth date
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Sets the checks if is active.
	 *
	 * @param isActive the new checks if is active
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Sets the contact number.
	 *
	 * @param number the new contact number
	 */
	public void setContactNumber(String number) {
		this.contactNumber = number;
	}

	/**
	 * Sets the first name.
	 *
	 * @param fname the new first name
	 */
	public void setFirstName(String fname) {
		this.firstName = fname;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lname the new last name
	 */
	public void setLastName(String lname) {
		this.lastName = lname;
	}

	/**
	 * Sets the ssn.
	 *
	 * @param ssn the new ssn
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return this.isActive;
	}

	/**
	 * Gets the address one.
	 *
	 * @return the address one
	 */
	public String getAddressOne() {
		return this.addressOne;
	}

	/**
	 * Gets the address two.
	 *
	 * @return the address two
	 */
	public String getAddressTwo() {
		return this.addressTwo;
	}

	/**
	 * Gets the address zip.
	 *
	 * @return the address zip
	 */
	public String getAddressZip() {
		return this.addressZip;
	}

	/**
	 * Gets the address state.
	 *
	 * @return the address state
	 */
	public String getAddressState() {
		return this.addressState;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the ssn.
	 *
	 * @return the ssn
	 */
	public String getSsn() {
		return this.ssn;
	}

	/**
	 * Gets the birth date.
	 *
	 * @return the birth date
	 */
	public Date getBirthDate() {
		return this.birthDate;
	}

	/**
	 * Gets the contact number.
	 *
	 * @return the contact number
	 */
	public String getContactNumber() {
		return this.contactNumber;
	}
	
	/**
	 * gets the patient id.
	 *
	 * @return the patient id
	 */
	public int getPatientId() {
	    return this.patientId;
	}
	
	/**
	 * Sets the patient id.
	 *
	 * @param patientId the new patient id
	 */
	public void setPatientId(int patientId) {
	    this.patientId = patientId;
	}

	/**
	 * gets the person id.
	 *
	 * @return the patient id
	 */
	public int getPersonId() {
	    return this.personId;
	}
	
	/**
	 * Sets the person id.
	 *
	 * @param personId the new person id
	 */
	public void setPersonId(int personId) {
	    this.personId = personId;
	}

}
