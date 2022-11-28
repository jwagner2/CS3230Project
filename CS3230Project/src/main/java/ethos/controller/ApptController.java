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
import main.java.ethos.model.Appointment;

// TODO: Auto-generated Javadoc
/**
 * The Class ApptController.
 */
public class ApptController {
    /** The search results. */
    private List<Appointment> searchResults;
    
    /** The all doctors. */
    private Map<String, Integer> allDoctors;

    /**
     * gets the search results.
     *
     * @return the search results
     */
    public List<Appointment> getResults() {
        return this.searchResults;
    }


    /**
     * Gets the patient appts.
     *
     * @param patientId the patient id
     * @return the patient appts
     */
    public List<Map<String, Object>> getPatientAppts(int patientId) {
        this.searchResults = new ArrayList<Appointment>();
        AppointmentDal apptDal = new AppointmentDal();
        try {
            this.searchResults = apptDal.getAppointmentsForPatient(patientId);

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
            appts.put("id", currentAppt.getDoctorId());
            appts.put("dateTime", currentAppt.getApptDateTime());
            apptInfo.add(appts);
        }

        return apptInfo;
    }

    /**
     * Gets the times.
     *
     * @param drAppts the dr appts
     * @return the times
     */
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

    /**
     * Gets the doctors availability.
     *
     * @param doctorName the doctor name
     * @param date the date
     * @return the doctors availability
     */
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
    
    /**
     * Gets the all doctors.
     *
     * @return the all doctors
     */
    public Map<String, Integer> getAllDoctors(){
        return this.allDoctors;
    }
    
    /**
     * Compare dates.
     *
     * @param doctorId the doctor id
     * @param time the time
     * @return true, if successful
     */
    public boolean compareDates(int doctorId, LocalDateTime time) {
        Appointment toGetDateFrom = this.getByKey(doctorId, time);
        if (toGetDateFrom.getApptDateTime().isBefore(LocalDateTime.now().minusMinutes(20))) {
            return false;
        }
        return true;
    }
    
    /**
     * Gets the by key.
     *
     * @param doctorId the doctor id
     * @param time the time
     * @return the by key
     */
    private Appointment getByKey(int doctorId, LocalDateTime time) {
        for (Appointment current: this.searchResults) {
            if (doctorId == current.getDoctorId() && time == current.getApptDateTime()) {
                return current;
            }
        }
        return null;
    }

    /**
     * populates with doctors and doctor ids.
     *
     * @return the doctors
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
    
    /**
     * Edits the appt.
     *
     * @param indexToEdit the index to edit
     * @param newDate the new date
     * @param doctorName the doctor name
     * @param newTime the new time
     */
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
    
    /**
     * Book appt.
     *
     * @param toBook the to book
     */
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
