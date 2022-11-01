package main.java.ethos.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
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
    private Map<String, Integer> allDoctors;

    /**
     * gets the search results
     * 
     * @return the search results
     */
    public List<Appointment> getResults() {
        return this.searchResults;
    }


    public List<Map<String, Object>> getPatientAppts(int patientId) {
        this.searchResults = new ArrayList<Appointment>();
        AppointmentDal apptDal = new AppointmentDal();
        try {
            this.searchResults = apptDal.getAppointmentsForPatient(patientId);
            System.out.println(this.searchResults.size());

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
            appts.put("doctor", currentAppt.getDoctorFName() + " " + currentAppt.getDoctorLName());
            appts.put("date", currentAppt.getApptDateTime().toLocalDate());
            appts.put("time", currentAppt.getApptTime());
            appts.put("reason", currentAppt.getAppointmentReason());
            apptInfo.add(appts);
        }

        return apptInfo;
    }

    public List<LocalTime> getTimes(List<LocalTime> drAppts) {
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

            }
            currentTime = currentTime.plus(subintervalLength);
        }
        return result;
    }

    public List<LocalTime> getDoctorsAvailability(String doctorName, Date date) {
        int doctorID = this.allDoctors.get(doctorName);
        AppointmentDal apptDal = new AppointmentDal();
        try {
            return this.getTimes(apptDal.getDoctorAvailability(doctorID, date));
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }
    
    public Map<String, Integer> getAllDoctors(){
        return this.allDoctors;
    }
    
    public boolean compareDates(int apptIndex) {
        Appointment toGetDateFrom = this.searchResults.get(apptIndex);
        if (toGetDateFrom.getApptDateTime().isBefore(LocalDateTime.now().minusMinutes(20))) {
            return false;
        }
        return true;
    }

    /**
     * populates with doctors and doctor ids
     */
    public void getDoctors() {
        this.allDoctors = new HashMap<>();
        AppointmentDal apptDal = new AppointmentDal();
        try {
            this.allDoctors = apptDal.getDoctorNameAndId();
        } catch (SQLException sqlEx) {
            System.out.println("Error building name:id map for doctors - \n");
            sqlEx.printStackTrace();
        }
    }
    
    public void editAppt(int indexToEdit, LocalDate newDate, String doctorName, LocalTime newTime) {
        Appointment toEdit = this.searchResults.get(indexToEdit);
        LocalDateTime originalTime = toEdit.getApptDateTime();
        LocalDateTime apptTime = newDate.atTime(newTime);
        
        toEdit.setApptDateTime(apptTime);
        toEdit.setDoctorId(this.allDoctors.get(doctorName));
        toEdit.setAppointmentReason(toEdit.getAppointmentReason());
        AppointmentDal apptDal = new AppointmentDal();
        try {
            apptDal.editApptTime(toEdit, originalTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
      
    }
    
    public void bookAppt(Appointment toBook) {
        AppointmentDal apptDal = new AppointmentDal();
        try {

            apptDal.createAppointment(toBook);
            this.searchResults.add(toBook);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
