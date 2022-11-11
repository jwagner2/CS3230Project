package main.java.ethos.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.ethos.model.PageType;
import main.java.ethos.view.ApptView;
import main.java.ethos.view.LoginView;
import main.java.ethos.view.MainView;
import main.java.ethos.view.PatientInfoView;
import main.java.ethos.view.PriorVisitView;
import main.java.ethos.view.VisitView;

public class SceneController {
    

    /**
     * Changes the current view to the main screen.
     *
     * @param currentStage - the current stage for the application
     */
    public void changeToMainView(Stage currentStage,  ControllerManager manager) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PageType.class.getResource(PageType.MAIN.label));
            Parent parent = loader.load();

            MainView mainView = loader.<MainView>getController();
            mainView.initialize(manager);

            Scene scene = new Scene(parent);
            currentStage.setTitle("ethos");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (MalformedURLException murlerr) {
            System.err.println("Bad FXML URL");
            murlerr.printStackTrace();
        } catch (IOException ioerr) {
            System.err.println("Bad file");
            ioerr.printStackTrace();
        }

    }

    /**
     * Change to login.
     *
     * @param currentStage the current stage
     */
    public void changeToLogin(Stage currentStage, ControllerManager manager) {
   
        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PageType.class.getResource(PageType.LOGIN.label));
            Parent parent = loader.load();

            LoginView LoginView = loader.<LoginView>getController();
            LoginView.initialize(manager);

            Scene scene = new Scene(parent);
            currentStage.setTitle("ethos");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (MalformedURLException murlerr) {
            System.err.println("Bad FXML URL");
            murlerr.printStackTrace();
        } catch (IOException ioerr) {
            System.err.println("Bad file");
            ioerr.printStackTrace();
        }

    }
    
    /**
     * Changes the current view to the appointment screen.
     *
     * @param currentStage - the current stage for the application
     */
    public void changeToApptView(Stage currentStage, ControllerManager manager) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PageType.class.getResource(PageType.APPT.label));
            Parent parent = loader.load();

            ApptView apptView = loader.<ApptView>getController();
            apptView.initialize(manager);

            Scene scene = new Scene(parent);
            currentStage.setTitle("ethos -- Scheduled Appointments");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (MalformedURLException murlerr) {
            System.err.println("Bad FXML URL");
            murlerr.printStackTrace();
        } catch (IOException ioerr) {
            System.err.println("Bad file");
            ioerr.printStackTrace();
        }

    }
    
    /**
     * Changes the current view to the appointment screen.
     *
     * @param currentStage - the current stage for the application
     */
    public void changeToVisitView(Stage currentStage, ControllerManager manager, int doctorId, LocalDateTime apptDateTime) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PageType.class.getResource(PageType.VISIT.label));
            Parent parent = loader.load();

            VisitView visitView = loader.<VisitView>getController();
            visitView.initialize(manager, doctorId, apptDateTime);
            
            Scene scene = new Scene(parent);
            currentStage.setTitle("ethos -- Visit");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (MalformedURLException murlerr) {
            System.err.println("Bad FXML URL");
            murlerr.printStackTrace();
        } catch (IOException ioerr) {
            System.err.println("Bad file");
            ioerr.printStackTrace();
        }

    }

    /**
     * Changes the view to the patient info view.
     *
     * @param currentStage - the current stage for the application
     */
    public void changeToPatientInfoView(Stage currentStage,  ControllerManager manager) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PageType.class.getResource(PageType.EDIT_INFO.label));
            Parent parent = loader.load();

            PatientInfoView infoView = loader.<PatientInfoView>getController();
            infoView.initialize(manager);

            Scene scene = new Scene(parent);
            currentStage.setTitle("ethos -- Patient Info");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (MalformedURLException murlerr) {
            System.err.println("Bad FXML URL");
            murlerr.printStackTrace();
        } catch (IOException ioerr) {
            System.err.println("Bad file");
            ioerr.printStackTrace();
        }

    }

    public void changeToPastVisitsView(Stage currentStage, ControllerManager manager) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PageType.class.getResource(PageType.PAST_VISIT.label));
            Parent parent = loader.load();

            PriorVisitView priorVisitView = loader.<PriorVisitView>getController();
            priorVisitView.initialize(manager);

            Scene scene = new Scene(parent);
            currentStage.setTitle("ethos -- Prior Visit History");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (MalformedURLException murlerr) {
            System.err.println("Bad FXML URL");
            murlerr.printStackTrace();
        } catch (IOException ioerr) {
            System.err.println("Bad file");
            ioerr.printStackTrace();
        }
    }

}
