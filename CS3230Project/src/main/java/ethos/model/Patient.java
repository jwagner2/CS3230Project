package main.java.ethos.model;

import java.sql.Date;

/**
 * The Class Patient.
 */
public class Patient {

	private String firstName;
	private String lastName;
	private String ssn;
	private Date birthDate;
	private boolean isActive;
	private String contactNumber;
	private String addressOne;
	private String addressTwo;
	private String addressZip;
	private String addressState;

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
	 */
	public Patient(String fname, String lname, String ssn, Date birthDate, boolean isActive, String contactNumber,
			String addressOne, String addressTwo, String addressZip, String addressState) {
		this.firstName = fname;
		this.lastName = lname;
		this.ssn = ssn;
		this.birthDate = birthDate;
		this.isActive = isActive;
		this.contactNumber = contactNumber;
		this.setAddress(addressOne, addressTwo, addressZip, addressState);
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
		return isActive;
	}

	/**
	 * Gets the address one.
	 *
	 * @return the address one
	 */
	public String getAddressOne() {
		return addressOne;
	}

	/**
	 * Gets the address two.
	 *
	 * @return the address two
	 */
	public String getAddressTwo() {
		return addressTwo;
	}

	/**
	 * Gets the address zip.
	 *
	 * @return the address zip
	 */
	public String getAddressZip() {
		return addressZip;
	}

	/**
	 * Gets the address state.
	 *
	 * @return the address state
	 */
	public String getAddressState() {
		return addressState;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
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
		return ssn;
	}

	/**
	 * Gets the birth date.
	 *
	 * @return the birth date
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Gets the contact number.
	 *
	 * @return the contact number
	 */
	public String getContactNumber() {
		return contactNumber;
	}

}
