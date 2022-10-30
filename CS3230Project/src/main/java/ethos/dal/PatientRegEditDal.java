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

    String editPatientInfoQuery = "update person set fname = ?, lname = ?, ssn = ?, dob = ?, "
            + "addr1_street = ?, addr2_street = ?, addr_state = ?, addr_zip = ?, phone = ?, gender = ? where pid = ?;"
            + "update patient set isActive = ? where pid = ?";

    String registerQuery = "insert into person (fname, lname, ssn, dob, addr1_street, addr2_street, addr_state, addr_zip, phone, gender)"
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); SET @new_patient_pid = (select last_insert_id()); insert into patient (pid, isActive)"
            + "values (@new_patient_pid, 1);";

    /**
     * Register edit patient.
     *
     * @param patient the patient
     * @throws SQLException the SQL exception
     */
    public void editPatient(Patient patient) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(editPatientInfoQuery)) {

            this.setStatement(patient, true, stmt);
            int rs = stmt.executeUpdate();
            System.out.println("rows affected = " + rs);
        }
    }

    public void registerPatient(Patient patient) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(registerQuery)) {

            this.setStatement(patient, false, stmt);
            int rs = stmt.executeUpdate();
            System.out.println("rows affected = " + rs);
        }
    }

    private void setStatement(Patient patient, boolean edit, PreparedStatement stmt) throws SQLException {
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
            stmt.setInt(13, patient.getPersonId());
        }
    }

}
