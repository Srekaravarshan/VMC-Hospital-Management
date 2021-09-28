package hospital.java.controllers;

import hospital.java.models.DoctorModel;
import hospital.java.repositories.patient_repository.PatientRepository;
import hospital.java.sources.Datasource;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class DoctorsController {

    @FXML
    private VBox list;

    private VBox listItem(DoctorModel doctor) {

        Label name = new Label();
        name.setText(doctor.getName() + " " + doctor.getQualification());
        name.getStyleClass().add("key_label");

        Label regNo = new Label();
        regNo.setText(doctor.getRegNo());

        Label hospital = new Label();
        hospital.setText(doctor.getHospital());

        Label mobile = new Label();
        mobile.setText(doctor.getMobile());

        Label email = new Label();
        email.setText(doctor.getEmail());

        Label district = new Label();
        district.setText(doctor.getDistrict() + " - " + doctor.getPincode());

        Button edit = new Button();
        edit.setText("Edit");
        edit.getStyleClass().add("nav_btn");
        edit.setOnAction(event -> {
            try {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(list.getScene().getWindow());
                dialog.setTitle("Add doctor");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainController.class.getResource("/hospital/resources/views/addDoctor.fxml"));

                dialog.getDialogPane().setContent(fxmlLoader.load());

                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

                Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
                dialogStage.getIcons().add(new Image(Objects.requireNonNull(
                        PatientRepository.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

                AddDoctorsController controller = fxmlLoader.getController();
                controller.setData(doctor);

                final Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
                okButton.addEventFilter(ActionEvent.ACTION, ae -> {
                    if (!(controller.validate())) {
                        ae.consume();
                    } else {
                        try {
                            DoctorModel editedDoctor = controller.processResults();
                            String oldName = doctor.getName();
                            doctor.setName(editedDoctor.getName());
                            doctor.setQualification(editedDoctor.getQualification());
                            doctor.setDepartment(editedDoctor.getDepartment());
                            doctor.setRegNo(editedDoctor.getRegNo());
                            doctor.setHospital(editedDoctor.getHospital());
                            doctor.setDistrict(editedDoctor.getDistrict());
                            doctor.setPincode(editedDoctor.getPincode());
                            doctor.setMobile(editedDoctor.getMobile());
                            doctor.setEmail(editedDoctor.getEmail());

                            Datasource.instance.updateDoctor(doctor, oldName);
                            Datasource.getDoctors().set(Datasource.getDoctors().indexOf(doctor), doctor);
                            list.getChildren().set(Datasource.getDoctors().indexOf(doctor), listItem(doctor));
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
                dialog.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button delete = new Button();
        delete.setText("Delete");
        delete.getStyleClass().add("nav_btn");
        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Doctor");
            alert.setHeaderText("Remove Doctor: " + doctor.getName());
            alert.setContentText("Are you sure?  Press OK to confirm, or cancel to Back out.");

            Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(
                    PatientRepository.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                Task<Boolean> task = new Task<>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return Datasource.instance.deleteDoctor(doctor);
                    }
                };

                task.setOnSucceeded(e -> {
                    list.getChildren().remove(Datasource.getDoctors().indexOf(doctor));
                    Datasource.getDoctors().remove(doctor);
                });

                new Thread(task).start();
            }
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(edit, delete);
        buttons.setAlignment(Pos.TOP_RIGHT);
        buttons.setSpacing(10);
        buttons.setMaxWidth(280);
        buttons.setPrefHeight(25);

        VBox item = new VBox();
        item.getChildren().addAll(name, regNo, hospital, mobile, email, district, buttons);
        item.setMaxWidth(280);

        return item;
    }

    public void initialize() {
        try {

            Datasource.instance.queryDoctors();

            for (DoctorModel doctor : Datasource.getDoctors()) {
                list.getChildren().add(listItem(doctor));
            }

            list.setSpacing(5);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void addDoctor() {

        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(list.getScene().getWindow());
            dialog.setTitle("Add doctor");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainController.class.getResource("/hospital/resources/views/addDoctor.fxml"));

            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(
                    PatientRepository.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

            AddDoctorsController controller = fxmlLoader.getController();

            final Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.addEventFilter(ActionEvent.ACTION, ae -> {
                if (!(controller.validate())) {
                    ae.consume();
                } else {
                    try {
                        DoctorModel doctor = controller.processResults();
                        int id = Datasource.instance.insertDoctor(doctor);
                        doctor.setId(id);
                        Datasource.getDoctors().add(doctor);
                        list.getChildren().add(listItem(doctor));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
