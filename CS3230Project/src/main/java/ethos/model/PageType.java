package main.java.ethos.model;

// TODO: Auto-generated Javadoc
/**
 * The Enum PageType.
 */
public enum PageType {
    
    /** The login. */
    LOGIN("/view/codebehind/LoginPage.fxml"),
    
    /** The main. */
    MAIN("/view/codebehind/MainPage.fxml"),
    
    /** The edit info. */
    EDIT_INFO("/view/codebehind/PatientInfoPage.fxml"),
    
    /** The visit. */
    VISIT("/view/codebehind/VisitPage.fxml"),
    
    /** The appt. */
    APPT("/view/codebehind/AppointmentPage.fxml"),
    
    /** The lab. */
    LAB("/view/codebehind/LabView.fxml"),
    
    /** The past visit. */
    PAST_VISIT("/view/codebehind/PriorVisitView.fxml"),
    
    /** The lab dialog. */
    LAB_DIALOG("/view/codebehind/LabOrderDialog.fxml"),

    ADMIN("/view/codebehind/AdminPage.fxml");

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