package main.java.ethos.dal;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import main.java.ethos.model.Visit;

public class VisitDal {

    private String submitVisitInfoStatement = "insert into visit (doctor_id, appt_datetime, nurse_id, systolic_pressure, diastolic_pressure, body_temp_degreesF, height_inches, weight_pounds, pulse_bpm, symptoms, diagnosis)"
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    //TODO: Implement update diagnosis (not required for Iteration 3)
    // private String updateDiagnosisStatement = "update visit set diagnosis = ? where visit_id = ?";

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
}