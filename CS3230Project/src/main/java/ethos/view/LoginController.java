package main.java.ethos.view;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.java.ethos.Main;
import main.java.ethos.controller.ControllerManager;
import main.java.ethos.dal.LoginDal;
import main.java.ethos.model.User;

/**
 * The Class LoginController.
 */
public class LoginController {

	private ControllerManager manager = new ControllerManager();

	@FXML
	private TextField unameTextField;

	@FXML
	private TextField pwdTextField;

	@FXML
	private Button loginButton;

	@FXML
	private Label invalidCredentialsLabel;

	/**
	 * Handle login.
	 *
	 * @param event the event
	 */
	@FXML
	void handleLogin(ActionEvent event) throws IOException {
		if (this.unameTextField.getText().isEmpty() && this.pwdTextField.getText().isEmpty()) {
			System.out.print("here");
			this.invalidCredentialsLabel.setVisible(true);
		} else {
			System.out.print("here");
			if (this.manager.validateLogin(this.unameTextField.getText(), this.pwdTextField.getText())) {
				this.invalidCredentialsLabel.setVisible(false);
				
					Stage stage = (Stage) this.loginButton.getScene().getWindow();;
					Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(Main.MAIN_PAGE));
					Scene scene = new Scene(parent);
					stage.setTitle("ethos");
					stage.setScene(scene);
					stage.show();

				}

			}
	}
}