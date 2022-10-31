package main.java.ethos.model;

import java.util.List;

public class Visit {
    private int systolicPressure;
    private int diastolicPressure;
    private double bodyWeightLbs;
    private double bodyTempDegreesF;
    private int pulseBpm;
    private String symptoms;
    private String diagnosis;
    private List<LabTest> labsOrdered;


    public Visit(int systolicPressure, int diastolicPressure, double bodyWeight, double bodyTemp, int pulseBpm,
            String symptoms, String diagnosis, List<LabTest> labsOrdered) {
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.bodyWeightLbs = bodyWeight;
        this.bodyTempDegreesF = bodyTemp;
        this.pulseBpm = pulseBpm;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.labsOrdered = labsOrdered;
    }
    //Need to add diagnosis to visit view


}