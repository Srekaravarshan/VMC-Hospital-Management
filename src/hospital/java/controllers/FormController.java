package hospital.java.controllers;

import hospital.java.models.CagModel;
import hospital.java.models.Patient;
import hospital.java.models.PciModel;
import hospital.java.repositories.patient_repository.PatientRepository;
import hospital.java.sources.Datasource;
import hospital.java.sources.PageSource;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class FormController {

    PageSource pageSource = PageSource.form.isEditing() ? PageSource.editForm : PageSource.form;
    PatientRepository patientRepository = new PatientRepository();
    @FXML
    private BorderPane addPatientPane;
    @FXML
    private Button formSaveBtn, formPrintButton, page1, page2, page3;

    public void initialize() {

        PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
        delay.setOnFinished(event -> {
            pageSource.fxmlLoaders().add(new FXMLLoader(
                    MainController.class.getResource("/hospital/resources/views/page1.fxml")));
            pageSource.fxmlLoaders().add(new FXMLLoader(
                    MainController.class.getResource("/hospital/resources/views/page2.fxml")));
            pageSource.fxmlLoaders().add(new FXMLLoader(
                    MainController.class.getResource("/hospital/resources/views/page3.fxml")));
            try {
                pageSource.pages().add(pageSource.fxmlLoaders().get(0).load());
                pageSource.pages().add(pageSource.fxmlLoaders().get(1).load());
                pageSource.pages().add(pageSource.fxmlLoaders().get(2).load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            pageSource.setPage1Controller(pageSource.fxmlLoaders().get(0).getController());
            pageSource.setPage2Controller(pageSource.fxmlLoaders().get(1).getController());
            pageSource.setPage3Controller(pageSource.fxmlLoaders().get(2).getController());

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().add(pageSource.pages().get(0));

            page1.getStyleClass().add("selected_nav");

            formSaveBtn.setVisible(!PageSource.form().isEditing());
            formPrintButton.setVisible(!PageSource.form().isEditing());

            addPatientPane.setCenter(pageSource.pages().get(0));

            if (PageSource.form.isEditing())
                setData(pageSource.getPatient(), pageSource.getCag(), pageSource.getPci());

            page2.setVisible(false);
            page3.setVisible(false);
            if(pageSource.page1Controller().cagCheck!=null)
                page3.setVisible(pageSource.page1Controller().cagCheck.isSelected());
            if(pageSource.page1Controller().pciCheck!=null)
                page2.setVisible(pageSource.page1Controller().pciCheck.isSelected());

            pageSource.page1Controller().cagCheck.selectedProperty().addListener((observable, oldValue, newValue) -> page3.setVisible(newValue));
            pageSource.page1Controller().pciCheck.selectedProperty().addListener((observable, oldValue, newValue) -> page2.setVisible(newValue));

        });
        delay.play();
    }

    @FXML
    private void navPage1() {
        if (pageSource.pageIndex() != 0) {
            addPatientPane.setCenter(pageSource.pages().get(0));
            pageSource.setPageIndex(0);

            page1.getStyleClass().remove("selected_nav");
            page2.getStyleClass().remove("selected_nav");
            page3.getStyleClass().remove("selected_nav");

            page1.getStyleClass().add("selected_nav");
        }
    }

    @FXML
    private void navPage2() {
        if (pageSource.pageIndex() != 1) {
            addPatientPane.setCenter(pageSource.pages().get(1));
            pageSource.setPageIndex(1);

            page1.getStyleClass().remove("selected_nav");
            page2.getStyleClass().remove("selected_nav");
            page3.getStyleClass().remove("selected_nav");

            page2.getStyleClass().add("selected_nav");
        }
    }

    @FXML
    private void navPage3() {
        if (pageSource.pageIndex() != 2) {
            addPatientPane.setCenter(pageSource.pages().get(2));
            pageSource.setPageIndex(2);

            page1.getStyleClass().remove("selected_nav");
            page2.getStyleClass().remove("selected_nav");
            page3.getStyleClass().remove("selected_nav");

            page3.getStyleClass().addAll("selected_nav");
        }
    }

    @FXML
    public void save() {
        try {
            if (pageSource.page1Controller().validate(false)) {

                Patient patient = pageSource.page1Controller().processResults();
                CagModel cag = pageSource.page3Controller().processResults();
                PciModel pci = pageSource.page2Controller().processResults();

//                new Thread(() -> {
                try {
                    int id = Datasource.instance.insertPatient(patient);
                    Datasource.instance.insertCagPci(id, cag, pci);

                    patient.setId(id);
                    cag.setId(id);
                    cag.setId(id);

                    Datasource.getPatients().add(patient);
                    if (pageSource.pageIndex() == 0) {
                        new Thread(() -> {
                            pageSource.page1Controller().flashMessage();
                            try {
                                Thread.sleep(2000);
                                pageSource.page1Controller().closeFlashMessage();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                pageSource.page1Controller().closeFlashMessage();
                            }
                        }).start();
                    } else if (pageSource.pageIndex() == 1) {
                        new Thread(() -> {
                            pageSource.page2Controller().flashMessage();
                            try {
                                Thread.sleep(2000);
                                pageSource.page2Controller().closeFlashMessage();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                pageSource.page2Controller().closeFlashMessage();
                            }
                        }).start();
                    } else {
                        new Thread(() -> {
                            pageSource.page3Controller().flashMessage();
                            try {
                                Thread.sleep(2000);
                                pageSource.page3Controller().closeFlashMessage();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                pageSource.page3Controller().closeFlashMessage();
                            }
                        }).start();
                    }
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
//                }).start();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void print() {
        try {
            if (pageSource.page1Controller().validate(true)) {
                Patient patient = pageSource.page1Controller().processResults();
                new Thread(() -> patientRepository.printAllDetails(patient, pageSource.page3Controller().processResults(), pageSource.page2Controller().processResults())).start();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clearAll() {
        pageSource.page1Controller().clear();
        pageSource.page2Controller().clear();
        pageSource.page3Controller().clear();
    }

    public void setData(Patient patient, CagModel cag, PciModel pci) {

        pageSource.page1Controller().setData(patient);
        pageSource.page2Controller().setData(pci);
        pageSource.page3Controller().setData(cag);
    }

    public Patient getEditedPatient() {
        return pageSource.page1Controller().processResults();
    }

    public CagModel getEditedCag() {
        return pageSource.page3Controller().processResults();
    }

    public PciModel getEditedPci() {
        return pageSource.page2Controller().processResults();
    }

}
