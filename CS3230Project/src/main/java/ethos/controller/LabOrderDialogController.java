package main.java.ethos.controller;

import java.util.List;
import java.util.Map;

import main.java.ethos.model.LabTest;

public class LabOrderDialogController {
    
    private Map<Integer, Object> currentLabs;
    private List<Integer> labsToOrder;
    
    
    public Map<Integer, Object> getLabs(Map<Integer, Object> currentLabs) {
        this.currentLabs = currentLabs;
        return this.currentLabs;
    }
    
    

}
