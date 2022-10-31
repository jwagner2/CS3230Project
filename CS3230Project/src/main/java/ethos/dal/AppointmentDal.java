package main.java.ethos.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import main.java.ethos.model.Appointment;

public class AppointmentDal {
    
    private String doctorApptsQuery = "select p.fname, p.lname, a.doctor_id, a.patient_id, a.appt_datetime,"
    + " a.appt_reason from appointment a join doctor d on a.doctor_id = d.doctor_id join person p on p.pid = d.pid where d.doctor_id = ?";

    public List<Appointment> getAppointmentsByDoctorID(int doctorID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(doctorApptsQuery)) {

                stmt.setInt(1, doctorID);
                ResultSet rs = stmt.executeQuery();
                List<Appointment> appts = new ArrayList<Appointment>();
                while (rs.next()) {
                    
                    String firstName = rs.getString("fname");
                    String lastName = rs.getString("lname");
                    int doctorId = rs.getInt("doctor_id");
                    int patientId = rs.getInt("patient_id");
                    LocalDateTime apptDatetime = rs.getTimestamp("appt_datetime").toLocalDateTime();
                    String apptReason = rs.getString("appt_reason");
                    Appointment appt = new Appointment(firstName, lastName, doctorId, apptDatetime, apptReason);
                    appts.add(appt);
                }
                return appts;
        }
        
    }
}
