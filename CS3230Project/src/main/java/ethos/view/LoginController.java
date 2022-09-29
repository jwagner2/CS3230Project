package main.java.ethos.view;

import java.sql.SQLException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    void handleLogin(ActionEvent event) {
        if (this.unameTextField.getText().isEmpty() &&  this.pwdTextField.getText().isEmpty()) {
        	this.invalidCredentialsLabel.setVisible(true);
        } else {
        	 if (this.manager.validateLogin(this.unameTextField.getText(), this.pwdTextField.getText())) {
        		 this.invalidCredentialsLabel.setVisible(false);
        	 } else {
        		 this.invalidCredentialsLabel.setVisible(true);
        	 }
        }
    }
 
        /**if user == null {
            this.invalidCredentialsLable.setVisible(true);
        } else {
            change window size to fit main screen
            show main screen window
        }
        */
   // }
}