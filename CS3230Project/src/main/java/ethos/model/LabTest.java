package main.java.ethos.model;

// TODO: Auto-generated Javadoc
/**
 * The Class LabTest.
 */
public class LabTest {
    
    /** The test name. */
    private String testName;
    
    /** The test id. */
    private int testId;
    
    /** The test description. */
    private String testDescription;
    
    /** The results. */
    private String results;
    
    /** The is abnormal. */
    private boolean isAbnormal;
    

    /**
     * Instantiates a new lab test.
     *
     * @param testId the test id
     * @param testName the test name
     * @param testDescription the test description
     */
    public LabTest(int testId, String testName, String testDescription) {
        this.testId = testId;
        this.testName = testName;
        this.testDescription = testDescription;
    }

    /**
     * Gets the test name.
     *
     * @return the test name
     */
    public String getTestName() {
        return testName;
    }

    /**
     * Sets the test name.
     *
     * @param testName the new test name
     */
    public void setTestName(String testName) {
        this.testName = testName;
    }

    /**
     * Gets the test id.
     *
     * @return the test id
     */
    public int getTestId() {
        return testId;
    }

    /**
     * Sets the test id.
     *
     * @param testId the new test id
     */
    public void setTestId(int testId) {
        this.testId = testId;
    }
    
    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return this.testDescription;
    }
    
    /**
     * Gets the results.
     *
     * @return the results
     */
    public String getResults() {
        if (this.results == null) {
            return "";
        }
        return this.results;
    }
    
    /**
     * Sets the results.
     *
     * @param results the new results
     */
    public void setResults(String results) {
        this.results = results;
    }
    
    /**
     * Checks if is abnormal.
     *
     * @return true, if is abnormal
     */
    public boolean isAbnormal() {
        return this.isAbnormal;
    }
    
    /**
     * Sets the checks if is abnormal.
     *
     * @param isAbnormal the new checks if is abnormal
     */
    public void setIsAbnormal(boolean isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

}
