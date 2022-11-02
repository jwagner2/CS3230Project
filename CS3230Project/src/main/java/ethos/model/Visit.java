package main.java.ethos.model;

import java.time.LocalDateTime;
import java.util.List;

public class Visit {

    private int doctorId;
    private LocalDateTime apptDateTime;
    private int nurseId;
    private int systolicPressure;
    private int diastolicPressure;
    private double bodyTempDegreesF;
    private int heightInches;
    private double bodyWeightLbs;
    private int pulseBpm;
    private String symptoms;
    private String diagnosis;
    //private List<LabTest> labsOrdered;

    public Visit(int systolicPressure, int diastolicPressure, double bodyWeight, int heightInches, double bodyTemp, int pulseBpm,
            String symptoms, String diagnosis, int doctorId, int nurseId, LocalDateTime apptTime) {
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
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public double getBodyWeightLbs() {
        return bodyWeightLbs;
    }

    public void setBodyWeightLbs(double bodyWeightLbs) {
        this.bodyWeightLbs = bodyWeightLbs;
    }

    public double getBodyTempDegreesF() {
        return bodyTempDegreesF;
    }

    public void setBodyTempDegreesF(double bodyTempDegreesF) {
        this.bodyTempDegreesF = bodyTempDegreesF;
    }

    public int getPulseBpm() {
        return pulseBpm;
    }

    public void setPulseBpm(int pulseBpm) {
        this.pulseBpm = pulseBpm;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    // public List<LabTest> getLabsOrdered() {
    //     return labsOrdered;
    // }

    // public void setLabsOrdered(List<LabTest> labsOrdered) {
    //     this.labsOrdered = labsOrdered;
    // }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getNurseId() {
        return nurseId;
    }

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    public LocalDateTime getApptDateTime() {
        return apptDateTime;
    }

    public void setApptDateTime(LocalDateTime apptDateTime) {
        this.apptDateTime = apptDateTime;
    }

    public int getHeightInches() {
        return heightInches;
    }

    public void setHeightInches(int heightInches) {
        this.heightInches = heightInches;
    }
}
