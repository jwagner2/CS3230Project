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

    private String submitLabOrder = "insert into lab_order (test_id, visit_id, order_datetime) values (?,?,?)";

    public List<LabTest> getCurrentLabs() throws SQLException {
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

    public void orderLabs(List<LabTest> tests, int visitId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(this.submitLabOrder)) {
                for (LabTest current : tests) {
                    stmt.setInt(1, current.getTestId());
                    stmt.setInt(2, visitId);
                    java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
                    stmt.setTimestamp(3, date);
                    stmt.executeUpdate();
                }
        }

    }
}
