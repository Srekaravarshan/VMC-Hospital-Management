package hospital.java.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;
import java.util.function.Predicate;

import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintServiceLookup;
import javax.print.event.PrintJobListener;

import hospital.java.models.Patient;
import hospital.java.sources.Datasource;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.event.PrintJobEvent;

public class AllPatientsController {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ListView<Patient> patientList;
    @FXML
    private TextField searchField;
    @FXML
    private TextArea patientDetails;

    @FXML
    private ContextMenu listContextMenu;

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
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(actionEvent -> deletePatient());

        MenuItem editMenuItem = new MenuItem("Edit");
        editMenuItem.setOnAction(actionEvent -> editPatient());

        listContextMenu.getItems().addAll(editMenuItem, deleteMenuItem);
        patientList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null && patientList.getSelectionModel().getSelectedItem() != null) {
                Patient patient = patientList.getSelectionModel().getSelectedItem();
                patientDetails.setText(Patient.getDetailsString(patient));
            }
        });

        filteredList = new FilteredList<>(Datasource.getPatients(), filterPredicate(searchField.getText().trim()));

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(filterPredicate(newValue));
            if (filteredList.isEmpty()) {
                patientDetails.setText("");
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
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add Patient");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../../resources/views/addPatientDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddPatientDialogController controller = fxmlLoader.getController();

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().add(new Image(RegisterController.class.getResourceAsStream("../../resources/images/logo.png")));

        final Button btnOK = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        btnOK.addEventFilter(ActionEvent.ACTION, event -> {
            // Check whether some conditions are fulfilled
            if (!controller.validate()) {
                // The conditions are not fulfilled so we consume the event
                // to prevent the dialog to close
                event.consume();
            }
        });

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Patient patient = controller.processResults();

            Task<Boolean> task = new Task<>() {
                @Override
                protected Boolean call() throws Exception {
                    int id = Datasource.instance.insertPatient(patient.getName(), patient.getAddress(),
                            patient.getFatherName(), patient.getMotherName());
                    patient.setId(id);
                    return true;
                }
            };

            task.setOnSucceeded(e -> {
                Datasource.getPatients().add(patient);
                patientList.getSelectionModel().select(patient);
                patientList.refresh();
            });

            new Thread(task).start();

        }
    }

    @FXML
    private void deletePatient() {

        Patient patient = patientList.getSelectionModel().getSelectedItem();

        if (!(patient == null)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Patient");
            alert.setHeaderText("Remove Patient: " + patient.getName());
            alert.setContentText("Are you sure?  Press OK to confirm, or cancel to Back out.");

            Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(RegisterController.class.getResourceAsStream("../../resources/images/logo.png")));

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                Task<Boolean> task = new Task<>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return Datasource.instance.deletePatient(patient.getId());
                    }
                };

                task.setOnSucceeded(e -> {
                    Datasource.getPatients().remove(patient);
                    patientList.refresh();
                });

                new Thread(task).start();
            }
        }
    }

    @FXML
    private void editPatient() {
        final Patient patient = patientList.getSelectionModel().getSelectedItem();

        if (!(patient == null)) {
            int patientId = patient.getId();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(mainBorderPane.getScene().getWindow());
            dialog.setTitle("Edit Patient");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../resources/views/addPatientDialog.fxml"));
            
            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(RegisterController.class.getResourceAsStream("../../resources/images/logo.png")));

            AddPatientDialogController controller = fxmlLoader.getController();
            controller.setData(patient);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Patient editedPatient = controller.processResults();
                patient.setName(editedPatient.getName());
                patient.setId(editedPatient.getId());
                patient.setAddress(editedPatient.getAddress());
                patient.setFatherName(editedPatient.getFatherName());
                patient.setMotherName(editedPatient.getMotherName());
                patient.setId(patientId);

                Task<Boolean> task = new Task<>() {
                    @Override
                    protected Boolean call() {
                        return Datasource.instance.updatePatient(patient);
                    }
                };

                patientDetails.setText(Patient.getDetailsString(patient));
                task.setOnSucceeded(e -> patientList.refresh());

                new Thread(task).start();

            }
        }
    }

    @FXML
    private void listKeyPressed() {
        Patient patient = patientList.getSelectionModel().getSelectedItem();
        patientDetails.setText(Patient.getDetailsString(patient));

    }

    @FXML
    private void printDatabase() {
        try (BufferedWriter locFile = new BufferedWriter(new FileWriter("document.doc"))) {

            for (Patient patient : Datasource.getPatients()) {
                locFile.write(Patient.getDetailsString(patient));
                locFile.write((char) 12 + "");
            }

            // File file = new File("document.doc");
            // Desktop.getDesktop().print(file);

            PrintJobWatcher pjDone = new PrintJobWatcher();
            InputStream is = new BufferedInputStream(new FileInputStream("document.doc"));
            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            DocPrintJob job = service.createPrintJob();
            Doc doc = new SimpleDoc(is, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
            job.addPrintJobListener(pjDone);
            job.print(doc, null);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PrintException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class GetAllPatientsTask extends Task<ObservableList<Patient>> {
    @Override
    protected ObservableList<Patient> call() {
        return FXCollections.observableArrayList(Datasource.instance.queryPatients());
    }
}

class PrintJobWatcher implements PrintJobListener {

    Boolean done = false;
    Integer status = 0;

    PrintJobWatcher() {
        System.out.println("PrintJobWatcher()");
    }

    @Override
    public void printDataTransferCompleted(PrintJobEvent pje) {
        this.done(PrintJobEvent.DATA_TRANSFER_COMPLETE);
        System.out.println("DATA_TRANSFER_COMPLETE");
    }

    @Override
    public void printJobCompleted(PrintJobEvent pje) {
        this.done(PrintJobEvent.JOB_COMPLETE);
        System.out.println("JOB_COMPLETE");
    }

    @Override
    public void printJobFailed(PrintJobEvent pje) {
        this.done(PrintJobEvent.JOB_FAILED);
        System.out.println("JOB_FAILED");
    }

    @Override
    public void printJobCanceled(PrintJobEvent pje) {
        this.done(PrintJobEvent.JOB_CANCELED);
        System.out.println("JOB_CANCELED");
    }

    @Override
    public void printJobNoMoreEvents(PrintJobEvent pje) {
        this.done(PrintJobEvent.NO_MORE_EVENTS);
        System.out.println("NO_MORE_EVENTS");
    }

    @Override
    public void printJobRequiresAttention(PrintJobEvent pje) {
        this.done(PrintJobEvent.REQUIRES_ATTENTION);
        System.out.println("REQUIRES_ATTENTION");
    }

    private synchronized void done(Integer status) {
        System.out.println("DONE !");
        this.status = status;
        this.done = true;
        notifyAll();
    }

    synchronized void waitForDone() throws InterruptedException {
        System.out.println("AVANT : IMPRESSION EN COURS...");
        try {
            while (!this.done
                    || ((this.status != PrintJobEvent.JOB_COMPLETE) || (this.status != PrintJobEvent.JOB_FAILED))) {
                System.out.println("IMPRESSION EN COURS...");
                wait();
            }
        } catch (InterruptedException e) {
        }
    }
}