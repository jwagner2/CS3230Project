package main.java.ethos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import main.java.ethos.dal.VisitDal;
import main.java.ethos.model.Visit;

public class VisitController {

    /**
     * Checks to ensure that the data provided as patient fields is valid.
     * 
     * @param fields - the fields to check
     * @return true if the data in the fields are valid; false otherwise.
     */
    public List<String> validateFields(Map<String, String> fields) {
        List<String> invalidFields = new ArrayList<String>();
        if ((fields.get("systolic") == null || fields.get("systolic").isBlank())
                || Integer.parseInt(fields.get("systolic")) > 300) {
            System.err.println("Bad systolic input");
            invalidFields.add("systolic");
        }
        if ((fields.get("diastolic") == null || fields.get("diastolic").isBlank()
                || (Integer.parseInt(fields.get("diastolic")) > 300))) {
            System.err.println("Bad diastolic input");
            invalidFields.add("diastolic");
        }
        if (fields.get("weight") == null || fields.get("weight").isBlank()
                || Double.parseDouble(fields.get("weight")) > 1400) {
            System.err.println("Bad weight input");
            invalidFields.add("weight");
        }
        if (fields.get("temperature") == null || fields.get("temperature").isBlank()
                || Double.parseDouble(fields.get("temperature")) > 120) {
            System.err.println("Bad temperature input");
            invalidFields.add("temperature");
        }
        if (fields.get("pulse") == null || fields.get("pulse").isBlank()
                || Integer.parseInt(fields.get("pulse")) > 500) {
            System.err.println("Bad pulse input");
            invalidFields.add("pulse");
        }
        if (fields.get("height") == null || fields.get("height").isBlank()
                || Integer.parseInt(fields.get("height")) > 90) {
            System.err.println("Bad height input");
            invalidFields.add("height");
        }

        return invalidFields;
    }

    public boolean endVisit(Map<String, String> visitDetails) {
        VisitDal visitDal = new VisitDal();
        Visit visit = new Visit(Integer.parseInt(visitDetails.get("systolic")), Integer.parseInt(visitDetails.get("diastolic"), 0, 0, 0, 0, null, null, null, 0, null, 0)
        visitDal.enterVisitInfo(null);
        return false;
    }

}
