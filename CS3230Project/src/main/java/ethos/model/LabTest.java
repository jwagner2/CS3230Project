package main.java.ethos.model;

public class LabTest {
    private String testName;
    private int testId;
    private String testDescription;
    private String results;
    private boolean isAbnormal;
    

    public LabTest(int testId, String testName, String testDescription) {
        this.testId = testId;
        this.testName = testName;
        this.testDescription = testDescription;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }
    
    public String getDescription() {
        return this.testDescription;
    }
    
    public String getResults() {
        if (this.results == null) {
            return "";
        }
        return this.results;
    }
    
    public void setResults(String results) {
        this.results = results;
    }
    
    public boolean isAbnormal() {
        return this.isAbnormal;
    }
    
    public void setIsAbnormal(boolean isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

}
