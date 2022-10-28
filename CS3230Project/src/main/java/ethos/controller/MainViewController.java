package main.java.ethos.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.ethos.dal.PatientSearchDal;
import main.java.ethos.model.Patient;

public class MainViewController {
    

    
    /** The search results. */
    private List<Patient> searchResults;
    /**
     * gets the search results
     * @return the search results
     */
    public List<Patient> getResults() {
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
    public List<Map<String, Object>> executeSearch(String firstName, String lastName, Date dob) {
        this.searchResults = new ArrayList<Patient>();
        PatientSearchDal searchDal = new PatientSearchDal();
        try {
            this.searchResults = searchDal.patientSearch(firstName, lastName, dob);

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return this.buildResultsForTable();
    }
    
    /**
     * Builds the results for table.
     *
     * @return the list
     */
    public List<Map<String, Object>> buildResultsForTable() {
        if (this.searchResults == null) {
            return null;
        }
        List<Map<String, Object>> patientInfo = new ArrayList<Map<String, Object>>();
        for (Patient currentPatient : this.searchResults) {
            Map<String, Object> patient = new HashMap<String, Object>();
            patient.put("firstName", currentPatient.getFirstName());
            patient.put("lastName", currentPatient.getLastName());
            patient.put("dob", currentPatient.getBirthDate());
            patient.put("phone", currentPatient.getContactNumber());
            patientInfo.add(patient);
        }

        return patientInfo;
    }


}
