package hospital.java.controllers;

import hospital.java.models.DoctorModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddDoctorsController {

    @FXML
    private TextField nameField,
        qualificationField,
        departmentField,
        regnField,
        hospitalField,
        districtField,
        pincodeField,
        mobileField,
        emailField;

    public boolean validate () {
        return !nameField.getText().trim().equals("");
    }

    public DoctorModel processResults () {
        DoctorModel doctor = null;

        if ( this.validate() ) {
            doctor = new DoctorModel();
            doctor.setName(nameField.getText().trim());
            doctor.setQualification(qualificationField.getText().trim());
            doctor.setDepartment(departmentField.getText().trim());
            doctor.setRegNo(regnField.getText().trim());
            doctor.setHospital(hospitalField.getText().trim());
            doctor.setDistrict(districtField.getText().trim());
            doctor.setPincode(pincodeField.getText().trim());
            doctor.setMobile(mobileField.getText().trim());
            doctor.setEmail(emailField.getText().trim());
        }
        return doctor;
    }

    public void setData (DoctorModel doctor) {
        nameField.setText(doctor.getName());
        qualificationField.setText(doctor.getQualification());
        departmentField.setText(doctor.getDepartment());
        regnField.setText(doctor.getRegNo());
        hospitalField.setText(doctor.getHospital());
        districtField.setText(doctor.getDistrict());
        pincodeField.setText(doctor.getPincode());
        mobileField.setText(doctor.getMobile());
        emailField.setText(doctor.getEmail());
    }

}
