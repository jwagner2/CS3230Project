package main.java.ethos.model;

/**
 * The Class User.
 */
public class User {
	
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private UserRole currentRole;
	private int userId;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param fname the fname
	 * @param lname the lname
	 * @param userName the user name
	 * @param password the password
	 * @param role the current user role (admin or nurse)
	 */
	public User(String fname, String lname, String userName, String password, UserRole role, int userId) {
		
		this.firstName = fname;
		this.lastName = lname;
		this.userName = userName;
		this.password = password;
		this.currentRole = role;
		this.userId = userId;
	}

	/**
	 * Gets the user role
	 * @return the role
	 */
	public UserRole getUserRole() {
		return this.currentRole;
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
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}	
}
