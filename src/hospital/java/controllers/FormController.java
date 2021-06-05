package hospital.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintServiceLookup;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.SimpleDoc;

import hospital.java.models.Patient;
import hospital.java.sources.Datasource;
import javafx.concurrent.Task;

public class FormController {

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
        if (this.nameField.getText().trim().length() < 3) {
            nameError.setText("Name cannot be null.");
            nameError.setVisible(true);
            isValid = false;
        }
        // if (!(Datasource.instance.isUnique())) {
        //     rollNumError.setText("Roll Number must be unique.");
        //     rollNumError.setVisible(true);
        //     isValid = false;
        // }
        // if (this.rollNumField.getText().isEmpty()) {
        //     rollNumError.setText("Roll Number cannot be null.");
        //     rollNumError.setVisible(true);
        //     isValid = false;
        // }
        return isValid;
    }

    @FXML
    private void printDatabase() {
        if (validate()) {
            try (BufferedWriter locFile = new BufferedWriter(new FileWriter("document.doc"))) {

                Patient patient = processResults();
                locFile.write(Patient.getDetailsString(patient));

                PrintJobWatcher pjDone = new PrintJobWatcher();
                InputStream is = new BufferedInputStream(new FileInputStream("document.doc"));
                PrintService service = PrintServiceLookup.lookupDefaultPrintService();
                DocPrintJob job = service.createPrintJob();
                Doc doc = new SimpleDoc(is, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
                job.addPrintJobListener(pjDone);
                job.print(doc, null);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (PrintException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void saveDatabase() {
        if (validate()) {
            Patient patient = processResults();

            Task<Boolean> task = new Task<>() {
                @Override
                protected Boolean call() throws Exception {
                    int id = Datasource.instance.insertPatient(patient.getName(),
                            patient.getAddress(), patient.getFatherName(), patient.getMotherName());
                    patient.setId(id);
                    return true;
                }
            };

            task.setOnSucceeded(e -> {
                Datasource.getPatients().add(patient);
            });

            new Thread(task).start();
        }
    }

}

