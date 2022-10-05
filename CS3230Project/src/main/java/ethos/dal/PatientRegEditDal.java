package main.java.ethos.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.java.ethos.model.Patient;

/**
 * The Class PatientRegEditDal.
 */
public class PatientRegEditDal {

    String editQuery = "update patient set fname = ?, lname = ?, ssn = ?,dob = ?,  isActive = ?, phone = ?, "
            + "address1 = ?, address2 = ?, zip = ?, state = ?, gender = ? where patientId = ?";

    String registerQuery = "insert into patient (fname, lname, ssn, dob, isActive, phone, address1, address2, zip, state, gender)"
            + "values (?, ?, ?, ?, ?, ?,? ,?, ?, ?, ?)";

    /**
     * Register edit patient.
     *
     * @param patient the patient
     * @throws SQLException the SQL exception
     */
    public void registerEditPatient(Patient patient) throws SQLException {
        String queryToUse = "";
        boolean edit = false;
        if (patient.getPatientId() != 0) {
            edit = true;
            queryToUse = editQuery;
        } else {
            queryToUse = registerQuery;
        }
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(queryToUse)) {

            this.setStatement(patient, edit, stmt);

            int rs = stmt.executeUpdate();
            System.out.println("rows affected = " + rs);
        }
    }

    private void setStatement(Patient patient, boolean edit, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, patient.getFirstName());
        stmt.setString(2, patient.getLastName());
        stmt.setString(3, patient.getSsn());
        stmt.setDate(4, patient.getBirthDate());
        stmt.setBoolean(5, patient.isActive());
        stmt.setString(6, patient.getContactNumber());
        stmt.setString(7, patient.getAddressOne());
        stmt.setString(8, patient.getAddressTwo());
        stmt.setString(9, patient.getAddressZip());
        stmt.setString(10, patient.getAddressState());
        stmt.setString(11, Character.toString(patient.getGender()));
        if (edit) {
            stmt.setInt(12, patient.getPatientId());
        }
    }

}
