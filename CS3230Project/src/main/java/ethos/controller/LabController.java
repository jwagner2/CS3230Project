package main.java.ethos.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.ethos.dal.LabDal;
import main.java.ethos.model.LabTest;

public class LabController {
    
    private List<LabTest> currentLabs;
    
    public List<Map<String, Object>> getVisitLabs(int visitId) {
        List<Map<String, Object>> labsForTable = new ArrayList<Map<String, Object>>();
        LabDal labDal = new LabDal();
        try {
            this.currentLabs.addAll(labDal.getLabsForVisit(visitId));
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        for (LabTest current: this.currentLabs) {
            Map<String, Object> labInfo = new HashMap<String, Object>();
            labInfo.put("testName", current.getTestName());
            labInfo.put("testResults", current.getResults());
            labInfo.put("isAbnormal", current.isAbnormal());
            labsForTable.add(labInfo);
        }
        return labsForTable;
        
    }
}
