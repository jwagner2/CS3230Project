package main.java.ethos.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import main.java.ethos.dal.LabDal;
import main.java.ethos.dal.VisitDal;
import main.java.ethos.model.LabTest;
import main.java.ethos.model.Visit;

// TODO: Auto-generated Javadoc
/**
 * The Class VisitController.
 */
public class VisitController {

    /** The search results. */
    private List<Visit> searchResults;
    
    /** The current labs. */
    private List<LabTest> currentLabs;
    
    /** The tests to order. */
    private List<LabTest> testsToOrder;
    
    /** The current visit doctor id. */
    private int currentVisitDoctorId;
    
    /** The current visit appt datetime. */
    LocalDateTime currentVisitApptDatetime;
    
    /** The visit id. */
    int visitId;

    /**
     * Submit visit info.
     *
     * @param visitInfo the visit info
     * @return true, if successful
     */
    public boolean submitVisitInfo(Map<String, String> visitInfo) {
        VisitDal vDal = new VisitDal();
        LabDal labDal = new LabDal();
        Visit visit = this.createVisitFromInfo(visitInfo);
        try {
            vDal.enterVisitInfo(visit);
            int visitId = vDal.getLastVisitId();
            labDal.orderLabs(this.testsToOrder, visitId);

        } catch (SQLException e) {
            System.out.println("Error saving visit info --");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Creates the visit from info.
     *
     * @param visitInfo the visit info
     * @return the visit
     */
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
        Visit visit = new Visit(sysPressure, diasPressure, weight, height, temp, pulse, symptoms, diagnosis, doctorId,
                nurseId, apptTime, isFinal);
        return visit;
    }

    /**
     * Validate visit fields.
     *
     * @param fields the fields
     * @return the list
     */
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

    /**
     * Gets the patient visits.
     *
     * @param selectedPatientId the selected patient id
     * @return the patient visits
     */
    public List<Map<String, Object>> getPatientVisits(int selectedPatientId) {
        this.searchResults = new ArrayList<Visit>();
        VisitDal visitDal = new VisitDal();
        try {
            this.searchResults = visitDal.getVisitsForPatient(selectedPatientId);

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
                doctorName = new VisitDal().getDoctorForVisit(currVisit.getDoctorId());
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

    /**
     * Gets the visit info.
     *
     * @param doctorId the doctor id
     * @param apptDatetime the appt datetime
     * @return the visit info
     */
    public Map<String, String> getVisitInfo(int doctorId, LocalDateTime apptDatetime) {
        this.currentVisitDoctorId = doctorId;
        this.currentVisitApptDatetime = apptDatetime;
        VisitDal visitDal = new VisitDal();
        Map<String, String> visitInfo = null;
        try {
            Visit visit = visitDal.getVisitByDoctorAndDatetime(doctorId, apptDatetime);
            if (visit != null) {
                visitInfo = new HashMap<String, String>();
                visitInfo.put("systolic", String.valueOf(visit.getSystolicPressure()));
                visitInfo.put("diastolic", String.valueOf(visit.getDiastolicPressure()));
                visitInfo.put("weight", Double.toString(visit.getBodyWeightLbs()));
                System.out.println("visit Con " + Double.toString(visit.getBodyWeightLbs()));
                visitInfo.put("temperature", String.valueOf(visit.getBodyTempDegreesF()));
                visitInfo.put("height", String.valueOf(visit.getHeightInches()));
                visitInfo.put("pulse", Integer.toString(visit.getPulseBpm()));
                visitInfo.put("symptoms", visit.getSymptoms());
                visitInfo.put("diagnosis", visit.getDiagnosis());
                visitInfo.put("doctorId", String.valueOf(visit.getDoctorId()));
                visitInfo.put("apptDatetime", visit.getApptDateTime().toString());
                visitInfo.put("isFinal", String.valueOf(visit.isFinal()));
                this.visitId = visit.getVisitId();
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        this.setLabsAvailable();
        return visitInfo;
    }

    /**
     * Gets the current dr.
     *
     * @return the current dr
     */
    public int getCurrentDr() {
        return this.currentVisitDoctorId;
    }

    /**
     * Gets the current date time.
     *
     * @return the current date time
     */
    public LocalDateTime getCurrentDateTime() {
        return this.currentVisitApptDatetime;
    }

    /**
     * Update diagnosis.
     *
     * @param doctorId the doctor id
     * @param apptDatetime the appt datetime
     * @param diagnosis the diagnosis
     * @param isFinal the is final
     */
    public void updateDiagnosis(int doctorId, LocalDateTime apptDatetime, String diagnosis, boolean isFinal) {
        VisitDal vDal = new VisitDal();

        try {
            vDal.updateDiagnosis(doctorId, apptDatetime, diagnosis, isFinal);
        } catch (SQLException e) {
            System.out.println("Error updating diagnosis --");
            e.printStackTrace();
        }
    }

    /**
     * Sets the labs available.
     */
    private void setLabsAvailable() {
        LabDal labDal = new LabDal();
        try {
            this.currentLabs = labDal.getCurrentLabs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the current id.
     *
     * @return the current id
     */
    public int getCurrentId() {
        return this.visitId;
    }

    /**
     * Gets the current labs.
     *
     * @return the current labs
     */
    public List<Map<String, Object>> getCurrentLabs() {
        List<Map<String, Object>> currentLabs = new ArrayList<Map<String, Object>>();
        for (LabTest current : this.currentLabs) {
            Map<String, Object> lab = new HashMap<String, Object>();
            lab.put("testId", current.getTestId());
            lab.put("testName", current.getTestName());
            lab.put("testDescription", current.getDescription());
            currentLabs.add(lab);
        }
        return currentLabs;
    }

    /**
     * Sets the lab order.
     *
     * @param selectedItems the new lab order
     */
    public void setLabOrder(ObservableList<Map> selectedItems) {
        this.testsToOrder = new ArrayList<LabTest>();
        for (Map<String, Object> currentLabInfo : selectedItems) {
            int labId = (int) currentLabInfo.get("testId");
            String name = (String) currentLabInfo.get("testName");
            String description = (String) currentLabInfo.get("testDescription");
            LabTest currentLab = new LabTest(labId, name, description);
            this.testsToOrder.add(currentLab);
        }

    }

    /**
     * Gets the current order.
     *
     * @return the current order
     */
    public List<LabTest> getCurrentOrder() {
        return this.testsToOrder;
    }

    /**
     * Clear lab order.
     */
    public void clearLabOrder() {
        if (this.testsToOrder != null) {
            this.testsToOrder.clear();
        }

    }

}
