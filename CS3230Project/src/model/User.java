package model;

public class User {
	
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private boolean isAdmin;
	
	public User(String fname, String lname, String userName, String password, boolean isAdmin) {
		
		this.firstName = fname;
		this.lastName = lname;
		this.userName = userName;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAdmin() {
		return this.isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
