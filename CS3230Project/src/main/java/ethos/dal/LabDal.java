package main.java.ethos.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.ethos.model.LabTest;

// TODO: Auto-generated Javadoc
/**
 * The Class LabDal.
 */
public class LabDal {

    /** The get labs query. */
    private String getLabsQuery = "select * from lab_test_type";

    /** The submit lab order. */
    private String submitLabOrder = "insert into lab_order (test_id, visit_id, order_datetime) values (?,?,?)";

    /** The get labs for visit. */
    private String getLabsForVisit = "select lo.test_id, lo.results, lo.isAbnormal, lt.name, lt.description from lab_order lo, lab_test_type lt where lt.test_id = lo.test_id and visit_id = ?";

    /** The update lab. */
    private String updateLab = "update lab_order set results = ?, isAbnormal = ? where visit_id = ? and test_id = ?";

    /**
     * Gets the current labs.
     *
     * @return the current labs
     * @throws SQLException the SQL exception
     */
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

    /**
     * Update lab.
     *
     * @param result     the result
     * @param isAbnormal the is abnormal
     * @param visitId    the visit id
     * @param testId     the test id
     * @throws SQLException the SQL exception
     */
    public void updateLab(String result, boolean isAbnormal, int visitId, int testId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(this.updateLab)) {

            stmt.setString(1, result);
            stmt.setBoolean(2, isAbnormal);
            stmt.setInt(3, visitId);
            stmt.setInt(4, testId);
            stmt.executeUpdate();
        }
    }

    /**
     * Order labs.
     *
     * @param tests   the tests
     * @param visitId the visit id
     * @throws SQLException the SQL exception
     */
    public void orderLabs(List<LabTest> tests, int visitId) {
        for (LabTest current : tests) {
            try {
                this.orderLab(current, visitId);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private void orderLab(LabTest current, int visitId) throws SQLException {
        Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
        try (PreparedStatement stmt = connection.prepareStatement(this.submitLabOrder)) {
            connection.setAutoCommit(false);
            stmt.setInt(1, current.getTestId());
            stmt.setInt(2, visitId);
            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            stmt.setTimestamp(3, date);
            stmt.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();

        }

    }

    /**
     * Gets the labs for visit.
     *
     * @param visitId the visit id
     * @return the labs for visit
     * @throws SQLException the SQL exception
     */
    public List<LabTest> getLabsForVisit(int visitId) throws SQLException {
        List<LabTest> currentLabs = new ArrayList<LabTest>();
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(this.getLabsForVisit)) {

            stmt.setInt(1, visitId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int labId = rs.getInt("test_id");
                String testName = rs.getString("name");
                String description = rs.getString("description");
                LabTest newLab = new LabTest(labId, testName, description);
                String results = rs.getString("results");
                if (results != null) {
                    newLab.setResults(results);
                    newLab.setIsAbnormal(rs.getBoolean("isAbnormal"));
                }
                currentLabs.add(newLab);
            }

        }
        return currentLabs;
    }
}
