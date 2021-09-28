package hospital.java.controllers;

import hospital.java.models.Patient;
import hospital.java.models.PatientData;
import hospital.java.repositories.patient_repository.PatientRepository;
import hospital.java.sources.Datasource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Predicate;

public class AllPatientsController {

    PatientRepository patientRepository = new PatientRepository();
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
    @FXML
    private AnchorPane flash_msg;
    private FilteredList<Patient> filteredList;

    public static void listPatients() {
        GetAllPatientsTask task = new GetAllPatientsTask();
        new Thread(task).start();
    }

    public ListView<Patient> getPatientList() {
        return patientList;
    }

    public TableView<PatientData> getPatientDetails() {
        return patientDetails;
    }

    private Predicate<Patient> filterPredicate(String searchText) {
        return patient -> {
            if (searchText == null || searchText.isEmpty())
                return true;
            return searchFindOrder(patient, searchText);
        };
    }

    private boolean searchFindOrder(Patient patient, String searchText) {
        return (patient.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                (patient.getUHID().toLowerCase().contains(searchText.toLowerCase())) ||
                (patient.toString().contains(searchText.toLowerCase())) ||
                (Datasource.getCags().get(patient.getId()).toString().contains(searchText.toLowerCase())) ||
                (Datasource.getPcis().get(patient.getId()).toString().contains(searchText.toLowerCase())));
    }

