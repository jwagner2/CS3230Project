
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

    /** The login controller. */
    private LoginController loginController;

    /** The main view controller. */
    private MainViewController mainViewController;

    /** The reg edit controller. */
    private RegisterEditController regEditController;

    /** The appt controller. */
    private ApptController apptController;

    /** The visit controller. */
    private VisitController visitController;

    /** The scene controller. */
    private SceneController sceneController;

    /** The lab controller. */
    private LabController labController;

    private ReportController reportController;

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
        this.reportController = new ReportController();
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
     * and datetime.
     *
     * @param doctorId     -- the doctor's id
     * @param apptDatetime -- the datetime of the appointment
     * @return true if the visit has taken place; false otherwise
     */
    public boolean visitExists(int doctorId, LocalDateTime apptDatetime) {
        return this.visitController.getVisitInfo(doctorId, apptDatetime) != null;
    }

    /**
     * Gets the visit info.
     *
     * @param doctorId     the doctor id
     * @param apptDateTime the appt date time
     * @return the visit info
     */
    public Map<String, String> getVisitInfo(int doctorId, LocalDateTime apptDateTime) {
        Map<String, String> visitInfo = this.visitController.getVisitInfo(doctorId, apptDateTime);
        return visitInfo;
    }

    /**
     * Update diagnosis.
     *
     * @param doctorId     the doctor id
     * @param apptDatetime the appt datetime
     * @param diagnosis    the diagnosis
     * @param isFinal      the is final
     */
    public void updateDiagnosis(int doctorId, LocalDateTime apptDatetime, String diagnosis, boolean isFinal) {
        this.visitController.updateDiagnosis(doctorId, apptDatetime, diagnosis, isFinal);
    }

    /**
     * Validate login.
     *
     * @param username the username
     * @param password the password
     * @param isAdmin  the is admin
     * @param isNurse  the is nurse
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

    /**
     * Validate visit info.
     *
     * @param fields the fields
     * @return the list
     */
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
     * Gets the user ID.
     *
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
     * Changes the current view to the main screen.
     *
     * @param currentStage - the current stage for the application
     */
    public void changeToAdminView(Stage currentStage) {
        this.sceneController.changeToAdminView(currentStage, this);

    }

    public void changeToReportView(Stage currentStage) {
        this.sceneController.changeToReportView(currentStage, this);
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

    /**
     * Change to lab view.
     *
     * @param currentStage the current stage
     */
    public void changeToLabView(Stage currentStage) {
        this.sceneController.changeToLabView(currentStage, this);
    }

    /**
     * Launch lab dialog.
     *
     * @param currentStage the current stage
     */
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
     * Changes the view to the patient visit view.
     *
     * @param currentStage the current stage
     * @param doctorId     the doctor responsible for the visit
     * @param appDateTime  the app date time
     */
    public void changeToVisit(Stage currentStage, int doctorId, LocalDateTime appDateTime) {
        this.sceneController.changeToVisitView(currentStage, this, doctorId, appDateTime);
    }

    /**
     * Change to past visits view.
     *
     * @param currentStage the current stage
     */
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
     * @return true, if successful
     */
    public boolean patientRegister(Map<String, String> patientDetails, boolean isActive) {
        return this.regEditController.patientRegister(patientDetails, isActive);
    }

    /**
     * Enter visit info.
     *
     * @param visitInfo the visit info
     * @return true, if successful
     */
    public boolean enterVisitInfo(Map<String, String> visitInfo) {

        return this.visitController.submitVisitInfo(visitInfo);
    }

    /**
     * Sets the displayed patient.
     *
     * @param indexOfPatient the new displayed patient
     */
    public void setDisplayedPatient(int patientId) {
        List<Patient> results = this.mainViewController.getResults();
        for (Patient current : results) {
            if (patientId == current.getPatientId()) {
                this.regEditController.setDisplayedPatient(current);
            }
        }

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

    /**
     * Gets the selected patient id.
     *
     * @return the selected patient id
     */
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

    /**
     * Gets the all doctors.
     *
     * @return the all doctors
     */
    public Map<String, Integer> getAllDoctors() {
        this.apptController.getDoctors();
        return this.apptController.getAllDoctors();
    }

    /**
     * Gets the patient appts.
     *
     * @return the patient appts
     */
    public List<Map<String, Object>> getPatientAppts() {
        return this.apptController.getPatientAppts(this.getSelectedPatientId());
    }

    /**
     * Gets a list of past visits for the currently selected patient.
     * 
     * @return a list of visits.
     */
    public List<Map<String, Object>> getPatientVisits() {
        return this.visitController.getPatientVisits(this.getSelectedPatientId());
    }

    /**
     * Gets the available labs.
     *
     * @return the available labs
     */
    public List<Map<String, Object>> getAvailableLabs() {
        return this.visitController.getCurrentLabs();
    }

    /**
     * Builds the appt results for table.
     *
     * @return the list
     */
    public List<Map<String, Object>> buildApptResultsForTable() {
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

    /**
     * Gets the appt times.
     *
     * @param doctorName the doctor name
     * @param date       the date
     * @return the appt times
     */
    public List<LocalTime> getApptTimes(String doctorName, Date date) {

        return this.apptController.getDoctorsAvailability(doctorName, date);
    }

    /**
     * Checks if is date after today.
     *
     * @param indexOfAppt the index of appt
     * @return true, if is date after today
     */
    public boolean isDateAfterToday(LocalDateTime time) {
        if (time.isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is date today.
     *
     * @param indexOfAppt the index of appt
     * @return true, if is date today
     */
    public boolean isDateToday(LocalDateTime dateTime) {
        if (dateTime.toLocalDate().isEqual(LocalDate.now())) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is appointment today within 15.
     *
     * @param indexOfAppt the index of appt
     * @return true, if is appointment today within 15
     */
    public boolean isAppointmentTodayWithin15(int doctorId, LocalDateTime dateTime) {
        if (this.isDateToday(dateTime) && this.apptController.compareDates(doctorId, dateTime)) {
            return true;
        }
        // changed for testing.
        return false;

    }

    /**
     * Gets the doctor id for appt.
     *
     * @param indexOfAppt the index of appt
     * @return the doctor id for appt
     */
    public int getDoctorIdForAppt(int indexOfAppt) {
        if (indexOfAppt < 0) {
            return 0;
        }
        Appointment toCheck = this.apptController.getResults().get(indexOfAppt);
        return toCheck.getDoctorId();
    }

    /**
     * Book appt.
     *
     * @param doctorId    the doctor id
     * @param dateTime    the date time
     * @param appt_reason the appt reason
     */
    public void bookAppt(int doctorId, LocalDateTime dateTime, String appt_reason) {
        Appointment toBook = new Appointment(doctorId, this.getSelectedPatientId(), dateTime, appt_reason);
        this.apptController.bookAppt(toBook);
    }

    /**
     * Edits the appt.
     *
     * @param selectedIndex the selected index
     * @param newDate       the new date
     * @param doctorName    the doctor name
     * @param newTime       the new time
     */
    public void editAppt(int selectedIndex, LocalDate newDate, String doctorName, LocalTime newTime) {
        this.apptController.editAppt(selectedIndex, newDate, doctorName, newTime);
    }

    /**
     * Gets the name of a doctor by looking up the doctor ID.
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

    /**
     * Sets the lab order.
     *
     * @param selectedItems the new lab order
     */
    @SuppressWarnings("rawtypes")
    public void setLabOrder(ObservableList<Map> selectedItems) {
        this.visitController.setLabOrder(selectedItems);

    }

    /**
     * Gets the selected visit id.
     *
     * @return the selected visit id
     */
    public int getSelectedVisitId() {
        return this.visitController.getCurrentId();
    }

    /**
     * Gets the current order names.
     *
     * @return the current order names
     */
    public List<String> getCurrentOrderNames() {
        List<String> names = new ArrayList<String>();
        if (this.visitController.getCurrentOrder() != null) {
            for (LabTest currentLab : this.visitController.getCurrentOrder()) {
                names.add(currentLab.getTestName());
            }
        }
        return names;
    }

    /**
     * Clear lab order.
     */
    public void clearLabOrder() {
        this.visitController.clearLabOrder();
    }

    /**
     * Gets the visit labs.
     *
     * @return the visit labs
     */
    public List<Map<String, Object>> getVisitLabs() {
        System.out.println("visit num: " + this.getSelectedVisitId());
        List<Map<String, Object>> labs = this.labController.getVisitLabs(this.getSelectedVisitId());
        System.out.println("num labs: " + labs.size());
        return labs;
    }

    /**
     * Gets the visit dr.
     *
     * @return the visit dr
     */
    public int getVisitDr() {
        return this.visitController.getCurrentDr();
    }

    /**
     * Gets the visit date time.
     *
     * @return the visit date time
     */
    public LocalDateTime getVisitDateTime() {
        return this.visitController.getCurrentDateTime();
    }

    /**
     * Enter test result.
     *
     * @param result     the result
     * @param isAbnormal the is abnormal
     * @param name       the name
     */
    public void enterTestResult(String result, boolean isAbnormal, String name) {
        this.labController.enterTestResult(result, isAbnormal, name);
    }
    
    public boolean isVisitFinal() {
        return this.visitController.getCurrentVisit().isFinal();
    }

    public void removeLabFromOrder(String labName) {
        this.visitController.removeLabFromOrder(labName);
        
    }

    public void searchForVisitsBetween(LocalDate startDate, LocalDate endDate) {
        this.reportController.searchForVisitsBetween(startDate, endDate);
    }
}