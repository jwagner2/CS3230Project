package main.java.ethos.model;

public class LabTest {
    private String testName;
    private int testId;

    public LabTest(int testId, String testName) {
        this.testId = testId;
        this.testName = testName;
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

}
