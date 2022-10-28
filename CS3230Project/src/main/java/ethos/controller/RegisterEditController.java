package main.java.ethos.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javafx.scene.control.ComboBox;
import main.java.ethos.dal.PatientRegEditDal;
import main.java.ethos.model.Patient;

public class RegisterEditController {
    
    /** The displayed patient. */
    private Patient displayedPatient;
    
    private ArrayList<String> states;
    
    public RegisterEditController() {
        String[] states = { "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC",
                "DE", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA",
                "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE",
                "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC",
                "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY" };
        this.states = new ArrayList<String>(Arrays.asList(states));
    }
 
    
    /**
     * Checks for selected patient.
     *
     * @return true, if successful
     */
    public boolean hasSelectedPatient() {
        return this.displayedPatient != null;
    }
    
    /**
     * Checks to ensure that the data provided as patient fields is valid.
     * 
     * @param fields - the fields to check
     * @return true if the data in the fields are valid; false otherwise.
     */
    public List<String> validateFields(Map<String, String> fields) {
        List<String> invalidFields = new ArrayList<String>();
        if ((fields.get("fname") == null || fields.get("fname").isEmpty())
                || !Pattern.matches("[A-z]*", (fields.get("fname")))) {
            System.err.println("Bad fname input");
            invalidFields.add("fname");
        }
        if ((fields.get("lname") == null || fields.get("lname").isEmpty() || !Pattern.matches("[A-z]*", (fields.get("lname"))))) {
            System.err.println("Bad lname input");
            invalidFields.add("lname");
        } 
        if (fields.get("ssn") != null && !Pattern.matches("\\d{9}", fields.get("ssn"))) {
            System.err.println("Bad ssn input");
            invalidFields.add("ssn");
        } if (fields.get("dob") == null || fields.get("dob").isBlank()) {
            System.err.println("Bad dob input");
            invalidFields.add("dob");
        } if (fields.get("phone") == null || !Pattern.matches("\\d{10}", fields.get("phone"))) {
            System.err.println("Bad phone input");
            invalidFields.add("phone");
        } if (fields.get("addressOne") == null || fields.get("addressOne").isBlank()) {
            System.err.println("Bad address one input");
            invalidFields.add("addressOne");
        } if (!(fields.get("addressTwo") == null) && !fields.get("addressTwo").isEmpty() && !Pattern.matches("[A-z0-9]*", fields.get("addressTwo"))) {
            System.err.println("Bad address two input");
            invalidFields.add("addressTwo");
        } if (fields.get("zip") == null || !Pattern.matches("\\d{5}", fields.get("zip"))) {
            System.err.println("Bad zip input");
            invalidFields.add("zip");
        } if (fields.get("state") == null || !this.states.contains(fields.get("state"))) {
            System.err.println("Bad state input");
            invalidFields.add("state");
        } if (fields.get("gender") == null
                || (!fields.get("gender").equals("M") && !fields.get("gender").equals("F"))) {
            System.err.println("Bad gender input");
            invalidFields.add("gender");
        }

        return invalidFields;
    }
    
    /**
     * Register edit patient.
     */
    public boolean registerEditPatient() {
        PatientRegEditDal regEdit = new PatientRegEditDal();
        try {
            regEdit.registerEditPatient(this.displayedPatient);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Patient register.
     *
     * @param patientDetails the patient details
     * @param isActive       the is active
     */
    public boolean patientRegister(Map<String, String> patientDetails, boolean isActive) {
        java.util.Date langDate;
        java.sql.Date sqlDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            langDate = sdf.parse(patientDetails.get("dob"));
            sqlDate = new java.sql.Date(langDate.getTime());
            if (this.hasSelectedPatient()) {
                this.editPatientDetails(patientDetails, isActive, sqlDate);
            } else {
                return this.registerNewPatient(patientDetails, isActive, sqlDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Edits the patient details.
     *
     * @param patientDetails the patient details
     * @param isActive the is active
     * @param sqlDate the sql date
     */
    private void editPatientDetails(Map<String, String> patientDetails, boolean isActive, java.sql.Date sqlDate) {
        this.displayedPatient.setFirstName(patientDetails.get("fname"));
        this.displayedPatient.setLastName(patientDetails.get("lname"));
        this.displayedPatient.setSsn(patientDetails.get("ssn"));
        this.displayedPatient.setBirthDate(getPatientDob());
        this.displayedPatient.setBirthDate(sqlDate);
        this.displayedPatient.setIsActive(isActive);
        this.displayedPatient.setContactNumber(patientDetails.get("phone"));
        this.displayedPatient.setAddress(patientDetails.get("addressOne"), patientDetails.get("addressTwo"),
                patientDetails.get("zip"), patientDetails.get("state"));
        this.displayedPatient.setGender(patientDetails.get("gender").charAt(0));
        this.registerEditPatient();
    }

    /**
     * Register new patient.
     *
     * @param patientDetails the patient details
     * @param isActive the is active
     * @param sqlDate the sql date
     */
    private boolean registerNewPatient(Map<String, String> patientDetails, boolean isActive, Date sqlDate) {

        char gender = patientDetails.get("gender").charAt(0);
        this.displayedPatient = new Patient(patientDetails.get("fname"), patientDetails.get("lname"),
                patientDetails.get("ssn"),
                sqlDate, isActive, patientDetails.get("phone"), patientDetails.get("addressOne"),
                patientDetails.get("addressTwo"),
                patientDetails.get("zip"), patientDetails.get("state"), gender);
        return this.registerEditPatient();

    }
    
    /**
     * Sets the displayed patient.
     *
     * @param indexOfPatient the new displayed patient
     */
    public void setDisplayedPatient(Patient displayed) {
        System.out.println(displayed.getFirstName());
        this.displayedPatient = displayed;
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
     * Gets patient gender.
     *
     * @return the gender
     */
    public String getPatientGender() {
        return Character.toString(this.displayedPatient.getGender());
    }

    /**
     * Gets the patient dob.
     *
     * @return the patient dob
     */
    public Date getPatientDob() {
        return this.displayedPatient.getBirthDate();
    }
    
    /**
     * Clears.
     */
    public void clearDisplayedPatient() {
        this.displayedPatient = null;

    }
    
    /**
     * Populates the states ComboBox for patient register/edit.
     *
     * @param statesCombo - the states combo box
     */
    public void populateStatesComboBox(ComboBox<String> statesCombo) {
        for (String state : this.states) {
            statesCombo.getItems().add(state);
        }
    }


}