    public void initialize() {

        TableColumn<PatientData, String> column1 = new TableColumn<>("Title");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        column1.setPrefWidth(250);
        TableColumn<PatientData, String> column2 = new TableColumn<>("Detail");
        column2.setCellValueFactory(new PropertyValueFactory<>("detail"));
        column2.setPrefWidth(500);

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

//                HashMap<String, ArrayList<String>> keyValue = patient.getsKeys();

                ArrayList<String> values = new ArrayList<>();
                values.addAll(patient.getValues());
                values.addAll(Datasource.getCags().get(patient.getId()).getValues(false));
                values.addAll(Datasource.getPcis().get(patient.getId()).getValues(false));

                ArrayList<String> keys = new ArrayList<>();
                keys.addAll(patient.getKeys());
                keys.addAll(Datasource.getCags().get(patient.getId()).getKeys(false));
                keys.addAll(Datasource.getPcis().get(patient.getId()).getKeys(false));

                patientRepository.updateTableRow(values, keys, patientDetails, patient.getValues().size());
            } else {
                patientRepository.updateTableRow(null, null, patientDetails, 0);
            }
        });

        filteredList = new FilteredList<>(Datasource.getPatients(), filterPredicate(searchField.getText().trim()));

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(filterPredicate(newValue));
            if (filteredList.isEmpty()) {
                patientRepository.updateTableRow(null, null, null, 0);
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

    @FXML
    void deletePatient() {
        patientRepository.deletePatient(patientList, null);
    }

    @FXML
    void editPatient() {

        try {
            patientRepository.editPatient(mainBorderPane.getScene().getWindow(),
                    AllPatientsController.class.getResource("/hospital/resources/views/form.fxml"),
                    patientList, patientDetails, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void listKeyPressed(KeyEvent keyEvent) {
        Patient patient = patientList.getSelectionModel().getSelectedItem();
        if (patient != null) {

            ArrayList<String> values = new ArrayList<>(patient.getValues());
            values.addAll(Datasource.getCags().get(patient.getId()).getValues(false));
            values.addAll(Datasource.getPcis().get(patient.getId()).getValues(false));

            ArrayList<String> keys = new ArrayList<>();
            keys.addAll(patient.getKeys());
            keys.addAll(Datasource.getCags().get(patient.getId()).getKeys(false));
            keys.addAll(Datasource.getPcis().get(patient.getId()).getKeys(false));

            patientRepository.updateTableRow(values, keys, patientDetails, patient.getValues().size());
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                patientRepository.deletePatient(patientList, null);
            }
        }
    }

    @FXML
    private void printDatabase() {
        Platform.runLater(() -> {
            flashMessage();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                closeFlashMessage();
            }
        });

        new Thread(() -> {
            Patient patient = patientList.getSelectionModel().getSelectedItem();
            patientRepository.printAllDetails(patient, Datasource.getCags().get(patient.getId()), Datasource.getPcis().get(patient.getId()));

        }).start();
    }

    @FXML
    private void printPage1() {

        Patient patient = patientList.getSelectionModel().getSelectedItem();

        ArrayList<String> values = new ArrayList<>(patient.getPrintValues());

        ArrayList<String> keys = new ArrayList<>(patient.getPrintKeys());

        try {
            if (!patientRepository.customPrintDialog(values, keys, mainBorderPane.getScene().getWindow(), null, null)) {
                return;
            }

            new Thread(() -> {
                flashMessage();
                try {
                    Thread.sleep(2000);
                    closeFlashMessage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    closeFlashMessage();
                }
            }).start();
//            new Thread (() -> {
            patientRepository.printPatientDetails(patient, keys, values, "PATIENT DETAILS", false, 1, patient.getDate(), null, null);
        } catch (InvalidFormatException | IOException | PrinterException e) {
            e.printStackTrace();
        }


//            }).start();
    }

    @FXML
    private void printPage3() {

        Patient patient = patientList.getSelectionModel().getSelectedItem();

        ArrayList<String> values = new ArrayList<>();
        values.add(patient.getName());
        values.add(patient.getUHID());
        values.add(String.valueOf(patient.getAge()));
        values.add(patient.getSex());
        values.add(patient.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        values.addAll(Datasource.getPcis().get(patient.getId()).getValues(true));

        ArrayList<String> keys = new ArrayList<>();
        keys.add("Name");
        keys.add("UHID");
        keys.add("Age");
        keys.add("Sex");
        keys.add("Date");
        keys.addAll(Datasource.getPcis().get(patient.getId()).getKeys(true));

        try {
            if (!patientRepository.customPrintDialog(values, keys, mainBorderPane.getScene().getWindow(), null, null)) {
                return;
            }

//            new Thread(() -> {
            flashMessage();
            try {
                Thread.sleep(2000);
                closeFlashMessage();
            } catch (InterruptedException e) {
                e.printStackTrace();
                closeFlashMessage();
            }
//            }).start();
            patientRepository.printPatientDetails(patient, Datasource.getPcis().get(patient.getId()).getKeys(true), Datasource.getPcis().get(patient.getId()).getValues(true), "ANGIOPLASTY NOTE", true, 3, Datasource.getPcis().get(patient.getId()).getDateOfProcedure(), null, Datasource.getPcis().get(patient.getId()));
        } catch (InvalidFormatException | IOException | PrinterException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void printPage2() {

        Patient patient = patientList.getSelectionModel().getSelectedItem();

        ArrayList<String> values = new ArrayList<>();
        values.add(patient.getName());
        values.add(patient.getUHID());
        values.add(String.valueOf(patient.getAge()));
        values.add(patient.getSex());
        values.add(patient.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        values.addAll(Datasource.getCags().get(patient.getId()).getValues(true));

        ArrayList<String> keys = new ArrayList<>();
        keys.add("Name");
        keys.add("UHID");
        keys.add("Age");
        keys.add("Sex");
        keys.add("Date");
        keys.addAll(Datasource.getCags().get(patient.getId()).getKeys(true));

        try {
            if (!patientRepository.customPrintDialog(values, keys, mainBorderPane.getScene().getWindow(), null, null)) {
                return;
            }

            flashMessage();
            try {
                Thread.sleep(2000);
                closeFlashMessage();
            } catch (InterruptedException e) {
                e.printStackTrace();
                closeFlashMessage();
            }
            patientRepository.printPatientDetails(patient, Datasource.getCags().get(patient.getId()).getKeys(true), Datasource.getCags().get(patient.getId()).getValues(true), "CARDIAC CATHETERIZATION AND CORONARY ANGIOGRAM REPORT", true, 2, Datasource.getCags().get(patient.getId()).getDateOfProcedure(), Datasource.getCags().get(patient.getId()), null);
        } catch (InvalidFormatException | IOException | PrinterException e) {
            e.printStackTrace();
        }

    }


    public void flashMessage() {
        flash_msg.setVisible(true);
    }

    public void closeFlashMessage() {
        flash_msg.setVisible(false);
    }

}

class GetAllPatientsTask extends Task<ObservableList<Patient>> {
    @Override
    protected ObservableList<Patient> call() {
        try {
            return FXCollections.observableArrayList(Datasource.instance.queryPatients());
        } catch (SQLException e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }
}
