package main.java.ethos.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.ethos.model.User;

public class LoginDal {
	
	
	public User login(String username, String password, Boolean adminLogin, Boolean nurseLogin) throws SQLException{
			
			
			String queryToUse = "select * from user where username = ? and password = ?";
			User loggedIn = null;
			try ( Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING); 
					PreparedStatement stmt = connection.prepareStatement(queryToUse)){ 
				
				stmt.setString(1, username);
				stmt.setString(2, password);
				ResultSet rs = stmt.executeQuery();
				while (rs.next() ) {
					String firstName = rs.getString("fname");
					String lastName = rs.getString("lname");
					boolean isAdmin = rs.getBoolean("isAdmin");
					boolean isNurse = rs.getBoolean("isNurse");
					if (adminLogin == isAdmin && nurseLogin == isNurse) {
						loggedIn = new User(firstName, lastName, username, password, isAdmin, isNurse);
						return loggedIn;
					}
			
				}

			} 
			return loggedIn;
		}

}
