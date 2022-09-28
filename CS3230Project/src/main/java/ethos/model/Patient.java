package model;

import java.sql.Date;

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

	public void setAddress(String addressOne, String addressTwo, String addressZip, String addressState) {
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
		this.addressZip = addressZip;
		this.addressState = addressState;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setContactNumber(String number) {
		this.contactNumber = number;
	}

	public void setFirstName(String fname) {
		this.firstName = fname;
	}

	public void setLastName(String lname) {
		this.lastName = lname;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public boolean isActive() {
		return isActive;
	}

	public String getAddressOne() {
		return addressOne;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public String getAddressZip() {
		return addressZip;
	}

	public String getAddressState() {
		return addressState;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSsn() {
		return ssn;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getContactNumber() {
		return contactNumber;
	}

}
