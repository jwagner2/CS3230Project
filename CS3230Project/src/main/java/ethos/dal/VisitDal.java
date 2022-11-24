package main.java.ethos.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import main.java.ethos.model.Visit;

/**
 * The Class VisitDal.
 */
public class VisitDal {

    /** The submit visit info statement. */
    private String submitVisitInfoStatement = "insert into visit (is_final, doctor_id, appt_datetime, nurse_id, systolic_pressure, diastolic_pressure, body_temp_degreesF, height_inches, weight_pounds, pulse_bpm, symptoms, diagnosis)"
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    /** The get doctor by id. */
    private String getDoctorById = "select fname, lname from doctor d join person p on d.pid = p.pid where d.doctor_id = ?";

    /** The get patient visits. */
    private String getPatientVisits = "select v.* from appointment a join visit v on a.doctor_id = v.doctor_id and a.appt_datetime = v.appt_datetime where a.patient_id = ? and v.appt_save_time < NOW()";

    /** The get visits by doctor and datetime query. */
    private String getVisitsByDoctorAndDatetimeQuery = "select * from visit where doctor_id = ? and appt_datetime = ?";

    /** The update diagnosis statement. */
    private String updateDiagnosisStatement = "update visit set diagnosis = ?, is_final = ? where doctor_id = ? and appt_datetime = ?";
    
    /** The get visit id. */
    private String getVisitId = "SELECT MAX(visit_id) from visit;";

    //TODO: finish query
    private String getVisitsBetweenQuery = "select v.visit_id, ";

