/*
 * 
 */
package main.java.ethos.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.ethos.dal.LoginDal;
import main.java.ethos.model.PageType;
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

	public void changeView(PageType page, Stage currentStage) {
		try {
			Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(page.label));
			Scene scene = new Scene(parent);
			currentStage.setTitle("ethos");
			currentStage.setScene(scene);
			currentStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
