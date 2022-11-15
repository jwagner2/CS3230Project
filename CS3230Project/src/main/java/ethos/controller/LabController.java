package main.java.ethos.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.ethos.dal.LabDal;
import main.java.ethos.model.LabTest;

// TODO: Auto-generated Javadoc
/**
 * The Class LabController.
 */
public class LabController {
    
    /** The current labs. */
    private List<LabTest> currentLabs;
    
    /** The visit id. */
    int visitId;

    /**
     * Instantiates a new lab controller.
     */
    public LabController() {
        this.currentLabs = new ArrayList<LabTest>();
    }
    
    /**
     * Gets the visit labs.
     *
     * @param visitId the visit id
     * @return the visit labs
     */
    public List<Map<String, Object>> getVisitLabs(int visitId) {
        this.visitId = visitId;
        this.currentLabs.clear();
        List<Map<String, Object>> labsForTable = new ArrayList<Map<String, Object>>();
        LabDal labDal = new LabDal();
        try {
            this.currentLabs.addAll(labDal.getLabsForVisit(visitId));
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        for (LabTest current : this.currentLabs) {
            Map<String, Object> labInfo = new HashMap<String, Object>();
            labInfo.put("testName", current.getTestName());
            labInfo.put("testResults", current.getResults());
            labInfo.put("isAbnormal", current.isAbnormal());
            labsForTable.add(labInfo);
        }
        return labsForTable;
    }
    
    /**
     * Enter test result.
     *
     * @param result the result
     * @param isAbnormal the is abnormal
     * @param name the name
     */
    public void enterTestResult(String result, boolean isAbnormal, String name) {
        LabDal labDal = new LabDal();
        LabTest labSelected = null;
        for (LabTest current: this.currentLabs) {
            if (current.getTestName().equals(name)) {
                labSelected = current;
            }
        }
        labSelected.setResults(result);
        labSelected.setIsAbnormal(isAbnormal);
        try {
            labDal.updateLab(result, isAbnormal, this.visitId, labSelected.getTestId());
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
    }
}
