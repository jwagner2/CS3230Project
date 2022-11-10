package main.java.ethos.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import main.java.ethos.model.Patient;

/**
 * The Class PatientRegEditDal.
 */
public class PatientRegEditDal {
 

    /**
     * Register edit patient.
     *
     * @param patient the patient
     * @throws SQLException the SQL exception
     */
    public void editPatient(Patient patient) throws SQLException {
        
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                CallableStatement cstmt = connection.prepareCall("{call uspEditPatient(?,?,?,?,?,?,?,?,?,?,?,?)}");) {
            this.setStatement(patient, true, cstmt);
            int rs = cstmt.executeUpdate();
        }
    }

    public void registerPatient(Patient patient) throws SQLException {

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                CallableStatement cstmt = connection.prepareCall("{call uspRegisterPatient(?,?,?,?,?,?,?,?,?,?)}");) {

            this.setStatement(patient, false, cstmt);
            int rs = cstmt.executeUpdate();
            System.out.println("rows affected = " + rs);
        }
    }

    private void setStatement(Patient patient, boolean edit, CallableStatement stmt) throws SQLException {
        stmt.setString(1, patient.getFirstName());
        stmt.setString(2, patient.getLastName());
        stmt.setString(3, patient.getSsn());
        stmt.setDate(4, patient.getBirthDate());
        stmt.setString(5, patient.getAddressOne());
        stmt.setString(6, patient.getAddressTwo());
        stmt.setString(7, patient.getAddressState());
        stmt.setString(8, patient.getAddressZip());
        stmt.setString(9, patient.getContactNumber());
        stmt.setString(10, Character.toString(patient.getGender()));
        if (edit) {
            stmt.setInt(11, patient.getPersonId());
            stmt.setBoolean(12, patient.isActive());
        }
    }

}
