package hospital.java.repositories.patient_repository;

import hospital.java.models.Patient;
import hospital.java.models.PatientData;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Window;

import java.net.URL;

public interface BasePatientRepository {
    void addPatientWithDialog(Window window, URL url, ListView<Patient> patientList);
    void editPatient (Window window, URL url, ListView<Patient> patientList, TableView<PatientData> patientDetails);
    void deletePatient (ListView<Patient> patientList);
    void printAllPatientDetails ();
    void printPatientDetails (Patient patient);
}
