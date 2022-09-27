package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.User;

public class LoginDal {
	
	
public User login(String username, String password, Boolean adminLogin) throws SQLException{
		
		"TODO"
		String query = "ToDo";
		try ( Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING); 
				PreparedStatement stmt = connection.prepareStatement(query)){ 
	
			ResultSet rs = stmt.executeQuery();
			while (rs.next() ) {
		
			  	User loggedIn = new User();
			}

        } 
		return loggedIn;
	}
}
