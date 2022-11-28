package main.java.ethos.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.ethos.dal.AdminDal;
import main.java.ethos.model.Appointment;
import main.java.ethos.model.ReportEntry;

/**
 * The Class AdminController.
 */
public class AdminController {
    
    /** The search results. */
    private List<ReportEntry> searchResults;

    /**
     * Gets the report.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the report
     */
    public List<Map<String, Object>> getReport(LocalDate startDate, LocalDate endDate) {
        this.searchResults = new ArrayList<ReportEntry>();
        AdminDal adminDal = new AdminDal();
        try {
            this.searchResults = adminDal.executeReportQuery(startDate, endDate)

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return this.buildResultsForTable();

    /**
     * Builds the results for table.
     *
     * @return the list
     */
    public List<Map<String, Object>> buildResultsForTable() {
        if (this.searchResults == null) {
            return null;
        }
        List<Map<String, Object>> report = new ArrayList<Map<String, Object>>();
        for (ReportEntry currentEntry : this.searchResults) {
            Map<String, Object> reportEntry = new HashMap<String, Object>();
            reportEntry.put("apptDateTime", currentEntry.getApptDate());
            reportEntry.put("patientId", currentEntry.getPatientId());
            reportEntry.put("patientName", currentEntry.getPatientName());
            reportEntry.put("nurseName", currentEntry.getNurseName());
            reportEntry.put("doctorName", currentEntry.getDrName());
            reportEntry.put("diagnosis", currentEntry.getDiagnosis());
            reportEntry.put("labOrder", currentEntry.getLabsOrdered());
            reportEntry.put("labResults", currentEntry.getIsAbnormal());
            report.add(reportEntry);
        }

        return report;
    }
}
