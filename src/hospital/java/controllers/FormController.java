package hospital.java.controllers;

import java.io.IOException;

import hospital.java.models.Patient;
import hospital.java.repositories.patient_repository.PatientRepository;
import hospital.java.sources.Datasource;
import hospital.java.sources.DropdownData;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FormController {

    @FXML
    private TextField nameField, uhidField, ageField, sexField;
    @FXML
    private ComboBox<String> riskFactorsBox, otherComorbiditiesBox, pastCadBox, treatmentForPastCadBox, echoBox,
            currentDiagnosisBox, coronaryAngiographyBox, pciBox, complicationsPredischargeBox, postPciBox;
    @FXML
    private Label nameError, uhidError;
    @FXML
    private AnchorPane flash_msg;

    PatientRepository patientRepository = new PatientRepository();

    public void initialize() {
        riskFactorsBox.getItems().addAll(DropdownData.riskFactorsList);
        otherComorbiditiesBox.getItems().addAll(DropdownData.otherComorbiditiesList);
        pastCadBox.getItems().addAll(DropdownData.pastCadList);
        treatmentForPastCadBox.getItems().addAll(DropdownData.treatmentForPastCadList);
        echoBox.getItems().addAll(DropdownData.echoList);
        currentDiagnosisBox.getItems().addAll(DropdownData.currentDiagnosisList);
        coronaryAngiographyBox.getItems().addAll(DropdownData.coronaryAngiographyList);
        pciBox.getItems().addAll(DropdownData.pciList);
        complicationsPredischargeBox.getItems().addAll(DropdownData.complicationsPredischargeList);
        postPciBox.getItems().addAll(DropdownData.postPsiList);
    }


//    public void setData(Patient patient) {
//        this.nameField.setText(patient.getName());
//        this.addressField.setText(patient.getAddress());
//        this.fatherNameField.setText(patient.getFatherName());
//        this.motherNameField.setText(patient.getMotherName());
//    }
public Patient processResults() {
    String name = nameField.getText().trim();
    String uhid = uhidField.getText().trim();
    String age = ageField.getText().trim();
    String sex = sexField.getText().trim();
    String riskFactors = riskFactorsBox.getSelectionModel().getSelectedItem();
    String otherComorbidities = otherComorbiditiesBox.getSelectionModel().getSelectedItem();
    String pastCad = pastCadBox.getSelectionModel().getSelectedItem();
    String treatmentForPastCad = treatmentForPastCadBox.getSelectionModel().getSelectedItem();
    String echo = echoBox.getSelectionModel().getSelectedItem();
    String currentDiagnosis = currentDiagnosisBox.getSelectionModel().getSelectedItem();
    String coronaryAngiography = coronaryAngiographyBox.getSelectionModel().getSelectedItem();
    String pci = pciBox.getSelectionModel().getSelectedItem();
    String complicationsPredischarge = complicationsPredischargeBox.getSelectionModel().getSelectedItem();
    String postPci = postPciBox.getSelectionModel().getSelectedItem();

    Patient patient = new Patient();
    patient.setName(name);
    patient.setUHID(uhid);
    patient.setAge(Integer.parseInt(age));
    patient.setSex(sex);
    patient.setRiskFactors(riskFactors);
    patient.setOtherComorbidities(otherComorbidities);
    patient.setCad(pastCad);
    patient.setTreatmentForPastCad(treatmentForPastCad);
    patient.setEcho(echo);
    patient.setCurrentDiagnosis(currentDiagnosis);
    patient.setCoronaryAngiography(coronaryAngiography);
    patient.setPci(pci);
    patient.setComplicationsInHospitalPredischarge(complicationsPredischarge);
    patient.setPostPci(postPci);

    return patient;
}

    public boolean validate() {
        boolean isValid = true;
        nameError.setVisible(false);
        uhidError.setVisible(false);
        if (this.nameField.getText().trim().length() < 3) {
            nameError.setText("Name cannot be null.");
            nameError.setVisible(true);
            isValid = false;
        }
        // if (!(Datasource.instance.isUnique())) {
        // rollNumError.setText("Roll Number must be unique.");
        // rollNumError.setVisible(true);
        // isValid = false;
        // }
        // if (this.rollNumField.getText().isEmpty()) {
        // rollNumError.setText("Roll Number cannot be null.");
        // rollNumError.setVisible(true);
        // isValid = false;
        // }
        return isValid;
    }

    @FXML
    private void printDatabase() {
        if (validate()) {
            Patient patient = processResults();
            // patientRepository.printPatientDetails(patient);
            System.out.println(Patient.getDetailsString(patient));
            try {
                patientRepository.printHtmlPage();
            } catch (IOException e) {
                // TODO Auto-generated catch block
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
                    int id = Datasource.instance.insertPatient(patient.getName(), patient.getUHID(), patient.getAge(),
                            patient.getSex(), patient.getRiskFactors(), patient.getOtherComorbidities(),
                            patient.getCad(), patient.getTreatmentForPastCad(), patient.getEcho(),
                            patient.getCurrentDiagnosis(), patient.getCoronaryAngiography(), patient.getPci(),
                            patient.getComplicationsInHospitalPredischarge(), patient.getPostPci());
                    patient.setId(id);
                    return true;
                }
            };


//            TranslateTransition openNav=new TranslateTransition(new Duration(350), flash_msg);
//            openNav.setToY(-50);
//            TranslateTransition closeNav=new TranslateTransition(new Duration(350), flash_msg);

            task.setOnSucceeded(e -> {
                Datasource.getPatients().add(patient);
//                openNav.play();
//                System.out.println("bplay");
//                flash_msg.setLayoutY(-50);
//                System.out.println("aplay");
//                Platform.runLater(() -> {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException interruptedException) {
//                        interruptedException.printStackTrace();
//                        flash_msg.setLayoutY(0);
//                    }
//                    flash_msg.setLayoutY(0);
////                    closeNav.setToY(0);
////                System.out.println("bplay");
////                    closeNav.play();
////                System.out.println("aplay");
//                });
            });

            new Thread(task).start();
        }
    }

}
