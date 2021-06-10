package hospital.java.repositories.patient_repository;

import hospital.java.controllers.AddPatientDialogController;
import hospital.java.helpers.PrintJobWatcher;
<<<<<<< HEAD
import hospital.java.helpers.PrintableEditorPane;
=======
>>>>>>> 0fb1a35a8d899d110356ec7e4a9a5ae2369808dc
import hospital.java.models.Patient;
import hospital.java.models.PatientData;
import hospital.java.sources.Datasource;
import hospital.java.sources.DropdownData;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import javax.print.*;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

<<<<<<< HEAD
import java.awt.print.*;

=======
>>>>>>> 0fb1a35a8d899d110356ec7e4a9a5ae2369808dc
public class PatientRepository implements BasePatientRepository {
    @Override
    public void addPatientWithDialog(Window window, URL url, ListView<Patient> patientList) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(window);
        dialog.setTitle("Add Patient");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddPatientDialogController controller = fxmlLoader.getController();

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().add(new Image(Objects.requireNonNull(PatientRepository.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

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
                    int id = Datasource.instance.insertPatient(patient.getName(), patient.getUHID(), patient.getAge(),
                            patient.getSex(), patient.getRiskFactors(), patient.getOtherComorbidities(),
                            patient.getCad(), patient.getTreatmentForPastCad(), patient.getEcho(),
                            patient.getCurrentDiagnosis(), patient.getCoronaryAngiography(), patient.getPci(),
                            patient.getComplicationsInHospitalPredischarge(), patient.getPostPci());
                    patient.setId(id);
                    patient.setId(id);
                    return true;
                }
            };

            task.setOnSucceeded(e -> {
                Datasource.getPatients().add(patient);
                if (patientList != null) {
                    patientList.getSelectionModel().select(patient);
                    patientList.refresh();
                }
            });

            new Thread(task).start();

        }
    }

    @Override
    public void editPatient(Window window, URL url, ListView<Patient> patientList, TableView<PatientData> patientDetails) {
        final Patient patient = patientList.getSelectionModel().getSelectedItem();

        if (!(patient == null)) {
            int patientId = patient.getId();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(window);
            dialog.setTitle("Edit Patient");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);

            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(PatientRepository.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

            AddPatientDialogController controller = fxmlLoader.getController();
            controller.setData(patient);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Patient editedPatient = controller.processResults();

                patient.setName(editedPatient.getName());
                patient.setUHID(editedPatient.getUHID());
                patient.setAge(editedPatient.getAge());
                patient.setSex(editedPatient.getSex());
                patient.setRiskFactors(editedPatient.getRiskFactors());
                patient.setOtherComorbidities(editedPatient.getOtherComorbidities());
                patient.setCad(editedPatient.getCad());
                patient.setTreatmentForPastCad(editedPatient.getTreatmentForPastCad());
                patient.setEcho(editedPatient.getEcho());
                patient.setCurrentDiagnosis(editedPatient.getCurrentDiagnosis());
                patient.setCoronaryAngiography(editedPatient.getCoronaryAngiography());
                patient.setPci(editedPatient.getPci());
                patient.setComplicationsInHospitalPredischarge(editedPatient.getComplicationsInHospitalPredischarge());
                patient.setPostPci(editedPatient.getPostPci());
                patient.setId(patientId);

                Task<Boolean> task = new Task<>() {
                    @Override
                    protected Boolean call() {
                        return Datasource.instance.updatePatient(patient);
                    }
                };

                updateTableRow(patient, patientDetails);
                task.setOnSucceeded(e -> patientList.refresh());

                new Thread(task).start();

            }
        }
    }


    public void updateTableRow(Patient patient, TableView<PatientData> patientDetails) {
        if (patient != null) {
            ArrayList<String> titles = new ArrayList<>(Arrays.asList(
                    "Name", "UHID", "Age", "Sex", "Risk Factors", "Other Comorbidities",
                    "Past h/o CAD", "Treatment for past CAD", "Echo", "Current Diagnosis", "Coronary Angiography",
                    "PCI", "Complications In hospital Predischarge", "Post PCI"
            ));
            ArrayList<String> details = new ArrayList<>(Arrays.asList(
                    patient.getName(),
                    patient.getUHID(),
                    String.valueOf(patient.getAge()),
                    patient.getSex(),
                    patient.getRiskFactors(),
                    patient.getOtherComorbidities(),
                    patient.getCad(),
                    patient.getTreatmentForPastCad(),
                    patient.getEcho(),
                    patient.getCurrentDiagnosis(),
                    patient.getCoronaryAngiography(),
                    patient.getPci(),
                    patient.getComplicationsInHospitalPredischarge(),
                    patient.getPostPci()
            ));

            patientDetails.getItems().clear();
            for (int row = 0; row < titles.size(); row++) {
                patientDetails.getItems().add(new PatientData(titles.get(row), details.get(row)));
            }
        } else {
            patientDetails.getItems().clear();
        }
        patientDetails.refresh();
    }


    @Override
    public void deletePatient(ListView<Patient> patientList) {
        Patient patient = patientList.getSelectionModel().getSelectedItem();

        if (!(patient == null)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Patient");
            alert.setHeaderText("Remove Patient: " + patient.getName());
            alert.setContentText("Are you sure?  Press OK to confirm, or cancel to Back out.");

            Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(PatientRepository.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

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

    @Override
    public void printAllPatientDetails() {
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

        } catch (IOException | PrintException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printPatientDetails(ArrayList<String> patient) {

        XWPFDocument doc = new XWPFDocument();
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setTop(BigInteger.valueOf(500L));
        pageMar.setBottom(BigInteger.valueOf(0L));
        try {
            XWPFParagraph title = doc.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = title.createRun();

            String imgFile = System.getProperty("user.dir") + "/src/hospital/resources/images/clg-logo.png";
            FileInputStream is;
            is = new FileInputStream(imgFile);
            run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(450), Units.toEMU(90)); // 200x200 pixels
            run.addBreak();
            run.addBreak();
            is.close();

            XWPFTable tab = doc.createTable();

            CTTblWidth width = tab.getCTTbl().addNewTblPr().addNewTblW();
            width.setType(STTblWidth.DXA);
            width.setW(BigInteger.valueOf(9072));

            tab.setInsideHBorder(XWPFTable.XWPFBorderType.NIL, 0, 0, "ffffff");
            tab.setInsideVBorder(XWPFTable.XWPFBorderType.NIL, 0, 0, "ffffff");

            tab.getCTTbl().getTblPr().getTblBorders().getLeft().setVal(STBorder.NONE);
            tab.getCTTbl().getTblPr().getTblBorders().getRight().setVal(STBorder.NONE);
            tab.getCTTbl().getTblPr().getTblBorders().getTop().setVal(STBorder.NONE);
            tab.getCTTbl().getTblPr().getTblBorders().getBottom().setVal(STBorder.NONE);

            XWPFTableRow row;

            try (OutputStream os = new FileOutputStream("document.docx")) {

                for (int i = 0; i < DropdownData.titles.size(); i++) {

                    row = tab.insertNewTableRow(i); // Second Row
                    row.setHeight(800);

                    XWPFRun rowRun = row.addNewTableCell().addParagraph().createRun();
                    row.getCell(0).removeParagraph(0);
                    rowRun.setBold(true);
                    rowRun.setText(DropdownData.titles.get(i));

                    rowRun = row.addNewTableCell().addParagraph().createRun();
                    row.getCell(1).removeParagraph(0);
                    rowRun.setBold(false);
                    rowRun.setFontSize(12);
                    rowRun.setText(patient.get(i));

                }

                doc.write(os);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
    }
<<<<<<< HEAD

=======
<<<<<<< HEAD

    public void printHtmlPage () throws IOException {
        PrintableEditorPane jEditorPane = new PrintableEditorPane();

        jEditorPane.setContentType("text/html");
        jEditorPane.read(new BufferedReader(new FileReader("document.html")), ""); /* Read your HTML File
        */

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(jEditorPane);
        if (job.printDialog()) {/* Displays the standard system print dialog
        */
            try{
                job.print();
            }
            catch (Exception ex){
                System.out.println(ex);
            }
        }
    }

=======
>>>>>>> 0fb1a35a8d899d110356ec7e4a9a5ae2369808dc
>>>>>>> 84610caf0a857f9de85099c754aeb1ee18480cef
}
