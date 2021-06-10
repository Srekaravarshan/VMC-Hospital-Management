package hospital.java.controllers;

import hospital.java.models.Patient;
import hospital.java.models.PatientData;
import hospital.java.repositories.patient_repository.PatientRepository;
import hospital.java.sources.Datasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.function.Predicate;

public class AllPatientsController {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ListView<Patient> patientList;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<PatientData> patientDetails;

    @FXML
    private ContextMenu listContextMenu;

<<<<<<< HEAD
    public ListView<Patient> getPatientList () {
        return patientList;
    }

    public TableView<PatientData> getPatientDetails () {
        return patientDetails;
    }

=======
>>>>>>> 0fb1a35a8d899d110356ec7e4a9a5ae2369808dc
    PatientRepository patientRepository = new PatientRepository();

    private FilteredList<Patient> filteredList;

    private Predicate<Patient> filterPredicate(String searchText) {
        return patient -> {
            if (searchText == null || searchText.isEmpty())
                return true;
            return searchFindOrder(patient, searchText);
        };
    }

    private boolean searchFindOrder(Patient patient, String searchText) {
        return patient.getName().toLowerCase().contains(searchText.toLowerCase());
    }

    public void initialize() {

        TableColumn<PatientData, String> column1 = new TableColumn<>("Title");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<PatientData, String> column2 = new TableColumn<>("Detail");
        column2.setCellValueFactory(new PropertyValueFactory<>("detail"));

        patientDetails.getColumns().add(column1);
        patientDetails.getColumns().add(column2);

        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(actionEvent -> deletePatient());

        MenuItem editMenuItem = new MenuItem("Edit");
        editMenuItem.setOnAction(actionEvent -> editPatient());

        listContextMenu.getItems().addAll(editMenuItem, deleteMenuItem);
        patientList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null && patientList.getSelectionModel().getSelectedItem() != null) {
                Patient patient = patientList.getSelectionModel().getSelectedItem();
                patientRepository.updateTableRow(patient, patientDetails);
            }
        });

        filteredList = new FilteredList<>(Datasource.getPatients(), filterPredicate(searchField.getText().trim()));

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(filterPredicate(newValue));
            if (filteredList.isEmpty()) {
                patientRepository.updateTableRow(null, null);
            } else {
                patientList.getSelectionModel().selectFirst();
            }
            patientList.refresh();
        });

        patientList.setItems(filteredList);
        patientList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        patientList.getSelectionModel().selectFirst();

        patientList.setCellFactory(patientListView -> {
            ListCell<Patient> cell = new ListCell<>() {

                @Override
                protected void updateItem(Patient item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            };
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(listContextMenu);
                }

            });
            return cell;
        });

    }

    public static void listPatients() {
        GetAllPatientsTask task = new GetAllPatientsTask();
        new Thread(task).start();
    }

    @FXML
    public void showAddPatientDialog() {
        patientRepository.addPatientWithDialog(mainBorderPane.getScene().getWindow(),
                AllPatientsController.class.getResource("/hospital/resources/views/addPatientDialog.fxml"),
                patientList);
    }

    @FXML
    void deletePatient() {
        patientRepository.deletePatient(patientList);
    }

    @FXML
    void editPatient() {
        patientRepository.editPatient(mainBorderPane.getScene().getWindow(),
                AllPatientsController.class.getResource("/hospital/resources/views/addPatientDialog.fxml"),
                patientList, patientDetails);
    }

    @FXML
    private void listKeyPressed() {
        Patient patient = patientList.getSelectionModel().getSelectedItem();
        patientRepository.updateTableRow(patient, patientDetails);

    }

    @FXML
    private void printDatabase() {
        patientRepository.printPatientDetails(patientList.getSelectionModel().getSelectedItem());
    }
}

class GetAllPatientsTask extends Task<ObservableList<Patient>> {
    @Override
    protected ObservableList<Patient> call() {
        return FXCollections.observableArrayList(Datasource.instance.queryPatients());
    }
}
