package main.java.ethos.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.ethos.dal.AppointmentDal;
import main.java.ethos.dal.PatientSearchDal;
import main.java.ethos.model.Appointment;
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
//    public List<Map<String, Object>> executeSearch() {
//        this.searchResults = new ArrayList<Appointment>();
//        AppointmentDal apptDal = new AppointmentDal();
//        try {
//            this.searchResults = apptDal;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        }
//        return this.buildResultsForTable();
 //   }
    
    
    public List<Map<String, Object>> getDrAppts(int doctorId) {
        this.searchResults = new ArrayList<Appointment>();
        AppointmentDal apptDal = new AppointmentDal();
        try {
            this.searchResults = apptDal.getAppointmentsForPatient(doctorId);

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
        List<Map<String, Object>> apptInfo = new ArrayList<Map<String, Object>>();
        for (Appointment currentAppt : this.searchResults) {
            Map<String, Object> appts = new HashMap<String, Object>();
            appts.put("doctor", currentAppt.getDoctorFName() + currentAppt.getDoctorLName());
            appts.put("date", currentAppt.getApptDateTime().toLocalDate());
            appts.put("time", currentAppt.getApptTime());
            appts.put("reason", currentAppt.getAppointmentReason());
            apptInfo.add(appts);
        }

        return apptInfo;
    }
    
    public List<LocalTime> getTimes(List<LocalTime> drAppts){
        List<LocalTime> result = new ArrayList<>();
        String start = "08:00:00";
        String finish = "18:00:00";

        LocalTime startTime = LocalTime.parse(start);
        LocalTime endTime = LocalTime.parse(finish);

        Duration totalTime = Duration.between(startTime, endTime);
        int subintervalCount = 10;
        Duration subintervalLength = totalTime.dividedBy(subintervalCount);

        LocalTime currentTime = startTime;
        for (int i = 0; i < subintervalCount; i++) {
            if (!drAppts.contains(currentTime)) {
                result.add(currentTime);
                currentTime = currentTime.plus(subintervalLength);
                
            }
        }

   
        return result;
    }


}
