
package main.java.ethos.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.java.ethos.model.Appointment;
import main.java.ethos.model.LabTest;
import main.java.ethos.model.Patient;
import main.java.ethos.model.UserRole;

/**
 * The Class ControllerManager.
 */
public class ControllerManager {
    
    private LoginController loginController;
    
    private MainViewController mainViewController;
    
    private RegisterEditController regEditController;

    private ApptController apptController;

    private VisitController visitController;
    
    private SceneController sceneController;
    
    private LabController labController;
    
    /**
     * Instantiates a new controller manager.
     */
    public ControllerManager() {

        this.loginController = new LoginController();
        this.mainViewController = new MainViewController();
        this.regEditController = new RegisterEditController();
        this.sceneController = new SceneController();
        this.apptController = new ApptController();
        this.visitController = new VisitController();
        this.labController = new LabController();
    }

    /**
     * Checks for selected patient.
     *
     * @return true, if successful
     */
    public boolean hasSelectedPatient() {
        return this.regEditController.hasSelectedPatient();
    }

    /**
     * Determines if a visit has already been made for the specified doctor
     * and datetime
     * @param doctorId -- the doctor's id
     * @param apptDatetime -- the datetime of the appointment
     * @return true if the visit has taken place; false otherwise
     */
    public boolean visitExists(int doctorId, LocalDateTime apptDatetime) {
        Map<String, String> stuff = this.visitController.getVisitInfo(doctorId, apptDatetime);
        return this.visitController.getVisitInfo(doctorId, apptDatetime) != null;
    }

    /**
     * 
     * @param doctorId
     * @param apptDateTime
     * @return
     */
    public Map<String, String> getVisitInfo(int doctorId, LocalDateTime apptDateTime) {
        Map<String, String> visitInfo = this.visitController.getVisitInfo(doctorId, apptDateTime);
        return visitInfo;
    }

    public void updateDiagnosis(int doctorId, LocalDateTime apptDatetime, String diagnosis, boolean isFinal) {
        this.visitController.updateDiagnosis(doctorId, apptDatetime, diagnosis, isFinal);
    }
    
    /**
     * Validate login.
     *
     * @param username the username
     * @param password the password
     * @return true, if successful
     */
    public boolean validateLogin(String username, String password, boolean isAdmin, boolean isNurse) {
        UserRole role = null;
        if (isAdmin) {
            role = UserRole.ADMIN;
        } else if (isNurse) {
            role = UserRole.NURSE;
        }
        return this.loginController.validateLogin(username, password, role);
    }

    /**
     * Checks to ensure that the data provided as patient fields is valid.
     * 
     * @param fields - the fields to check
     * @return true if the data in the fields are valid; false otherwise.
     */
    public List<String> validatePatientInfo(Map<String, String> fields) {
        return this.regEditController.validateFields(fields);
    }

    public List<String> validateVisitInfo(Map<String, String> fields) {
        fields.put("nurseId", String.valueOf(getLoggedInUserId()));
        return this.visitController.validateVisitFields(fields);
    }

    /**
     * Gets the logged in name.
     *
     * @return the logged in name
     */
    public String getLoggedInName() {
        return this.loginController.getLoggedInName();
    }

    /**
     * Gets the logged in user name.
     *
     * @return the logged in user name
     */
    public String getLoggedInUserName() {
        return this.loginController.getLoggedInUserName();
    }

    /**
     * Gets the user ID
     * @return the id
     */
    public int getLoggedInUserId() {
        return this.loginController.getLoggedInUser().getUserId();
    }


    /**
     * Changes the current view to the main screen.
     *
     * @param currentStage - the current stage for the application
     */
    public void changeToMainView(Stage currentStage) {
        this.sceneController.changeToMainView(currentStage, this);

    }

    /**
     * Change to login.
     *
     * @param currentStage the current stage
     */
    public void changeToLogin(Stage currentStage) {
        this.loginController = new LoginController();
        if (this.mainViewController.getResults() != null) {
            this.mainViewController.getResults().clear();
        }
        this.sceneController.changeToLogin(currentStage, this);

    }
    
    public void changeToLabView(Stage currentStage) {
        this.sceneController.changeToLabView(currentStage, this);
    }
    
