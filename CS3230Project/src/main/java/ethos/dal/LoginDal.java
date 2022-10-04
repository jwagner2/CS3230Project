package main.java.ethos.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.ethos.model.User;

/**
 * The Class LoginDal.
 */
public class LoginDal {

    public LoginDal() {

    }

    /**
     * Login.
     *
     * @param username   the username
     * @param password   the password
     * @param adminLogin the admin login
     * @param nurseLogin the nurse login
     * @return the user
     * @throws SQLException the SQL exception
     */
    public User login(String username, String password, Boolean adminLogin, Boolean nurseLogin) throws SQLException {

        String queryToUse = "select * from user where username = ? and password = ?";
        User loggedIn = null;
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(queryToUse)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("rs has next");
                String firstName = rs.getString("fname");
                String lastName = rs.getString("lname");
                boolean isAdmin = rs.getBoolean("isAdmin");
                boolean isNurse = rs.getBoolean("isNurse");
                if (adminLogin == isAdmin && nurseLogin == isNurse) {
                    loggedIn = new User(firstName, lastName, username, password, isAdmin, isNurse);
                    return loggedIn;
                }
            }
            System.out.println("rs no has next");
        }
        return loggedIn;
    }

}
