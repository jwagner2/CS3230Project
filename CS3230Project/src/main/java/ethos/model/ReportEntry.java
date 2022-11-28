package main.java.ethos.model;

import java.time.LocalDateTime;
import java.util.List;

public class ReportEntry {

   

    private LocalDateTime apptDate;
    private int patientId;
    private String patientName;
    private String nurseName;
    private String drName;
    private String labsOrdered;
    private String isAbnormal;
    private String diagnosis;
    
    
    public ReportEntry(LocalDateTime apptDate, int patientId, String patientName, String nurseName, String drName,
            String diagnosis, String labsOrdered, String results) {
        this.apptDate = apptDate;
        this.patientId = patientId;
        this.patientName = patientName;
        this.nurseName = nurseName;
        this.drName = drName;
        this.diagnosis = diagnosis;
        this.labsOrdered = labsOrdered;
        this.isAbnormal = this.parseResults(results);
    }
    public LocalDateTime getApptDate() {
        return apptDate;
    }

    public int getPatientId() {
        return patientId;
    }


    public String getPatientName() {
        return patientName;
    }


    public String getNurseName() {
        return nurseName;
    }

    public String getDrName() {
        return drName;
    }


    public String getLabsOrdered() {
        return labsOrdered;
    }


    public void setLabsOrdered(String labsOrdered) {
        
        this.labsOrdered = labsOrdered;
    }


    public String getIsAbnormal() {
        return this.isAbnormal;
    }


    public String parseResults(String isAbnormal) {
        String[] results = isAbnormal.split(",");
        String newResults = "";
        for (String current : results) {
            if (Integer.parseInt(current) == 1) {
                newResults += "Abnormal, ";
            }else {
                newResults += "Normal, ";
            }
        }
        return newResults;
    }


    public String getDiagnosis() {
        return diagnosis;
    }


}
