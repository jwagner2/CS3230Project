package main.java.ethos.view;

import java.io.IOException;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import main.java.ethos.controller.ControllerManager;

/**
 * The Class LoginController.
 */
public class LoginView {

	/** The manager. */
	private ControllerManager manager;

	/** The uname text field. */
	@FXML
	private TextField unameTextField;

	/** The pwd text field. */
	@FXML
	private TextField pwdTextField;

	/** The login button. */
	@FXML
	private Button loginButton;
	
	/** The invalid credentials label. */
	@FXML
	private Label invalidCredentialsLabel;

	/** The nurse radio button. */
	@FXML
    private RadioButton nurseRadioButton;

    /** The user role. */
    @FXML
    private ToggleGroup user_role;

    /** The admin radio button. */
    @FXML
    private RadioButton adminRadioButton;

	/**
	 * Handle login.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	void handleLogin(ActionEvent event) throws IOException {
		if (this.unameTextField.getText().isEmpty() || this.pwdTextField.getText().isEmpty()) {
			this.invalidCredentialsLabel.setVisible(true);
		} else {
			this.invalidCredentialsLabel.setVisible(false);
			if (this.manager.validateLogin(this.unameTextField.getText(), this.pwdTextField.getText(), this.adminRadioButton.isSelected(), this.nurseRadioButton.isSelected())) {
				this.invalidCredentialsLabel.setVisible(false);
				if (this.adminRadioButton.isSelected()) {
					this.manager.changeToAdminView((Stage) this.loginButton.getScene().getWindow());
				} else {
					this.manager.changeToMainView((Stage) this.loginButton.getScene().getWindow());
				}
			} else {
				this.invalidCredentialsLabel.setVisible(true);
				this.pwdTextField.setText("");
			}
		}
	}

	/**
	 * Initialize.
	 *
	 * @param manager the manager
	 */
	public void initialize(ControllerManager manager) {
		this.manager = manager;
	}
}