
package main.java.ethos.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.java.ethos.dal.LoginDal;
import main.java.ethos.dal.PatientRegEditDal;
import main.java.ethos.dal.PatientSearchDal;
import main.java.ethos.model.PageType;
import main.java.ethos.model.Patient;
import main.java.ethos.model.User;
import main.java.ethos.view.MainView;
import main.java.ethos.view.PatientInfoView;

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
     * Changes the current view to the main screen
     * @param currentStage - the current stage for the application
     */
    public void changeToMainView(Stage currentStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PageType.class.getResource(PageType.MAIN.label));
            Parent parent = loader.load();

            MainView mainView = loader.<MainView>getController();
            mainView.initialize(this);

            Scene scene = new Scene(parent);
            currentStage.setTitle("ethos");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (MalformedURLException murlerr) {
            System.err.println("Bad FXML URL");
            murlerr.printStackTrace();
        } catch (IOException ioerr) {
            System.err.println("Bad file");
            ioerr.printStackTrace();
        }
        
    }

    /**
     * Changes the view to the patient info view
     * @param currentStage - the current stage for the application
     */
    public void changeToPatientInfoView(Stage currentStage) {
        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PageType.class.getResource(PageType.EDIT_INFO.label));
            Parent parent = loader.load();

            PatientInfoView infoView = loader.<PatientInfoView>getController();
            infoView.initialize(this);

            Scene scene = new Scene(parent);
            currentStage.setTitle("ethos");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (MalformedURLException murlerr) {
            System.err.println("Bad FXML URL");
            murlerr.printStackTrace();
        } catch (IOException ioerr) {
            System.err.println("Bad file");
            ioerr.printStackTrace();
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

    /**
     * Populates the states ComboBox for patient register/edit
     * @param statesCombo - the states combo box
     */
    public void populateStatesComboBox(ComboBox<String> statesCombo) {
        String[] states = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC",  
        "DE", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA",  
        "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE",  
        "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC",  
        "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"};

        for (String state : states) {
            statesCombo.getItems().add(state);
        }
    }

}
