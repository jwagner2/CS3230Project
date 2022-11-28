package main.java.ethos.dal;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.ethos.model.LabTest;
import main.java.ethos.model.ReportEntry;

public class AdminDal {
    
    private List<String> columnNamesFromQuery;
    private String reportQuery = "select v.appt_datetime, v.patient_id, pa.patient_name, dr.doctor_name, nu.nurse_name, lab.labs_ordered, lab.lab_results, v.diagnosis"
            + " from ("
            + "    select v.visit_id, v.doctor_id, v.appt_datetime, v.nurse_id, v.diagnosis,a.patient_id"
            + "    from visit v"
            + "    inner join appointment a"
            + "    on v.appt_datetime = a.appt_datetime and v.doctor_id = a.doctor_id"
            + "    where v.appt_datetime < ? and v.appt_datetime > ?"
            + ") as v"
            + "inner join ("
            + "    select concat(p.fname, \" \", p.lname) as patient_name, pa.patient_id"
            + "    from person p, patient pa"
            + "    where p.pid = pa.pid"
            + ") as pa on v.patient_id = pa.patient_id"
            + "inner join("
            + "    select concat(p.fname, \" \", p.lname) as doctor_name, dr.doctor_id"
            + "    from person p, doctor dr"
            + "    where p.pid = dr.pid"
            + ") as dr on v.doctor_id = dr.doctor_id"
            + "inner join("
            + "    select concat(p.fname, \" \", p.lname) as nurse_name, nu.nurse_id"
            + "    from person p, nurse nu"
            + "    where p.pid = nu.pid"
            + ") as nu on v.nurse_id = nu.nurse_id"
            + "inner join("
            + "    select GROUP_CONCAT(l.name SEPARATOR ', ') as labs_ordered, GROUP_CONCAT(lo.isAbnormal SEPARATOR ', ') as lab_results, lo.visit_id"
            + "    from lab_test_type l, lab_order lo, visit v"
            + "    where lo.visit_id = v.visit_id and l.test_id = lo.test_id"
            + "    Group by lo.visit_id"
            + ") as lab on v.visit_id = lab.visit_id"
            + "order by appt_datetime ASC, SUBSTRING_INDEX(patient_name,' ', -1) ASC";

    public AdminDal() {
        this.columnNamesFromQuery = new ArrayList<String>();
    }

    public Map<String, Object> submitAdminQuery(String queryString) throws SQLException {
        Map<String, Object> executionResult = new HashMap<String, Object>();       
        executionResult.put("result set", null);
        executionResult.put("rows affected", Integer.MIN_VALUE); 
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(queryString)) {
            boolean querySuccess = stmt.execute();
            if (querySuccess) {
                executionResult.put("result set", this.prepareResults(stmt.getResultSet()));
                executionResult.put("columns", this.columnNamesFromQuery);
            } 
            executionResult.put("rows affected", stmt.getUpdateCount());
            
        }
        return executionResult;
    }

    private List<Map<String, String>> prepareResults(ResultSet rs) throws SQLException {   
        ArrayList<String> columns = new ArrayList<String>();
        for (int index = 1; index <= rs.getMetaData().getColumnCount(); index++) {
            columns.add(rs.getMetaData().getColumnName(index));
        }
        List<Map<String, String>> rowData = new ArrayList<Map<String, String>>();
        while (rs.next()) {
            Map<String, String> data = new HashMap<String, String>();
            for (int colNum = 0; colNum < columns.size(); colNum++) {
                data.put(columns.get(colNum), String.valueOf(rs.getObject(colNum + 1)));
            }
            rowData.add(data);
        }
        this.columnNamesFromQuery = columns;
        return rowData;
    }

    public List<String> getColumnNames() {
        return this.columnNamesFromQuery;
    }
    

    public List<ReportEntry> executeReportQuery(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<ReportEntry> report = new ArrayList<ReportEntry>();
        Timestamp startTimestamp = this.getTimestampFromDatetime(LocalDateTime.from(startDate));
        Timestamp endTimestamp = this.getTimestampFromDatetime(LocalDateTime.from(endDate));
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(this.reportQuery)) {
            stmt.setTimestamp(1, endTimestamp);
            stmt.setTimestamp(2, startTimestamp);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDateTime apptDatetime = rs.getTimestamp("appt_datetime").toLocalDateTime();
                int patientId = rs.getInt("patient_id");
                String patientName = rs.getString("patient_name");
                String nurseName = rs.getString("nurse_name");
                String doctorName = rs.getString("doctor_name");
                String diagnosis = rs.getString("diagnosis");
                String labs = rs.getString("labs_ordered");
                String results = rs.getString("lab_results");
                ReportEntry newEntry = new ReportEntry(apptDatetime, patientId, patientName, nurseName, doctorName, diagnosis, labs, results);
                report.add(newEntry);
            }
            
        }
        return report;
    }


    private Timestamp getTimestampFromDatetime(LocalDateTime dateTime) {
        return new Timestamp(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime());
    }

    public ResultSet submitQuery(String queryString) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(queryString)) {
            ResultSet rs = stmt.executeQuery();
            return rs;
        }
    }
}
