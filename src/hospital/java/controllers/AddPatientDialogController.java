package hospital.java.controllers;

import hospital.java.models.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddPatientDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField fatherNameField;
    @FXML
    private TextField motherNameField;
    @FXML
    private Label nameError;
    @FXML
    private Label rollNumError;

    public void setData(Patient patient) {
        this.nameField.setText(patient.getName());
        this.addressField.setText(patient.getAddress());
        this.fatherNameField.setText(patient.getFatherName());
        this.motherNameField.setText(patient.getMotherName());
    }

    public Patient processResults() {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String fatherName = fatherNameField.getText().trim();
        String motherName = motherNameField.getText().trim();

        Patient patient = new Patient();
        patient.setName(name);
        patient.setAddress(address);
        patient.setFatherName(fatherName);
        patient.setMotherName(motherName);

        return patient;
    }

    public boolean validate() {
        boolean isValid = true;
        nameError.setVisible(false);
        rollNumError.setVisible(false);
        if ( this.nameField.getText().trim().length() < 3 ) {
            nameError.setText("Name cannot be null.");
            nameError.setVisible(true);
            isValid = false;
        }
        // if(!(Datasource.instance.isUnique(rollNumField.getText()))) {
        //     rollNumError.setText("Roll Number must be unique.");
        //     rollNumError.setVisible(true);
        //     isValid = false;
        // }
        // if ( this.rollNumField.getText().isEmpty()) {
        //     rollNumError.setText("Roll Number cannot be null.");
        //     rollNumError.setVisible(true);
        //     isValid = false;
        // }
        return isValid;
    }

}
