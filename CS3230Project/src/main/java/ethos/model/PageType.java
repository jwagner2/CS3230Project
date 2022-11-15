package main.java.ethos.model;

/**
 * The Enum PageType.
 */
public enum PageType {
    
    LOGIN("/view/codebehind/LoginPage.fxml"),
    MAIN("/view/codebehind/MainPage.fxml"),
    EDIT_INFO("/view/codebehind/PatientInfoPage.fxml"),
    VISIT("/view/codebehind/VisitPage.fxml"),
    APPT("/view/codebehind/AppointmentPage.fxml"),
    LAB("/view/codebehind/LabView.fxml"),
    PAST_VISIT("/view/codebehind/PriorVisitView.fxml"),
    LAB_DIALOG("/view/codebehind/LabOrderDialog.fxml");

    /** The label. */
    public final String label;

    /**
     * Instantiates a new page type.
     *
     * @param label the label
     */
    private PageType(String label) {
        this.label = label;
    }
}