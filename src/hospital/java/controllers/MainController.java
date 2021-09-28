package hospital.java.controllers;

import hospital.java.models.Patient;
import hospital.java.repositories.patient_repository.PatientRepository;
import hospital.java.sources.Datasource;
import hospital.java.sources.DropdownData;
import hospital.java.sources.PageSource;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class MainController {

    PatientRepository patientRepository = new PatientRepository();
    Node formLoaderLoad, summaryLoad;
    Node allPatientsLoaderLoad;
    @FXML
    private BorderPane navPane;
    @FXML
    private MenuItem editPatientMI, deletePatientMI;
    private FXMLLoader formLoader;
    private FXMLLoader allPatientsLoader, summaryLoader;
    private AllPatientsController allPatientsController;
    private SummaryController summaryController;
    private FormController formController;

    public void initialize() {
        editPatientMI.setDisable(true);
        deletePatientMI.setDisable(true);
        AllPatientsController.listPatients();
        PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
        delay.setOnFinished(event -> {
            try {
                formLoader = new FXMLLoader(
                        MainController.class.getResource("/hospital/resources/views/form.fxml"));
                allPatientsLoader = new FXMLLoader(
                        MainController.class.getResource("/hospital/resources/views/allPatients.fxml"));
                summaryLoader = new FXMLLoader(
                        MainController.class.getResource("/hospital/resources/views/summary.fxml"));
                formLoaderLoad = formLoader.load();
                allPatientsLoaderLoad = allPatientsLoader.load();
                summaryLoad = summaryLoader.load();
                summaryController = summaryLoader.getController();
                allPatientsController = allPatientsLoader.getController();
                formController = formLoader.getController();
                navPane.setCenter(formLoaderLoad);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }

    @FXML
    private void logout() {
        try {
            Stage loginStage = (Stage) navPane.getScene().getWindow();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(
                    LoginController.class.getResource("/hospital/resources/views/login.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root, 500, 600));
            primaryStage.getIcons().add(new Image(Objects
                    .requireNonNull(LoginController.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

            Datasource.instance.isConnected = false;
            PageSource.form.reset();
            PageSource.editForm.reset();

            loginStage.close();
            primaryStage.show();
            Datasource.getPatients().clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleShowView(ActionEvent e) {
        String view = (String) ((MenuItem) e.getSource()).getUserData();
        System.out.println(e.getSource());
        System.out.println(view);
        if (view.equals("/hospital/resources/views/form.fxml")) {
            editPatientMI.setDisable(true);
            deletePatientMI.setDisable(true);
            formController.clearAll();
            navPane.setCenter(formLoaderLoad);
        } else if (view.equals("/hospital/resources/views/summary.fxml")) {
            editPatientMI.setDisable(true);
            deletePatientMI.setDisable(true);
            navPane.setCenter(summaryLoad);
        } else {
            editPatientMI.setDisable(false);
            deletePatientMI.setDisable(false);
            navPane.setCenter(allPatientsLoaderLoad);
        }
    }

    @FXML
    private void showDoctors() {

        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(navPane.getScene().getWindow());
            dialog.setTitle("Doctors");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainController.class.getResource("/hospital/resources/views/doctors.fxml"));

            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(
                    PatientRepository.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void editPatient() {
        try {
            patientRepository.editPatient(navPane.getScene().getWindow(),
                    MainController.class.getResource("/hospital/resources/views/form.fxml"),
                    allPatientsController.getPatientList(), allPatientsController.getPatientDetails(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void deletePatient() {
        patientRepository.deletePatient(allPatientsController.getPatientList(), null);
    }

    @FXML
    private void getAsCSVAll() {
        try {

            ArrayList<ArrayList<String>> allDetails = new ArrayList<>();
            ArrayList<String> keys = new ArrayList<>();

            keys.addAll(DropdownData.titles);
            keys.addAll(DropdownData.coronaryAngiographyList);
            keys.addAll(DropdownData.pciList);

            for (int i = 0; i < Datasource.getPatients().size(); i++) {
                Patient patient = Datasource.getPatients().get(i);
                allDetails.add(patient.getValues());
                allDetails.get(i).addAll(Datasource.getCags().get(patient.getId()).getValues(false));
                allDetails.get(i).addAll(Datasource.getPcis().get(patient.getId()).getValues(false));

                System.out.println(allDetails.get(i).get(0));
                System.out.println(allDetails.size());
            }


            patientRepository.getCSV(allDetails, PatientRepository.toStringArray(keys), "Patients.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void save() {
        formController.save();
    }

    @FXML
    private void refresh() {
        try {

            summaryController.refresh();

            Datasource.getPatients().clear();
            Datasource.getCags().clear();
            Datasource.getPcis().clear();
            Datasource.instance.queryPatients();
            allPatientsController.getPatientList().getSelectionModel().selectFirst();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void exit() {
        Platform.exit();
        System.exit(0);
    }

}

// TODO: create trigger if not exists