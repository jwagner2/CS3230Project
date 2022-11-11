package main.java.ethos.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import main.java.ethos.dal.VisitDal;
import main.java.ethos.model.Visit;

public class VisitController {

    private List<Visit> searchResults;

    public boolean submitVisitInfo(Map<String, String> visitInfo) {
        VisitDal vDal = new VisitDal();
        
        Visit visit = this.createVisitFromInfo(visitInfo);
        try {
            vDal.enterVisitInfo(visit);
        } catch (SQLException e) {
            System.out.println("Error saving visit info --");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Visit createVisitFromInfo(Map<String, String> visitInfo) {
        int sysPressure = Integer.parseInt(visitInfo.get("systolic"));
        int diasPressure = Integer.parseInt(visitInfo.get("diastolic"));
        double weight = Double.parseDouble(visitInfo.get("weight"));
        double temp = Double.parseDouble(visitInfo.get("temperature"));
        int height = Integer.parseInt(visitInfo.get("height"));
        int pulse = Integer.parseInt(visitInfo.get("pulse"));
        String symptoms = visitInfo.get("symptoms");
        String diagnosis = visitInfo.get("diagnosis");
        LocalDateTime apptTime = LocalDateTime.parse(visitInfo.get("apptDatetime"));
        int nurseId = Integer.parseInt(visitInfo.get("nurseId"));
        int doctorId = Integer.parseInt(visitInfo.get("doctorId"));
        boolean isFinal = Boolean.parseBoolean(visitInfo.get("isFinal"));

        System.out.println("\nCreating new visit from data:" + "\n" + sysPressure + "\n" + diasPressure + "\n" + weight + "\n" + height + "\n" + temp + "\n" + pulse + "\n" + symptoms + "\n" + diagnosis + "\n" + height + "\n" + apptTime + "\n" + pulse);
        Visit visit = new Visit(sysPressure, diasPressure, weight, height, temp, pulse, symptoms, diagnosis, doctorId, nurseId, apptTime, isFinal);
        return visit;
    }

    public List<String> validateVisitFields(Map<String, String> fields) {
        List<String> invalidFields = new ArrayList<String>();
        if (fields.get("systolic") == null || fields.get("systolic").isEmpty()
            || !Pattern.matches("[0-9]{2,}", fields.get("systolic"))) {
            System.out.println("Bad systolic pressure input");
            invalidFields.add("systolic");
        }
        if (fields.get("diastolic") == null || fields.get("diastolic").isEmpty()
            || !Pattern.matches("[0-9]{2,}", fields.get("diastolic"))) {
            System.out.println("Bad diastolic pressure input");
            invalidFields.add("diastolic");
        }
        if (fields.get("weight") == null || fields.get("weight").isEmpty()
            || !Pattern.matches("[0-9]+\\.*[0-9]?", fields.get("weight"))) {
            System.out.println("Bad body weight input");
            invalidFields.add("weight");
        }
        if (fields.get("temperature") == null || fields.get("temperature").isEmpty()
            || !Pattern.matches("[0-9]{2,}\\.*[0-9]?", fields.get("temperature"))) {
            System.out.println("Bad body temp input");
            invalidFields.add("temperature");
        }
        if (fields.get("height") == null || fields.get("height").isEmpty()
            || !Pattern.matches("[0-9]+", fields.get("height"))) {
            System.out.println("Bad height input");
            invalidFields.add("height");
        }
        if (fields.get("pulse") == null || fields.get("pulse").isEmpty()
            || !Pattern.matches("[0-9]{2,}", fields.get("pulse"))) {
            System.out.println("Bad pulse input");
            invalidFields.add("pulse");
        }       

        return invalidFields;
    }

    public List<Map<String, Object>> getPatientVisits(int selectedPatientId) {
        this.searchResults = new ArrayList<Visit>();
        VisitDal visitDal = new VisitDal();
        try {
            this.searchResults = visitDal.getVisitsForPatient(selectedPatientId);
            System.out.println("Visits: " + this.searchResults.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.buildResultsForPastVisitsTable();
    }


    /**
     * Builds the results for table.
     *
     * @return the list
     */
    public List<Map<String, Object>> buildResultsForPastVisitsTable() {
        if (this.searchResults == null) {
            return null;
        }
        List<Map<String, Object>> visits = new ArrayList<Map<String, Object>>();
        
        for (Visit currVisit : this.searchResults) {
            Map<String, Object> visitInfo = new HashMap<String, Object>();
            String doctorName = "";
            try {
                doctorName = new VisitDal().getDoctorForVisit(this.searchResults.get(0).getDoctorId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            visitInfo.put("doctor", doctorName);
            visitInfo.put("date", currVisit.getApptDateTime().toLocalDate());
            visitInfo.put("time", currVisit.getApptDateTime().toLocalTime());
            visitInfo.put("doctorId", currVisit.getDoctorId());
            visits.add(visitInfo);
        }

        return visits;
    }

    public Map<String, String> getVisitInfo(int doctorId, LocalDateTime apptDatetime) {
        VisitDal visitDal = new VisitDal();
        Map<String, String> visitInfo = null;
        try {
            Visit visit = visitDal.getVisitByDoctorAndDatetime(doctorId, apptDatetime);
            visitInfo = new HashMap<String, String>();
            visitInfo.put("systolic", String.valueOf(visit.getSystolicPressure()));
            visitInfo.put("diastolic", String.valueOf(visit.getDiastolicPressure()));
            visitInfo.put("weight", String.valueOf(visit.getBodyWeightLbs()));
            visitInfo.put("temperature", String.valueOf(visit.getBodyTempDegreesF()));
            visitInfo.put("height", String.valueOf(visit.getHeightInches()));
            visitInfo.put("pulse", String.valueOf(visit.getPulseBpm()));
            visitInfo.put("symptoms", visit.getSymptoms());
            visitInfo.put("diagnosis", visit.getDiagnosis());
            visitInfo.put("doctorId", String.valueOf(visit.getDoctorId()));
            visitInfo.put("apptDatetime", visit.getApptDateTime().toString());
            visitInfo.put("isFinal", String.valueOf(visit.isFinal()));
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return visitInfo;
    }

    public void updateDiagnosis(int doctorId, LocalDateTime apptDatetime, String diagnosis, boolean isFinal) {
        VisitDal vDal = new VisitDal();

        try {
            vDal.updateDiagnosis(doctorId, apptDatetime, diagnosis, isFinal);
        } catch (SQLException e) {
            System.out.println("Error updating diagnosis --");
            e.printStackTrace();
        }
    }
}