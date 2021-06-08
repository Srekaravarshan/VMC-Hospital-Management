package hospital.java.controllers;

import hospital.java.models.Patient;
import hospital.java.sources.DropdownData;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddPatientDialogController {

    @FXML
    private TextField nameField, uhidField, ageField, sexField;
    @FXML
    private ComboBox<String> 
            riskFactorsBox, 
            otherComorbiditiesBox, 
            pastCadBox, 
            treatmentForPastCadBox, 
            echoBox, 
            currentDiagnosisBox, 
            coronaryAngiographyBox, 
            pciBox, 
            complicationsPredischargeBox, 
            postPciBox;
    @FXML
    private Label nameError, uhidError;

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
    
    public void setData(Patient patient) {
        this.nameField.setText(patient.getName());
        this.uhidField.setText(patient.getUHID());
        this.ageField.setText(String.valueOf(patient.getAge()));
        this.sexField.setText(patient.getSex());
        this.riskFactorsBox.getSelectionModel().select(patient.getRiskFactors());
        this.otherComorbiditiesBox.getSelectionModel().select(patient.getOtherComorbidities());
        this.pastCadBox.getSelectionModel().select(patient.getCad());
        this.treatmentForPastCadBox.getSelectionModel().select(patient.getTreatmentForPastCad());
        this.echoBox.getSelectionModel().select(patient.getEcho());
        this.currentDiagnosisBox.getSelectionModel().select(patient.getCurrentDiagnosis());
        this.coronaryAngiographyBox.getSelectionModel().select(patient.getCoronaryAngiography());
        this.pciBox.getSelectionModel().select(patient.getPci());
        this.complicationsPredischargeBox.getSelectionModel().select(patient.getComplicationsInHospitalPredischarge());
        this.postPciBox.getSelectionModel().select(patient.getPostPci());
    }

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
        patient.setAge(Integer.valueOf(age));
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
