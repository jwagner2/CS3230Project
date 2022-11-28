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
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.ethos.model.LabTest;
import main.java.ethos.model.ReportEntry;

// TODO: Auto-generated Javadoc
/**
 * The Class AdminDal.
 */
public class AdminDal {
    
    /** The column names from query. */
    private List<String> columnNamesFromQuery;
    
    /** The report query. */
    private String reportQuery ="select v.appt_datetime, v.patient_id, pa.pfname, pa.plname, dr.doctor_name, \r\n"
            + "            nu.nurse_name, lab.labs_ordered, lab.lab_results, v.diagnosis\r\n"
            + "            from (\r\n"
            + "                select v.visit_id, v.doctor_id, v.appt_datetime, v.nurse_id, v.diagnosis,a.patient_id\r\n"
            + "                from visit v\r\n"
            + "                inner join appointment a\r\n"
            + "                on DATE(v.appt_datetime) = DATE(a.appt_datetime) and v.doctor_id = a.doctor_id\r\n"
            + "                where v.appt_datetime < ? and v.appt_datetime > ?\r\n"
            + "            ) as v\r\n"
            + "            inner join (\r\n"
            + "                select p.fname as pfname, p.lname as plname, pa.patient_id\r\n"
            + "                from person p, patient pa\r\n"
            + "                where p.pid = pa.pid\r\n"
            + "            ) as pa on v.patient_id = pa.patient_id\r\n"
            + "            inner join(\r\n"
            + "                select concat(p.fname, \" \", p.lname) as doctor_name, dr.doctor_id\r\n"
            + "                from person p, doctor dr\r\n"
            + "                where p.pid = dr.pid\r\n"
            + "            ) as dr on v.doctor_id = dr.doctor_id\r\n"
            + "            inner join(\r\n"
            + "               select concat(p.fname, \" \", p.lname) as nurse_name, nu.nurse_id\r\n"
            + "            from person p, nurse nu\r\n"
            + "               where p.pid = nu.pid\r\n"
            + "            ) as nu on v.nurse_id = nu.nurse_id\r\n"
            + "            inner join(\r\n"
            + "                select GROUP_CONCAT(l.name SEPARATOR ', ') as labs_ordered, GROUP_CONCAT(lo.isAbnormal SEPARATOR ', ') as lab_results, lo.visit_id\r\n"
            + "                from lab_test_type l, lab_order lo, visit v\r\n"
            + "                where lo.visit_id = v.visit_id and l.test_id = lo.test_id\r\n"
            + "                Group by lo.visit_id\r\n"
            + "            )as lab on v.visit_id = lab.visit_id\r\n"
            + "           order by appt_datetime ASC, plname ASC";

    /**
     * Instantiates a new admin dal.
     */
    public AdminDal() {
        this.columnNamesFromQuery = new ArrayList<String>();
    }

    /**
     * Submit admin query.
     *
     * @param queryString the query string
     * @return the map
     * @throws SQLException the SQL exception
     */
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

    /**
     * Prepare results.
     *
     * @param rs the rs
     * @return the list
     * @throws SQLException the SQL exception
     */
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

    /**
     * Gets the column names.
     *
     * @return the column names
     */
    public List<String> getColumnNames() {
        return this.columnNamesFromQuery;
    }
    

    /**
     * Execute report query.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the list
     * @throws SQLException the SQL exception
     */
    public List<ReportEntry> executeReportQuery(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<ReportEntry> report = new ArrayList<ReportEntry>();
        Timestamp startTimestamp = this.getTimestampFromDatetime(startDate);
        Timestamp endTimestamp = this.getTimestampFromDatetime(endDate);
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(this.reportQuery)) {
            stmt.setTimestamp(1, endTimestamp);
            stmt.setTimestamp(2, startTimestamp);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDate apptDatetime = rs.getTimestamp("appt_datetime").toLocalDateTime().toLocalDate();
                int patientId = rs.getInt("patient_id");
                String patientFName = rs.getString("pfname");
                String patientLName = rs.getString("plname");
                String nurseName = rs.getString("nurse_name");
                String doctorName = rs.getString("doctor_name");
                String diagnosis = rs.getString("diagnosis");
                String labs = rs.getString("labs_ordered");
                String results = rs.getString("lab_results");
                ReportEntry newEntry = new ReportEntry(apptDatetime, patientId, patientFName, patientLName, nurseName, doctorName, diagnosis, labs, results);
                report.add(newEntry);
            }
            
        }
        return report;
    }


    /**
     * Gets the timestamp from datetime.
     *
     * @param dateTime the date time
     * @return the timestamp from datetime
     */
    private Timestamp getTimestampFromDatetime(LocalDate dateTime) {
        LocalTime time = LocalTime.MIN;
        LocalDateTime apptTime = dateTime.atTime(time);
        return new java.sql.Timestamp(Date.from(apptTime.atZone(ZoneId.systemDefault()).toInstant()).getTime());
    }

    /**
     * Submit query.
     *
     * @param queryString the query string
     * @return the result set
     * @throws SQLException the SQL exception
     */
    public ResultSet submitQuery(String queryString) throws SQLException {
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
                PreparedStatement stmt = connection.prepareStatement(queryString)) {
            ResultSet rs = stmt.executeQuery();
            return rs;
        }
    }
}
