package main.java.ethos.controller;

import java.sql.SQLException;

import main.java.ethos.dal.LoginDal;
import main.java.ethos.model.User;
import main.java.ethos.model.UserRole;

public class LoginController {
    
    private User loggedInUser;

    /**
     * Validate login.
     *
     * @param username the username
     * @param password the password
     * @return true, if successful
     */
    public boolean validateLogin(String username, String password, UserRole role) {
        LoginDal valid8r = new LoginDal();
        try {
            if (role == UserRole.ADMIN) {
                this.loggedInUser = valid8r.loginAdmin(username, password);
            } else if (role == UserRole.NURSE) {
                this.loggedInUser = valid8r.loginNurse(username, password);
            }
            
            if (this.loggedInUser != null) {
                System.out.println("Login success");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }
    
    /**
     * Gets the logged in name.
     *
     * @return the logged in name
     */
    public String getLoggedInName() {
        String name = this.loggedInUser.getFirstName();
        name += " " + this.loggedInUser.getLastName();
        return name;
    }
    
    /**
     * Gets the logged in user name.
     *
     * @return the logged in user name
     */
    public String getLoggedInUserName() {
        return this.loggedInUser.getUserName();
    }
    
    
}
