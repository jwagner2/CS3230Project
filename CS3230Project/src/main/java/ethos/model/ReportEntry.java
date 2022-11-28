package main.java.ethos.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportEntry.
 */
public class ReportEntry {

   

    /** The appt date. */
    private LocalDate apptDate;
    
    /** The patient id. */
    private int patientId;
    
    /** The patient F name. */
    private String patientFName;
    
    /** The patient L name. */
    private String patientLName;
    
    /** The nurse name. */
    private String nurseName;
    
    /** The dr name. */
    private String drName;
    
    /** The labs ordered. */
    private String labsOrdered;
    
    /** The is abnormal. */
    private String isAbnormal;
    
    /** The diagnosis. */
    private String diagnosis;
    
    
    /**
     * Instantiates a new report entry.
     *
     * @param apptDate the appt date
     * @param patientId the patient id
     * @param patientFName the patient F name
     * @param patientLName the patient L name
     * @param nurseName the nurse name
     * @param drName the dr name
     * @param diagnosis the diagnosis
     * @param labsOrdered the labs ordered
     * @param results the results
     */
    public ReportEntry(LocalDate apptDate, int patientId, String patientFName, String patientLName, String nurseName, String drName,
            String diagnosis, String labsOrdered, String results) {
        this.apptDate = apptDate;
        this.patientId = patientId;
        this.patientFName = patientFName;
        this.patientLName = patientFName;
        this.nurseName = nurseName;
        this.drName = drName;
        this.diagnosis = diagnosis;
        this.labsOrdered = labsOrdered;
        this.isAbnormal = this.parseResults(results);
    }
    
    /**
     * Gets the appt date.
     *
     * @return the appt date
     */
    public LocalDate getApptDate() {
        return apptDate;
    }

    /**
     * Gets the patient id.
     *
     * @return the patient id
     */
    public int getPatientId() {
        return patientId;
    }


    /**
     * Gets the patient F name.
     *
     * @return the patient F name
     */
    public String getPatientFName() {
        return patientFName;
    }
    
    /**
     * Gets the patient L name.
     *
     * @return the patient L name
     */
    public String getPatientLName() {
        return patientLName;
    }


    /**
     * Gets the nurse name.
     *
     * @return the nurse name
     */
    public String getNurseName() {
        return nurseName;
    }

    /**
     * Gets the dr name.
     *
     * @return the dr name
     */
    public String getDrName() {
        return drName;
    }


    /**
     * Gets the labs ordered.
     *
     * @return the labs ordered
     */
    public String getLabsOrdered() {
        return labsOrdered;
    }


    /**
     * Sets the labs ordered.
     *
     * @param labsOrdered the new labs ordered
     */
    public void setLabsOrdered(String labsOrdered) {
        
        this.labsOrdered = labsOrdered;
    }


    /**
     * Gets the checks if is abnormal.
     *
     * @return the checks if is abnormal
     */
    public String getIsAbnormal() {
        return this.isAbnormal;
    }


    /**
     * Parses the results.
     *
     * @param isAbnormal the is abnormal
     * @return the string
     */
    public String parseResults(String isAbnormal) {
        if (isAbnormal == null) {
            return "No Results";
        }
        String[] results = isAbnormal.split(",");
        String newResults = "";
        for (String current : results) {
            if (Integer.parseInt(current.strip()) == 1) {
                newResults += "Abnormal, ";
            }else {
                newResults += "Normal, ";
            }
        }
        return newResults;
    }


    /**
     * Gets the diagnosis.
     *
     * @return the diagnosis
     */
    public String getDiagnosis() {
        return diagnosis;
    }


}
