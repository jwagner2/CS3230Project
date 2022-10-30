package main.java.ethos.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.ethos.dal.PatientSearchDal;
import main.java.ethos.model.Patient;

public class ApptController {
    /** The search results. */
    private List<Appointment> searchResults;
    /**
     * gets the search results
     * @return the search results
     */
    public List<Appointment> getResults() {
        return this.searchResults;
    }
    
    /**
     * Execute search.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param dob       the dob
     * @return the list
     */
    //public List<Map<String, Object>> executeSearch(String firstName, String lastName, Date dob) {
        //this.searchResults = new ArrayList<Appointment>();
        //PatientSearchDal searchDal = new PatientSearchDal();
        //try {
          //  this.searchResults = searchDal.patientSearch(firstName, lastName, dob);

        //} catch (SQLException e) {
          //  e.printStackTrace();

        //}
        //return this.buildResultsForTable();
    //}
    
    /**
     * Builds the results for table.
     *
     * @return the list
     */
    public List<Map<String, Object>> buildResultsForTable() {
        if (this.searchResults == null) {
            return null;
        }
        List<Map<String, Object>> apptInfo = new ArrayList<Map<String, Object>>();
        for (Appointment currentAppt : this.searchResults) {
            Map<String, Object> appts = new HashMap<String, Object>();
            appts.put("doctor", currentAppt.getDoctor());
            appts.put("date", currentAppt.getDate());
            appts.put("time", currentAppt.getTime());
            appts.put("reason", currentAppt.getReason());
            apptInfo.add(appts);
        }

        return apptInfo;
    }


}
