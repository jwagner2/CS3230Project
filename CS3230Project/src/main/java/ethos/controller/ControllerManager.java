
package main.java.ethos.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.ethos.dal.LoginDal;
import main.java.ethos.dal.PatientRegEditDal;
import main.java.ethos.dal.PatientSearchDal;
import main.java.ethos.model.PageType;
import main.java.ethos.model.Patient;
import main.java.ethos.model.User;

/**
 * The Class ControllerManager.
 */
public class ControllerManager {

    private User loggedInUser;
    private Patient displayedPatient;
    private List<Patient> searchResults;

    /**
     * Validate login.
     *
     * @param username the username
     * @param password the password
     * @return true, if successful
     */
    public boolean validateLogin(String username, String password) {
        LoginDal valid8r = new LoginDal();
        System.out.println("here");
        try {
            this.loggedInUser = valid8r.login(username, password, true, true);
            if (this.loggedInUser != null) {
                System.out.println("Login success");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     * Gets the logged in name.
     *
     * @return the logged in name
     */
    public String getLoggedInName() {
        String name = this.loggedInUser.getFirstName();
        name += " " + this.loggedInUser.getLastName();
        return name;
    }

    /**
     * Gets the logged in user name.
     *
     * @return the logged in user name
     */
    public String getLoggedInUserName() {
        return this.loggedInUser.getUserName();
    }

    /**
     * Change view.
     *
     * @param page         the page
     * @param currentStage the current stage
     */
    public void changeView(PageType page, Stage currentStage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(page.label));
            Scene scene = new Scene(parent);
            currentStage.setTitle("ethos");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute search.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param dob       the dob
     * @return the list
     */
    public List<Patient> executeSearch(String firstName, String lastName, Date dob) {
        this.searchResults = new ArrayList<Patient>();
        PatientSearchDal searchDal = new PatientSearchDal();
        try {
            this.searchResults = searchDal.patientSearch(firstName, lastName, dob);

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return this.searchResults;
    }

    public void registerEditPatient() {
        PatientRegEditDal regEdit = new PatientRegEditDal();
        try {
            regEdit.registerEditPatient(this.displayedPatient);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void patientRegister(Map<String, String> patientDetails, boolean isActive) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        java.util.Date langDate;
        try {
            langDate = sdf.parse(patientDetails.get("dob"));
            java.sql.Date sqlDate = new java.sql.Date(langDate.getTime());
            char gender = patientDetails.get("gender").charAt(0);
            this.displayedPatient = new Patient(patientDetails.get("fname"), patientDetails.get("lname"),
                    patientDetails.get("ssn"),
                    sqlDate, isActive, patientDetails.get("phone"), patientDetails.get("addressOne"),
                    patientDetails.get("addressTwo"),
                    patientDetails.get("zip"), patientDetails.get("state"), gender);
            this.registerEditPatient();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets the displayed patient.
     *
     * @param indexOfPatient the new displayed patient
     */
    public void setDisplayedPatient(int indexOfPatient) {
        this.displayedPatient = this.searchResults.get(indexOfPatient);
    }

    /**
     * Gets the patient first name.
     *
     * @return the patient first name
     */
    public String getPatientFirstName() {
        return this.displayedPatient.getFirstName();
    }

    /**
     * Gets the patient last name.
     *
     * @return the patient last name
     */
    public String getPatientLastName() {
        return this.displayedPatient.getLastName();
    }

    /**
     * Gets the patient ssn.
     *
     * @return the patient ssn
     */
    public String getPatientSsn() {
        return this.displayedPatient.getSsn();
    }

    /**
     * Gets the patient is active.
     *
     * @return the patient is active
     */
    public boolean getPatientIsActive() {
        return this.displayedPatient.isActive();
    }

    /**
     * Gets the patient contact number.
     *
     * @return the patient contact number
     */
    public String getPatientContactNumber() {
        return this.displayedPatient.getContactNumber();
    }

    /**
     * Gets the patient address one.
     *
     * @return the patient address one
     */
    public String getPatientAddressOne() {
        return this.displayedPatient.getAddressOne();
    }

    /**
     * Gets the patient address two.
     *
     * @return the patient address two
     */
    public String getPatientAddressTwo() {
        return this.displayedPatient.getAddressTwo();
    }

    /**
     * Gets the patient zip.
     *
     * @return the patient zip
     */
    public String getPatientZip() {
        return this.displayedPatient.getAddressZip();
    }

    /**
     * Gets the patient state.
     *
     * @return the patient state
     */
    public String getPatientState() {
        return this.displayedPatient.getAddressState();
    }

    /**
     * Gets the patient gender.
     *
     * @return the patient gender
     */
    public char getPatientGender() {
        return this.getPatientGender();
    }
}
