package main.java.ethos.dal;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.ethos.model.Appointment;

public class AppointmentDal {

    private String patientApptsQuery = "select p.fname, p.lname, a.doctor_id, a.patient_id, a.appt_datetime,"
            + " a.appt_reason from appointment a join doctor d on a.doctor_id = d.doctor_id join person p on p.pid = d.pid where a.patient_id = ?;";

    private String doctorApptTimesForDateQuery = "select a.appt_datetime from appointment a where a.doctor_id = ? and DATE(a.appt_datetime) = ?;";

    private String doctorNameAndIdsQuery = "select CONCAT(p.fname, ' ', p.lname) as name, d.doctor_id from person p join doctor d on p.pid = d.pid;";

    private String createApptStatement = "insert into appointment (doctor_id, appt_datetime, patient_id, appt_reason)"
            + "values (?, ?, ?, ?);";

    // Assumes system users can only update time for appts
    private String updateApptInfoStatement = "update appointment set appt_datetime = ?, doctor_id = ? where patient_id = ? and appt_datetime = ?";

    public void createAppointment(Appointment appointment) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(this.createApptStatement)) {
            this.setStatement(appointment, stmt);
            int rs = stmt.executeUpdate();
            System.out.println("appointment -- rows affected = " + rs);
        }
    }

    public void editApptTime(Appointment appointment, LocalDateTime originalDateTime) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(this.updateApptInfoStatement)) {

            stmt.setTimestamp(1, this.getTimestampFromDatetime(appointment.getApptDateTime()));
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setInt(3, appointment.getPatientId());
            stmt.setTimestamp(4, this.getTimestampFromDatetime(originalDateTime));
            int rs = stmt.executeUpdate();
            System.out.println("appointment -- rows affected = " + rs);
        }
    }

    public List<Appointment> getAppointmentsForPatient(int patientID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(patientApptsQuery)) {

            stmt.setInt(1, patientID);
            ResultSet rs = stmt.executeQuery();
            List<Appointment> appts = new ArrayList<Appointment>();
            while (rs.next()) {

                String firstName = rs.getString("fname");
                String lastName = rs.getString("lname");
                int doctorId = rs.getInt("doctor_id");
                LocalDateTime apptDatetime = rs.getTimestamp("appt_datetime").toLocalDateTime();
                String apptReason = rs.getString("appt_reason");
                Appointment appt = new Appointment(firstName, lastName, doctorId, patientID, apptDatetime, apptReason);
                appts.add(appt);
            }
            return appts;
        }
    }

    public List<LocalTime> getDoctorAvailability(int doctorId, Date dateTime) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(doctorApptTimesForDateQuery)) {

            stmt.setInt(1, doctorId);
            stmt.setDate(2, dateTime);
            ResultSet rs = stmt.executeQuery();
            List<LocalTime> apptTimes = new ArrayList<LocalTime>();
            while (rs.next()) {
                LocalDateTime apptDatetime = rs.getTimestamp("appt_datetime").toLocalDateTime();
                apptTimes.add(apptDatetime.toLocalTime());
            }
            return apptTimes;
        }

    }

    public Map<String, Integer> getDoctorNameAndId() throws SQLException {
        HashMap<String, Integer> doctors = null;
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(doctorNameAndIdsQuery)) {

            doctors = new HashMap<String, Integer>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("doc: " + rs.getString("name"));
                String doctorName = rs.getString("name");
                int doctorId = rs.getInt("doctor_id");
                doctors.put(doctorName, doctorId);
            }
        }
        return doctors;
    }

    private void setStatement(Appointment appointment, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, appointment.getDoctorId());
        stmt.setTimestamp(2, this.getTimestampFromDatetime(appointment.getApptDateTime()));
        stmt.setInt(3, appointment.getPatientId());
        stmt.setString(4, appointment.getAppointmentReason());
    }

    private Timestamp getTimestampFromDatetime(LocalDateTime dateTime) {

        return new java.sql.Timestamp(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime());
    }
}