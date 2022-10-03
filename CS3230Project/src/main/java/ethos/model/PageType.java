package main.java.ethos.model;

public enum PageType {

    LOGIN("/view/codebehind/LoginPage.fxml"),
    MAIN("/view/codebehind/MainPage.fxml"),
    EDIT_INFO("/view/codebehind/PatientInfoPage.fxml");

    public final String label;

    private PageType(String label) {
        this.label = label;
    }
}