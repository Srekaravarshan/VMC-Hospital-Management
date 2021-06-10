package hospital.java.controllers;

import hospital.java.repositories.patient_repository.PatientRepository;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private BorderPane navPane;

    PatientRepository patientRepository = new PatientRepository();

    public void initialize() {
        AllPatientsController.listPatients();
        PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
        delay.setOnFinished(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/hospital/resources/views/form.fxml"));
                navPane.setCenter(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }

//    @FXML
//    private void logout() {
//        Auth.logOut();
//        Stage mainStage = (Stage) navPane.getScene().getWindow();
//        Stage primaryStage = new Stage();
////            URL fxmlLocation = getClass().getResource("resources/views/register.fxml");
////            System.out.println(fxmlLocation.getPath());
//        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/hospital/resources/views/login.fxml"));
////            System.out.println(Objects.requireNonNull(getClass().getResource("../../resources/views/register.fxml")).getPath());
//        Parent root = null;
//        try {
//            root = loader.load();
//        primaryStage.setTitle("Login");
//        primaryStage.setScene(new Scene(root, 500, 600));
//        primaryStage.getIcons().add(new Image(Objects.requireNonNull(LoginController.class.getResourceAsStream("/hospital/resources/images/logo.png"))));
//        mainStage.close();
//        primaryStage.show();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//    }

    @FXML
    private void handleShowView(ActionEvent e) {
        String view = (String) ((MenuItem) e.getSource()).getUserData();
        System.out.println(e.getSource());
        System.out.println(view);
        loadFXML(MainController.class.getResource(view));
    }

    private void loadFXML(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            navPane.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void addPatient() {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/hospital/resources/views/allPatients.fxml"));
        AllPatientsController controller = loader.getController();
        controller.showAddPatientDialog();
    }

    @FXML
    private void editPatient () {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/hospital/resources/views/allPatients.fxml"));
        AllPatientsController controller = loader.getController();
        controller.editPatient();
    }

    @FXML
    private void deletePatient() {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/hospital/resources/views/allPatients.fxml"));
        AllPatientsController controller = loader.getController();
        controller.deletePatient();
    }

}