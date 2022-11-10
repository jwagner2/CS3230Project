package main.java.ethos.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import main.java.ethos.model.Visit;

public class VisitDal {

    private String submitVisitInfoStatement = "insert into visit (doctor_id, appt_datetime, nurse_id, systolic_pressure, diastolic_pressure, body_temp_degreesF, height_inches, weight_pounds, pulse_bpm, symptoms, diagnosis)"
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    //TODO: Implement update diagnosis (not required for Iteration 3)
    // private String updateDiagnosisStatement = "update visit set diagnosis = ? where visit_id = ?";


    public String getDoctorForVisit(int doctorId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(getDoctorById)) {

            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            String doctorName = "";
            while (rs.next()) {
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                doctorName = fname + " " + lname;
            }
            return doctorName;
        }
    }

    public void enterVisitInfo(Visit visit) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(submitVisitInfoStatement)) {

            this.setStatement(visit, stmt);
            int rs = stmt.executeUpdate();
            System.out.println("visit -- rows affected = " + rs);
        }
    }

    private void setStatement(Visit visit, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, visit.getDoctorId());
        stmt.setTimestamp(2, this.getTimestampFromDatetime(visit.getApptDateTime()));
        stmt.setInt(3, visit.getNurseId());
        stmt.setInt(4, visit.getSystolicPressure());
        stmt.setInt(5, visit.getDiastolicPressure());
        stmt.setBigDecimal(6, this.getBigDecimalFromDouble(visit.getBodyTempDegreesF()));
        stmt.setInt(7, visit.getHeightInches());
        stmt.setBigDecimal(8, this.getBigDecimalFromDouble(visit.getBodyWeightLbs()));
        stmt.setInt(9, visit.getPulseBpm());
        stmt.setString(10, visit.getSymptoms());
        stmt.setString(11, visit.getDiagnosis());
    }

    private Timestamp getTimestampFromDatetime(LocalDateTime dateTime) {
        return new Timestamp(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime());
    }

    private BigDecimal getBigDecimalFromDouble(double value) {
        return new BigDecimal(value);
    }

    public List<Visit> getVisitsForPatient(int selectedPatientId) throws SQLException{

        // try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
        //         PreparedStatement stmt = connection.prepareStatement(patientApptsQuery)) {

        //     stmt.setInt(1, patientID);
        //     ResultSet rs = stmt.executeQuery();
        //     List<Appointment> appts = new ArrayList<Appointment>();
        //     while (rs.next()) {

        //         String firstName = rs.getString("fname");
        //         String lastName = rs.getString("lname");
        //         int doctorId = rs.getInt("doctor_id");
        //         LocalDateTime apptDatetime = rs.getTimestamp("appt_datetime").toLocalDateTime();
        //         String apptReason = rs.getString("appt_reason");
        //         Appointment appt = new Appointment(firstName, lastName, doctorId, patientID, apptDatetime, apptReason);
        //         appts.add(appt);
        //     }
        //     return appts;
        // }
        return null;
    }

    public Visit getVisitInfo(int doctorId, Date apptDate) {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(getVisitStatement)) {
            
            stmt.setInt(1, doctorId);
            stmt.setDate(2, apptDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                
                int doctorId = rs.getInt("doctor_id");
                Date apptDateTime = rs.getDate("appt_datetime");
                int nurseId = rs.getInt("nurse_id");
                int systolicPressure = rs.getInt("systolic_pressure");
                int diastolicPressure = rs.getInt("diastolic_pressure");
                double bodyTempDegreesF = rs.getDouble("body_temp_degreesF");
                int heightInches = rs.getInt("height_inches");
                double bodyWeightLbs = rs.getDouble("weight_pounds");
                int pulseBpm = rs.getInt("pulse_bpm");
                String symptoms = rs.getString("symptoms");
                String diagnosis = rs.getString("diagnosis");
            }
          //  Visit current = new Visit(doctorId, apptDateTime, nurseId, systolicPressure, diastolicPressure, bodyTempDegreesF, heightInches, bodyWeightLbs, pulseBpm, symptoms, diagnosis);
        }
            return null;
    }
}