    public void launchLabDialog(Stage currentStage) {
        
        this.sceneController.launchLabOrderDialog(currentStage, this);
    }
    
    /**
     * Changes the current view to the main screen.
     *
     * @param currentStage - the current stage for the application
     */
    public void changeToApptView(Stage currentStage) {
        this.sceneController.changeToApptView(currentStage, this);

    }

    /**
     * Changes the view to the patient info view.
     *
     * @param currentStage - the current stage for the application
     */
    public void changeToPatientInfoView(Stage currentStage) {
        this.sceneController.changeToPatientInfoView(currentStage, this);
    }

    /**
     * Changes the view to the patient visit view
     * @param currentStage the current stage
     * @param doctorId the doctor responsible for the visit
     */
    public void changeToVisit(Stage currentStage, int doctorId, LocalDateTime appDateTime) {
        this.sceneController.changeToVisitView(currentStage, this, doctorId, appDateTime);
    }

    public void changeToPastVisitsView(Stage currentStage) {
        this.sceneController.changeToPastVisitsView(currentStage, this);
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
        return this.mainViewController.executeSearch(firstName, lastName, dob);
    }

    /**
     * Builds the results for table.
     *
     * @return the list
     */
    public List<Map<String, Object>> buildResultsForTable() {
        return this.mainViewController.buildResultsForTable();
    }

    /**
     * Patient register.
     *
     * @param patientDetails the patient details
     * @param isActive       the is active
     */
    public boolean patientRegister(Map<String, String> patientDetails, boolean isActive) {
        return this.regEditController.patientRegister(patientDetails, isActive);
    }

    public boolean enterVisitInfo(Map<String, String> visitInfo) {
        
        return this.visitController.submitVisitInfo(visitInfo);
    }

    /**
     * Sets the displayed patient.
     *
     * @param indexOfPatient the new displayed patient
     */
    public void setDisplayedPatient(int indexOfPatient) {
        List<Patient> results = this.mainViewController.getResults();
        this.regEditController.setDisplayedPatient(results.get(indexOfPatient));
    }

    /**
     * Gets the patient first name.
     *
     * @return the patient first name
     */
    public String getPatientFirstName() {
        return this.regEditController.getPatientFirstName();
    }

    /**
     * Gets the patient last name.
     *
     * @return the patient last name
     */
    public String getPatientLastName() {
        return this.regEditController.getPatientLastName();
    }

    /**
     * Gets the patient ssn.
     *
     * @return the patient ssn
     */
    public String getPatientSsn() {
        return this.regEditController.getPatientSsn();
    }
    
    public int getSelectedPatientId() {
        return this.regEditController.getSelectedPatientId();
    }

    /**
     * Gets the patient is active.
     *
     * @return the patient is active
     */
    public boolean getPatientIsActive() {
        return this.regEditController.getPatientIsActive();
    }

    /**
     * Gets the patient contact number.
     *
     * @return the patient contact number
     */
    public String getPatientContactNumber() {
        return this.regEditController.getPatientContactNumber();
    }

    /**
     * Gets the patient address one.
     *
     * @return the patient address one
     */
    public String getPatientAddressOne() {
        return this.regEditController.getPatientAddressOne();
    }

    /**
     * Gets the patient address two.
     *
     * @return the patient address two
     */
    public String getPatientAddressTwo() {
        return this.regEditController.getPatientAddressTwo();
    }

    /**
     * Gets the patient zip.
     *
     * @return the patient zip
     */
    public String getPatientZip() {
        return this.regEditController.getPatientZip();
    }

    /**
     * Gets the patient state.
     *
     * @return the patient state
     */
    public String getPatientState() {
        return this.regEditController.getPatientState();
    }

    /**
     * Gets patient gender.
     *
     * @return the gender
     */
    public String getPatientGender() {
        return this.regEditController.getPatientGender();
    }

    /**
     * Gets the patient dob.
     *
     * @return the patient dob
     */
    public Date getPatientDob() {
        return this.regEditController.getPatientDob();
    }

    /**
     * Clears.
     */
    public void clearDisplayedPatient() {
        this.regEditController.clearDisplayedPatient();

    }
    
