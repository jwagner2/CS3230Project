package main.java.ethos.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.ethos.model.Patient;

// TODO: Auto-generated Javadoc
/**
 * The Class PatientSearchDal.
 */
public class PatientSearchDal {
    

    /** The name query. */
    private boolean nameQuery = false;
    
    /** The date query. */
    private boolean dateQuery = false;
    
    /** The combo query. */
    private boolean comboQuery = false;

    /**
     * Patient search.
     *
     * @param firstName the first name
     * @param lastName the last name
     * @param dob the dob
     * @return the list
     * @throws SQLException the SQL exception
     */
    public List<Patient> patientSearch(String firstName, String lastName, Date dob) throws SQLException {

        List<Patient> patientList = new ArrayList<Patient>();
        String queryToUse = determineQuery(firstName, lastName, dob);

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(queryToUse)) {

            this.setStatement(firstName, lastName, dob, stmt);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String fName = rs.getString("fname");
                String lName = rs.getString("lname");
                String ssn = rs.getString("ssn");
                if (rs.wasNull()) {
                    ssn = "";
                }
                Date dateOfBirth = rs.getDate("dob");
                boolean isActive = rs.getBoolean("isActive");
                String contactNumber = rs.getString("phone");
                String addressOne = rs.getString("addr1_street");
                String addressTwo = rs.getString("addr2_street");
                if (rs.wasNull()) {
                    addressTwo = "";
                }
                String addressZip = rs.getString("addr_zip");
                String addressState = rs.getString("addr_state");
                int patientId = rs.getInt("patient_id");
                int personId = rs.getInt("pid");
                char gender = rs.getString("gender").charAt(0);
                Patient patient = new Patient(fName, lName, ssn, dateOfBirth, isActive, contactNumber, addressOne,
                        addressTwo, addressZip, addressState, gender);
                patient.setPatientId(patientId);
                patient.setPersonId(personId);
                patientList.add(patient);

            }

        }
        this.resetBools();
        return patientList;
    }

    /**
     * Sets the statement.
     *
     * @param firstName the first name
     * @param lastName the last name
     * @param dob the dob
     * @param stmt the stmt
     * @throws SQLException the SQL exception
     */
    private void setStatement(String firstName, String lastName, Date dob, PreparedStatement stmt) throws SQLException {
        if (this.nameQuery) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
        } else if (this.dateQuery) {
            stmt.setDate(1, dob);
        } else if (this.comboQuery) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setDate(3, dob);
        }
    }

    /**
     * Determine query.
     *
     * @param firstName the first name
     * @param lastName the last name
     * @param dob the dob
     * @return the string
     */
    private String determineQuery(String firstName, String lastName, Date dob) {
        String queryToUse = "select * from person ps join patient pt on ps.pid = pt.pid where ";
        if (!firstName.isBlank() && !lastName.isBlank() && dob != null) {
            queryToUse += "fname = ? and lname = ? and dob = ?";
            this.comboQuery = true;
        } else if (dob != null) {
            queryToUse += "dob = ?";
            this.dateQuery = true;
        } else {
            queryToUse += "fname = ? and lname = ?";
            this.nameQuery = true;
        }
        return queryToUse;
    }

    /**
     * Reset bools.
     */
    private void resetBools() {
        this.nameQuery = false;
        this.dateQuery = false;
        this.comboQuery = false;
    }

}