    /**
     * Update diagnosis.
     *
     * @param doctorId the doctor id
     * @param apptDatetime the appt datetime
     * @param diagnosis the diagnosis
     * @param isFinal the is final
     * @throws SQLException the SQL exception
     */
    public void updateDiagnosis(int doctorId, LocalDateTime apptDatetime, String diagnosis, boolean isFinal) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(updateDiagnosisStatement)) {

            stmt.setString(1, diagnosis);
            stmt.setBoolean(2, isFinal);
            stmt.setInt(3, doctorId);
            stmt.setTimestamp(4, this.getTimestampFromDatetime(apptDatetime));
            int rs = stmt.executeUpdate();
            System.out.println("visit -- rows affected = " + rs);
        }
    }

    /**
     * Gets the doctor for visit.
     *
     * @param doctorId the doctor id
     * @return the doctor for visit
     * @throws SQLException the SQL exception
     */
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

    /**
     * Enter visit info.
     *
     * @param visit the visit
     * @throws SQLException the SQL exception
     */
    public void enterVisitInfo(Visit visit) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(submitVisitInfoStatement)) {

            this.setStatement(visit, stmt);
            int rs = stmt.executeUpdate();
            
            System.out.println("visit -- rows affected = " + rs);
        }
    }
    
    /**
     * Gets the last visit id.
     *
     * @return the last visit id
     * @throws SQLException the SQL exception
     */
    public int getLastVisitId() throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(this.getVisitId)) {
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int id = rs.getInt(1);
                return id;
             }
           
        }
        return -1; 
    }

    /**
     * Sets the statement.
     *
     * @param visit the visit
     * @param stmt the stmt
     * @throws SQLException the SQL exception
     */
    private void setStatement(Visit visit, PreparedStatement stmt) throws SQLException {
        stmt.setBoolean(1, visit.isFinal());
        stmt.setInt(2, visit.getDoctorId());
        stmt.setTimestamp(3, this.getTimestampFromDatetime(visit.getApptDateTime()));
        stmt.setInt(4, visit.getNurseId());
        stmt.setInt(5, visit.getSystolicPressure());
        stmt.setInt(6, visit.getDiastolicPressure());
        stmt.setBigDecimal(7, this.getBigDecimalFromDouble(visit.getBodyTempDegreesF()));
        stmt.setInt(8, visit.getHeightInches());
        stmt.setBigDecimal(9, this.getBigDecimalFromDouble(visit.getBodyWeightLbs()));
        stmt.setInt(10, visit.getPulseBpm());
        stmt.setString(11, visit.getSymptoms());
        stmt.setString(12, visit.getDiagnosis());
    }

    /**
     * Gets the timestamp from datetime.
     *
     * @param dateTime the date time
     * @return the timestamp from datetime
     */
    private Timestamp getTimestampFromDatetime(LocalDateTime dateTime) {
        return new Timestamp(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime());
    }

    /**
     * Gets the big decimal from double.
     *
     * @param value the value
     * @return the big decimal from double
     */
    private BigDecimal getBigDecimalFromDouble(double value) {
        return new BigDecimal(value);
    }

    /**
     * Gets the visits for patient.
     *
     * @param selectedPatientId the selected patient id
     * @return the visits for patient
     * @throws SQLException the SQL exception
     */
    public List<Visit> getVisitsForPatient(int selectedPatientId) throws SQLException {

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(this.getPatientVisits)) {

            stmt.setInt(1, selectedPatientId);
            ResultSet rs = stmt.executeQuery();
            List<Visit> visits = new ArrayList<Visit>();
            while (rs.next()) {
                int rDoctorId = rs.getInt("doctor_id");
                LocalDateTime apptDateTime = rs.getTimestamp("appt_datetime").toLocalDateTime();
                int nurseId = rs.getInt("nurse_id");
                int systolicPressure = rs.getInt("systolic_pressure");
                int diastolicPressure = rs.getInt("diastolic_pressure");
                double bodyTempDegreesF = rs.getDouble("body_temp_degreesF");
                int heightInches = rs.getInt("height_inches");
                double bodyWeightLbs = rs.getDouble("weight_pounds");
                int pulseBpm = rs.getInt("pulse_bpm");
                String symptoms = rs.getString("symptoms");
                String diagnosis = rs.getString("diagnosis");
                boolean isFinal = rs.getBoolean("is_final");
                Visit current = new Visit(systolicPressure, diastolicPressure, bodyWeightLbs, heightInches,
                        bodyTempDegreesF, pulseBpm, symptoms, diagnosis, rDoctorId, nurseId, apptDateTime, isFinal);
                visits.add(current);
            }
            return visits;
        }
    }

    /**
     * Gets the visit that matches the specified doctor id and datetime.
     *
     * @param doctorId -- the doctor's id
     * @param apptDT -- the appointment datetime
     * @return the matching visit if it exists, null otherwise
     * @throws SQLException the SQL exception
     */
    public Visit getVisitByDoctorAndDatetime(int doctorId, LocalDateTime apptDT) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(this.getVisitsByDoctorAndDatetimeQuery)) {

            stmt.setInt(1, doctorId);
            stmt.setTimestamp(2, this.getTimestampFromDatetime(apptDT));

            ResultSet rs = stmt.executeQuery();
            Visit visit = null;
            while (rs.next()) {
                int rDoctorId = rs.getInt("doctor_id");
                LocalDateTime apptDateTime = rs.getTimestamp("appt_datetime").toLocalDateTime();
                int nurseId = rs.getInt("nurse_id");
                int systolicPressure = rs.getInt("systolic_pressure");
                int diastolicPressure = rs.getInt("diastolic_pressure");
                double bodyTempDegreesF = rs.getDouble("body_temp_degreesF");
                int heightInches = rs.getInt("height_inches");
                double bodyWeightLbs = rs.getDouble("weight_pounds");
                int pulseBpm = rs.getInt("pulse_bpm");
                String symptoms = rs.getString("symptoms");
                String diagnosis = rs.getString("diagnosis");
                boolean isFinal = rs.getBoolean("is_final");
                visit = new Visit(systolicPressure, diastolicPressure, bodyWeightLbs, heightInches,
                        bodyTempDegreesF, pulseBpm, symptoms, diagnosis, rDoctorId, nurseId, apptDateTime, isFinal);
                visit.setVisitId(rs.getInt("visit_id"));
            }
            return visit;
        }
    }

    public void searchForVisitsBetween(LocalDate startDate, LocalDate endDate) {
        Timestamp startTimestamp = this.getTimestampFromDatetime(LocalDateTime.from(startDate));
        Timestamp endTimestamp = this.getTimestampFromDatetime(LocalDateTime.from(endDate));
    }

}