    public Map<String, Integer> getAllDoctors() {
        this.apptController.getDoctors();
        return this.apptController.getAllDoctors();
    }
    
    public List<Map<String, Object>> getPatientAppts(){
       return this.apptController.getPatientAppts(this.getSelectedPatientId());
    }

    /**
     * Gets a list of past visits for the currently selected patient.
     * @return a list of visits.
     */
    public List<Map<String, Object>> getPatientVisits() {
        return this.visitController.getPatientVisits(this.getSelectedPatientId());
    }
    
    public List<Map<String, Object>> getAvailableLabs(){
        return this.visitController.getCurrentLabs();
    }
    
    public List<Map<String,Object>> buildApptResultsForTable() {
        return this.apptController.buildResultsForTable();
    }    

    /**
     * Populates the states ComboBox for patient register/edit.
     *
     * @param statesCombo - the states combo box
     */
    public void populateStatesComboBox(ComboBox<String> statesCombo) {
        
        this.regEditController.populateStatesComboBox(statesCombo);
    }
    
    public List<LocalTime> getApptTimes(String doctorName, Date date) {
        
        return this.apptController.getDoctorsAvailability(doctorName, date);
    }

    public boolean isDateAfterToday(int indexOfAppt) {
        if (indexOfAppt < 0) {
            return false;
        }
        Appointment toCheck = this.apptController.getResults().get(indexOfAppt);
        if (toCheck.getApptDateTime().isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }
    
    public boolean isDateToday(int indexOfAppt) {
        if (indexOfAppt < 0) {
            return false;
        }
        Appointment toCheck = this.apptController.getResults().get(indexOfAppt);
        if (toCheck.getApptDateTime().toLocalDate().isEqual(LocalDate.now())) {
            return true;
        }
        return false;
    }
    public boolean isAppointmentTodayWithin15(int indexOfAppt) {
        if (indexOfAppt < 0) {
            return false;
        }
        if (this.isDateToday(indexOfAppt) && this.apptController.compareDates(indexOfAppt)) {
            return true;
        }
        //changed for testing.
        return true;
        
    }

    public int getDoctorIdForAppt(int indexOfAppt) {
        if (indexOfAppt < 0) {
            return 0;
        }
        Appointment toCheck = this.apptController.getResults().get(indexOfAppt);
        return toCheck.getDoctorId();
    }

    public void bookAppt(int doctorId, LocalDateTime dateTime, String appt_reason) {
        Appointment toBook = new Appointment(doctorId, this.getSelectedPatientId(), dateTime, appt_reason);
        this.apptController.bookAppt(toBook);
    }

    public void editAppt(int selectedIndex, LocalDate newDate, String doctorName, LocalTime newTime) {
       this.apptController.editAppt(selectedIndex, newDate, doctorName, newTime);
    }

    /**
     * Gets the name of a doctor by looking up the doctor ID
     * 
     * @param doctorId the doctor ID
     * @return the matching doctor's name
     */
    public String getDoctorName(int doctorId) {
        Map<String, Integer> docs = this.getAllDoctors();
        Object[] names = docs.keySet().toArray();
        String match = "";
        for (int i = 0; i < names.length; i++) {
            if (docs.get((String) names[i]).equals(doctorId)) {
                match = (String) names[i];
            }
        }
        return match;
    }

    public void setLabOrder(ObservableList<Map> selectedItems) {
        this.visitController.setLabOrder(selectedItems);
        
    }
    
    public int getSelectedVisitId() {
        return this.visitController.getCurrentId();
    }
    public List<String> getCurrentOrderNames() {
        List<String> names = new ArrayList<String>();
        for (LabTest currentLab : this.visitController.getCurrentOrder()) {
            names.add(currentLab.getTestName());
        }
        return names;
        
    }
    public void clearLabOrder() {
        this.visitController.clearLabOrder();
    }

    public List<Map<String, Object>> getVisitLabs() {
        System.out.println("visit num: " + this.getSelectedVisitId());
        List<Map<String, Object>> labs = this.labController.getVisitLabs(this.getSelectedVisitId());
        System.out.println("num labs: " + labs.size());
        return labs;
    }
}