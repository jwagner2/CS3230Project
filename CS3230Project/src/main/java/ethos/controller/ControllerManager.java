/*
 * 
 */
package main.java.ethos.controller;

import java.sql.SQLException;

import main.java.ethos.dal.LoginDal;
import main.java.ethos.model.Patient;
import main.java.ethos.model.User;

public class ControllerManager {

	private User loggedInUser;
	private Patient displayedPatient;

	/**
	 * Validate login.
	 *
	 * @param username the username
	 * @param password the password
	 * @return true, if successful
	 */
	public boolean validateLogin(String username, String password) {
		LoginDal valid8r = new LoginDal();
		System.out.println("here");
		try {
			this.loggedInUser = valid8r.login(username, password, true, true);
			if (this.loggedInUser != null) {
				System.out.println("Login success");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false;
	}

}
