package main.java.ethos.model;

import java.time.LocalDateTime;

// TODO: Auto-generated Javadoc
/**
 * The Class Visit.
 */
public class Visit {

    /** The doctor id. */
    private int doctorId;
    
    /** The visit id. */
    private int visitId;
    
    /** The appt date time. */
    private LocalDateTime apptDateTime;
    
    /** The nurse id. */
    private int nurseId;
    
    /** The systolic pressure. */
    private int systolicPressure;
    
    /** The diastolic pressure. */
    private int diastolicPressure;
    
    /** The body temp degrees F. */
    private double bodyTempDegreesF;
    
    /** The height inches. */
    private int heightInches;
    
    /** The body weight lbs. */
    private double bodyWeightLbs;
    
    /** The pulse bpm. */
    private int pulseBpm;
    
    /** The symptoms. */
    private String symptoms;
    
    /** The diagnosis. */
    private String diagnosis;
    
    /** The is final. */
    private boolean isFinal;
    //private List<LabTest> labsOrdered;

    /**
     * Instantiates a new visit.
     *
     * @param systolicPressure the systolic pressure
     * @param diastolicPressure the diastolic pressure
     * @param bodyWeight the body weight
     * @param heightInches the height inches
     * @param bodyTemp the body temp
     * @param pulseBpm the pulse bpm
     * @param symptoms the symptoms
     * @param diagnosis the diagnosis
     * @param doctorId the doctor id
     * @param nurseId the nurse id
     * @param apptTime the appt time
     * @param isFinal the is final
     */
    public Visit(int systolicPressure, int diastolicPressure, double bodyWeight, int heightInches, double bodyTemp, int pulseBpm,
            String symptoms, String diagnosis, int doctorId, int nurseId, LocalDateTime apptTime, boolean isFinal) {
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.bodyWeightLbs = bodyWeight;
        this.heightInches = heightInches;
        this.bodyTempDegreesF = bodyTemp;
        this.pulseBpm = pulseBpm;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        //this.labsOrdered = labsOrdered;
        this.doctorId = doctorId;
        this.nurseId = nurseId;
        this.apptDateTime = apptTime;
        this.isFinal = isFinal;
    }

    /**
     * Gets the systolic pressure.
     *
     * @return the systolic pressure
     */
    public int getSystolicPressure() {
        return systolicPressure;
    }

    /**
     * Sets the systolic pressure.
     *
     * @param systolicPressure the new systolic pressure
     */
    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    /**
     * Gets the diastolic pressure.
     *
     * @return the diastolic pressure
     */
    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    /**
     * Sets the diastolic pressure.
     *
     * @param diastolicPressure the new diastolic pressure
     */
    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    /**
     * Gets the body weight lbs.
     *
     * @return the body weight lbs
     */
    public double getBodyWeightLbs() {
        System.out.println(this.bodyWeightLbs);
        return bodyWeightLbs;
    }

    /**
     * Sets the body weight lbs.
     *
     * @param bodyWeightLbs the new body weight lbs
     */
    public void setBodyWeightLbs(double bodyWeightLbs) {
        this.bodyWeightLbs = bodyWeightLbs;
    }

    /**
     * Gets the body temp degrees F.
     *
     * @return the body temp degrees F
     */
    public double getBodyTempDegreesF() {
        return this.bodyTempDegreesF;
    }

    /**
     * Sets the body temp degrees F.
     *
     * @param bodyTempDegreesF the new body temp degrees F
     */
    public void setBodyTempDegreesF(double bodyTempDegreesF) {
        this.bodyTempDegreesF = bodyTempDegreesF;
    }

    /**
     * Gets the pulse bpm.
     *
     * @return the pulse bpm
     */
    public int getPulseBpm() {
        System.out.println(this.pulseBpm);
        return this.pulseBpm;
    }

    /**
     * Sets the pulse bpm.
     *
     * @param pulseBpm the new pulse bpm
     */
    public void setPulseBpm(int pulseBpm) {
        this.pulseBpm = pulseBpm;
    }

    /**
     * Gets the symptoms.
     *
     * @return the symptoms
     */
    public String getSymptoms() {
        return symptoms;
    }

    /**
     * Sets the symptoms.
     *
     * @param symptoms the new symptoms
     */
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    /**
     * Gets the diagnosis.
     *
     * @return the diagnosis
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Sets the diagnosis.
     *
     * @param diagnosis the new diagnosis
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    // public List<LabTest> getLabsOrdered() {
    //     return labsOrdered;
    // }

    // public void setLabsOrdered(List<LabTest> labsOrdered) {
    //     this.labsOrdered = labsOrdered;
    // }

    /**
     * Gets the doctor id.
     *
     * @return the doctor id
     */
    public int getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the doctor id.
     *
     * @param doctorId the new doctor id
     */
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Gets the nurse id.
     *
     * @return the nurse id
     */
    public int getNurseId() {
        return nurseId;
    }

    /**
     * Sets the nurse id.
     *
     * @param nurseId the new nurse id
     */
    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    /**
     * Gets the appt date time.
     *
     * @return the appt date time
     */
    public LocalDateTime getApptDateTime() {
        return apptDateTime;
    }

    /**
     * Sets the appt date time.
     *
     * @param apptDateTime the new appt date time
     */
    public void setApptDateTime(LocalDateTime apptDateTime) {
        this.apptDateTime = apptDateTime;
    }

    /**
     * Gets the height inches.
     *
     * @return the height inches
     */
    public int getHeightInches() {
        return heightInches;
    }

    /**
     * Sets the height inches.
     *
     * @param heightInches the new height inches
     */
    public void setHeightInches(int heightInches) {
        this.heightInches = heightInches;
    }
    
    /**
     * Sets the visit id.
     *
     * @param visitId the new visit id
     */
    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }
    
    /**
     * Gets the visit id.
     *
     * @return the visit id
     */
    public int getVisitId() {
        return this.visitId;
    }

    /**
     * Checks if is final.
     *
     * @return true, if is final
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Sets the final.
     *
     * @param isFinal the new final
     */
    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    
}
