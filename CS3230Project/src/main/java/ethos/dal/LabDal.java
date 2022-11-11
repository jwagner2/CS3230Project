package main.java.ethos.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.ethos.model.LabTest;

public class LabDal {
    
    private String getLabsQuery = "select * from lab_test_type";

    public List<LabTest >getCurrentLabs() throws SQLException {
        List<LabTest> currentLabs = new ArrayList<LabTest>();
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(this.getLabsQuery)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int labId = rs.getInt("test_id");
                String testName = rs.getString("name");
                String description = rs.getString("description");
                LabTest newLab = new LabTest(labId, testName, description); 
                currentLabs.add(newLab);
            }
            
            
        }
        return currentLabs;
    }
}
