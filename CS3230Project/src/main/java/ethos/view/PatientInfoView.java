package main.java.ethos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.ethos.controller.ControllerManager;
import main.java.ethos.model.PageType;

public class PatientInfoView {

    private ControllerManager manager = new ControllerManager();

    @FXML
    private TextField patientIdField;

    @FXML
    private TextField patientFnameField;

    @FXML
    private TextField patientLnameField;

    @FXML
    private TextField patientSsnField;

    @FXML
    private DatePicker patientDobPicker;

    @FXML
    private TextField patientPhoneField;

    @FXML
    private TextField patientAddr1Field;

    @FXML
    private TextField patientAddr2Field;

    @FXML
    private TextField patientAddrZipcodeField;

    @FXML
    private ComboBox<String> patientAddrStateComboBox;

    @FXML
    private CheckBox patientActiveChBox;

    @FXML
    private ComboBox<String> patientGenderComboBox;

    @FXML
    private Button submitButton;

    @FXML
    void handleSubmit(ActionEvent event) {
        this.manager.changeView(PageType.MAIN, (Stage) this.submitButton.getScene().getWindow());
    }
}
