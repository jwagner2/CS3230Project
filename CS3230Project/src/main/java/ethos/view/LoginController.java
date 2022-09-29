package main.java.ethos.view;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.java.ethos.dal.LoginDal;
import main.java.ethos.model.User;

public class LoginController {

    @FXML
    private TextField unameTextField;

    @FXML
    private TextField pwdTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Label invalidCredentialsLabel;

    @FXML
    void handleLogin(ActionEvent event) {
        this.validateLogin(unameTextField.getText(), pwdTextField.getText());
    }

    private void validateLogin(String username, String password) {
        LoginDal valid8r = new LoginDal();
        if (username.isEmpty() && password.isEmpty()) {
        	this.invalidCredentialsLabel.setVisible(true);
        }
        else {
            try {
                User user = valid8r.login(username, password, false, true);
                if (user != null) {
                    this.invalidCredentialsLabel.setVisible(false);
                    System.out.println("Login success");
                } else {
                    this.invalidCredentialsLabel.setVisible(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                
            }
        	
        }
 
        /**if user == null {
            this.invalidCredentialsLable.setVisible(true);
        } else {
            change window size to fit main screen
            show main screen window
        }
        */
    }

}