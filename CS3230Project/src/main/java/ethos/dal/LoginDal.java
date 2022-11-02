package main.java.ethos.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.ethos.model.User;
import main.java.ethos.model.UserRole;

/**
 * The Class LoginDal.
 */
public class LoginDal {

    public LoginDal() {

    }

    /**
     * Connects to the database and authenticates the user
     *
     * @param username   the username
     * @param password   the password
     * @param adminLogin the admin login
     * @param nurseLogin the nurse login
     * @return the user
     * @throws SQLException the SQL exception
     */
    public User loginAdmin(String username, String password) throws SQLException {

        String queryToUse = "select * from login l join person p on l.pid = p.pid left outer join admin a on p.pid = a.pid left outer join nurse n on p.pid = n.pid where username = binary ? and password = binary ?";
        User loggedIn = null;
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(queryToUse)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                
                String firstName = rs.getString("fname");
                String lastName = rs.getString("lname");
                boolean isAdmin = rs.getInt("admin_id") != 0;
                int userId = rs.getInt("admin_id");
                if (isAdmin) {
                    loggedIn = new User(firstName, lastName, username, password, UserRole.ADMIN, userId);
                    return loggedIn;
                }
            }
        }
        return loggedIn;
    }

    /**
     * Connects to the database and authenticates the user
     *
     * @param username   the username
     * @param password   the password
     * @param adminLogin the admin login
     * @param nurseLogin the nurse login
     * @return the user
     * @throws SQLException the SQL exception
     */
    public User loginNurse(String username, String password) throws SQLException {

        String queryToUse = "select * from login l join person p on l.pid = p.pid left outer join nurse n on p.pid = n.pid where username = binary ? and password = binary ?";
        User loggedIn = null;
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(queryToUse)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                
                String firstName = rs.getString("fname");
                String lastName = rs.getString("lname");
                boolean isNurse = rs.getInt("nurse_id") != 0;
                int userId = rs.getInt("nurse_id");
                if (isNurse) {
                    loggedIn = new User(firstName, lastName, username, password, UserRole.NURSE, userId);
                    return loggedIn;
                }
            }
        }
        return loggedIn;
    }

}
