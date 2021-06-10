package hospital.java.repositories.patient_repository;

import hospital.java.models.Patient;
import hospital.java.models.PatientData;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Window;

import java.net.URL;
import java.util.ArrayList;

public interface BasePatientRepository {
    void addPatientWithDialog(Window window, URL url, ListView<Patient> patientList);
    void editPatient (Window window, URL url, ListView<Patient> patientList, TableView<PatientData> patientDetails);
    void deletePatient (ListView<Patient> patientList);
    void printAllPatientDetails ();
    void printPatientDetails (ArrayList<String> patient);
}
