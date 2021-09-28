package hospital.java.repositories.patient_repository;

import com.opencsv.CSVWriter;
import com.spire.doc.TableRow;
import com.spire.doc.*;
import com.spire.doc.documents.*;
import com.spire.doc.fields.DocPicture;
import com.spire.doc.fields.TextRange;
import hospital.java.controllers.AllPatientsController;
import hospital.java.controllers.FormController;
import hospital.java.controllers.PrintDialogController;
import hospital.java.helpers.PrintableBook;
import hospital.java.models.CagModel;
import hospital.java.models.Patient;
import hospital.java.models.PatientData;
import hospital.java.models.PciModel;
import hospital.java.sources.Datasource;
import hospital.java.sources.PageSource;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class PatientRepository {

    public static String[] toStringArray(ArrayList<String> arr) {

        String[] str = new String[arr.size()];
        for (int j = 0; j < arr.size(); j++) {
            str[j] = arr.get(j);
        }

        return str;
    }

    public boolean customPrintDialog(ArrayList<String> values, ArrayList<String> keys, Window window, ArrayList<ArrayList<String>> summaryKeys, String title) throws IOException {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(window);
        dialog.setTitle(title == null ? "Print" : title);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AllPatientsController.class.getResource("/hospital/resources/views/printDialog.fxml"));

        dialog.getDialogPane().setContent(fxmlLoader.load());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().add(new Image(Objects.requireNonNull(
                PatientRepository.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

        PrintDialogController controller = fxmlLoader.getController();
        controller.setData(values, keys, summaryKeys);

        Optional<ButtonType> result = dialog.showAndWait();

        return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }

    public void editPatient(Window window, URL url, ListView<Patient> patientList,
                            TableView<PatientData> patientDetails, TableView<Patient> patientTable) throws IOException {
        PageSource.form.setEditing(true);
        Patient patient = null;

        if (patientList != null)
            patient = patientList.getSelectionModel().getSelectedItem();
        if (patientTable != null)
            patient = patientTable.getSelectionModel().getSelectedItem();


        if (!(patient == null)) {
            int patientId = patient.getId();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(window);
            dialog.setTitle("Edit Patient");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);

            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(
                    PatientRepository.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

            FormController controller = fxmlLoader.getController();

            CagModel cag = Datasource.getCags().get(patient.getId());
            PciModel pci = Datasource.getPcis().get(patient.getId());

            PageSource.editForm.setPatient(patient);
            PageSource.editForm.setCag(cag);
            PageSource.editForm.setPci(pci);

            Optional<ButtonType> result = dialog.showAndWait();
            dialog.setOnCloseRequest(e -> {
                PageSource.form.setEditing(false);
                PageSource.editForm.reset();
            });

            if (result.isPresent() && result.get() == ButtonType.OK) {


                Patient editedPatient = controller.getEditedPatient();
                CagModel editedCag = controller.getEditedCag();
                PciModel editedPci = controller.getEditedPci();

                patient.setId(patientId);

                patient.setName(editedPatient.getName());
                patient.setUHID(editedPatient.getUHID());
                patient.setAge(editedPatient.getAge());
                patient.setSex(editedPatient.getSex());
                patient.setComplaints(editedPatient.getComplaints());
                patient.setComplaintsField(editedPatient.getComplaintsField());
                patient.setComplaintsAdditional(editedPatient.getComplaintsAdditional());
                patient.setRiskFactors(editedPatient.getRiskFactors());
                patient.setRiskFactorsField(editedPatient.getRiskFactorsField());
                patient.setRiskFactorsAdditional(editedPatient.getRiskFactorsAdditional());
                patient.setOtherComorbidities(editedPatient.getOtherComorbidities());
                patient.setOtherComorbiditiesField(editedPatient.getOtherComorbiditiesField());
                patient.setOtherComorbiditiesAdditional(editedPatient.getOtherComorbiditiesAdditional());
                patient.setCad(editedPatient.getCad());
                patient.setPastCadField(editedPatient.getPastCadField());
                patient.setPastCadAdditional(editedPatient.getPastCadAdditional());
                patient.setTreatmentForPastCad(editedPatient.getTreatmentForPastCad());
                patient.setTreatmentPastCadField(editedPatient.getTreatmentPastCadField());
                patient.setTreatmentPastCadAdditional(editedPatient.getTreatmentPastCadAdditional());
                patient.setEcho(editedPatient.getEcho());
                patient.setEchoField(editedPatient.getEchoField());
                patient.setEchoAdditional(editedPatient.getEchoAdditional());
                patient.setCurrentDiagnosis(editedPatient.getCurrentDiagnosis());
                patient.setCurrentDiagnosisField(editedPatient.getCurrentDiagnosisField());
                patient.setCurrentDiagnosisAdditional(editedPatient.getCurrentDiagnosisAdditional());
                patient.setComplicationsInHospitalPredischarge(editedPatient.getComplicationsInHospitalPredischarge());
                patient.setComplicationsField(editedPatient.getComplicationsField());
                patient.setComplicationsAdditional(editedPatient.getComplicationsAdditional());
                patient.setOperator(editedPatient.getOperator());
                patient.setDate(editedPatient.getDate());
//                patient.setDateOfProcedure(editedPatient.getDateOfProcedure());
                patient.setAdditionalDetails(editedPatient.getAdditionalDetails());

                patient.setHasCag(editedPatient.isHasCag());
                patient.setHasPci(editedPatient.isHasPci());

                patient.setHasLmca(editedPatient.isHasLmca());
                patient.setHasBifurication(editedPatient.isHasBifurication());

                patient.setHasStentLad(editedPatient.isHasStentLad());
                patient.setHasStentLcx(editedPatient.isHasStentLcx());
                patient.setHasStentRca(editedPatient.isHasStentRca());
                patient.setHasStentLmca(editedPatient.isHasStentLmca());
                patient.setHasStentDiagonal(editedPatient.isHasStentDiagonal());
                patient.setHasStentOm(editedPatient.isHasStentOm());

                cag.setAccessRoute(editedCag.getAccessRoute());
                cag.setAccessRouteField(editedCag.getAccessRouteField());
                cag.setAccessRouteAdditional(editedCag.getAccessRouteAdditional());
                cag.setBp_1(editedCag.getBp_1());
                cag.setBp_2(editedCag.getBp_2());
                cag.setBp_3(editedCag.getBp_3());
                cag.setHr(editedCag.getHr());
                cag.setLmca(editedCag.getLmca());
                cag.setLad(editedCag.getLad());
                cag.setD1(editedCag.getD1());
                cag.setLcx(editedCag.getLcx());
                cag.setOm(editedCag.getOm());
                cag.setRca(editedCag.getRca());
                cag.setDiagnosis(editedCag.getDiagnosis());
                cag.setBifurcation(editedCag.getBifurcation());
                cag.setCalcification(editedCag.getCalcification());
                cag.setRestenosis(editedCag.getRestenosis());
                cag.setRecommendation(editedCag.getRecommendation());
                cag.setLmcaDisease(editedCag.getLmcaDisease());
                cag.setLmcaDiseaseField(editedCag.getLmcaDiseaseField());
                cag.setBipurificationDisease(editedCag.getBipurificationDisease());
                cag.setBipurificationDiseaseField(editedCag.getBipurificationDiseaseField());
                cag.setMiscellaneous(editedCag.getMiscellaneous());
                cag.setFinalImpression(editedCag.getFinalImpression());
                cag.setDateOfProcedure(editedCag.getDateOfProcedure());
                cag.setAdditionalDetails(editedCag.getAdditionalDetails());

                pci.setAccessRoute(editedPci.getAccessRoute());
                pci.setAccessRouteField(editedPci.getAccessRouteField());
                pci.setAccessRouteAdditional(editedPci.getAccessRouteAdditional());
                pci.setBp_1(editedPci.getBp_1());
                pci.setBp_2(editedPci.getBp_2());
                pci.setBp_3(editedPci.getBp_3());
                pci.setHr(editedPci.getHr());
                pci.setCatheter(editedPci.getCatheter());
                pci.setWire(editedPci.getWire());
                pci.setThrombus(editedPci.getThrombus());
                pci.setPredilation(editedPci.getPredilation());
                pci.setStent(editedPci.getStent());
                pci.setStentField(editedPci.getStentField());
                pci.setStentAdditional(editedPci.getStentAdditional());
                pci.setPostDilation(editedPci.getPostDilation());
                pci.setTimiFlow(editedPci.getTimiFlow());
                pci.setTimeFirstMedical(editedPci.getTimeFirstMedical());
                pci.setDbt(editedPci.getDbt());
                pci.setComplications(editedPci.getComplications());
                pci.setBifurcation(editedPci.getBifurcation());
                pci.setLmca(editedPci.getLmca());
                pci.setSpecialBalloons(editedPci.getSpecialBalloons());
                pci.setGuideCatheter(editedPci.getGuideCatheter());
                pci.setIvusFindings(editedPci.getIvusFindings());
                pci.setOctFindings(editedPci.getOctFindings());
                pci.setRotablation(editedPci.getRotablation());
                pci.setLmcaDisease(editedPci.getLmcaDisease());
                pci.setLmcaDiseaseField(editedPci.getLmcaDiseaseField());
                pci.setBipurificationDisease(editedPci.getBipurificationDisease());
                pci.setBipurificationDiseaseField(editedPci.getBipurificationDiseaseField());
                pci.setSyntaxScore(editedPci.getSyntaxScore());
                pci.setFinalImpression(editedPci.getFinalImpression());
                pci.setDateOfProcedure(editedPci.getDateOfProcedure());
                pci.setAdditionalDetails(editedPci.getAdditionalDetails());

                Patient finalPatient = patient;
                Task<Boolean> task = new Task<>() {
                    @Override
                    protected Boolean call() {
                        return Datasource.instance.updatePatient(finalPatient, cag, pci);
                    }
                };

                ArrayList<String> values = new ArrayList<>();
                values.addAll(patient.getValues());
                values.addAll(Datasource.getCags().get(patient.getId()).getValues(false));
                values.addAll(Datasource.getPcis().get(patient.getId()).getValues(false));

                ArrayList<String> keys = new ArrayList<>();
                keys.addAll(patient.getKeys());
                keys.addAll(Datasource.getCags().get(patient.getId()).getKeys(false));
                keys.addAll(Datasource.getPcis().get(patient.getId()).getKeys(false));

                updateTableRow(values, keys, patientDetails, patient.getValues().size());
                task.setOnSucceeded(e -> {
                    if (patientList != null)
                        patientList.refresh();
                    if (patientTable != null)
                        patientTable.refresh();
                });

                new Thread(task).start();

                PageSource.form.setEditing(false);
                PageSource.editForm.reset();

            }

        }
    }

    public void updateTableRow(ArrayList<String> values, ArrayList<String> keys, TableView<PatientData> patientDetails, int size) {
        if (values != null) {
            if (patientDetails != null) {
                patientDetails.getItems().clear();
                for (int row = 0; row < keys.size(); row++) {
                    if (row < size || values.get(row) != null && !values.get(row).trim().equals(""))
                        if (keys.get(row) != null && !keys.get(row).trim().equals(""))
                            patientDetails.getItems().add(new PatientData(keys.get(row), values.get(row), null, null, null, null, null, null));
                }
            }
        } else {
            if (patientDetails != null)
                patientDetails.getItems().clear();
        }
        if (patientDetails != null) {
            patientDetails.refresh();
        }
    }

    public void deletePatient(ListView<Patient> patientList, TableView<Patient> patientTable) {

        Patient patient = null;
        if (patientList != null)
            patient = patientList.getSelectionModel().getSelectedItem();
        if (patientTable != null)
            patient = patientTable.getSelectionModel().getSelectedItem();

        if (!(patient == null)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Patient");
            alert.setHeaderText("Remove Patient: " + patient.getName());
            alert.setContentText("Are you sure?  Press OK to confirm, or cancel to Back out.");

            Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(
                    PatientRepository.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                Patient finalPatient = patient;
                Task<Boolean> task = new Task<>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return Datasource.instance.deletePatient(finalPatient.getId());
                    }
                };

                Patient finalPatient1 = patient;
                task.setOnSucceeded(e -> {
                    Datasource.getPatients().remove(finalPatient1);

                    if (patientList != null)
                        patientList.refresh();
                    if (patientTable != null)
                        patientTable.refresh();
                });

                new Thread(task).start();
            }
        }
    }

    public void printAllDetails(Patient patient, CagModel cag, PciModel pci) {
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {

                writeToDocx(patient, patient.getPrintKeys(), patient.getPrintValues(), "document1.docx", false, 1, patient.getDate(), "Department of Cardiology", "PATIENT DETAILS");
                boolean cagExists = patient.isHasCag();
                boolean pciExists = patient.isHasPci();

                if (cagExists) {
                    writeToDocx(patient, cag.getKeys(true), cag.getValues(true), "document2.docx", true, 2, cag.getDateOfProcedure(), "CARDIAC CATHETERIZATION AND CORONARY ANGIOGRAM REPORT");

                }
                if (pciExists) {
                    writeToDocx(patient, pci.getKeys(true), pci.getValues(true), "document3.docx", true, 3, pci.getDateOfProcedure(), "ANGIOPLASTY NOTE");
                }
                printAllDocx(cagExists, pciExists);
                return true;
            }
        };
        new Thread(task).start();
    }

    public void printPatientDetails(Patient patient, ArrayList<String> keys, ArrayList<String> values, String title, boolean hasFullDetails, int page, LocalDate date, CagModel cag, PciModel pci)
            throws InvalidFormatException, IOException, PrinterException {
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                writeToDocx(patient, keys, values, "document.docx", hasFullDetails, page, date, title);
                printDocx("document.docx");
                return true;
            }
        };
        new Thread(task).start();
    }

    public void getCSV(ArrayList<ArrayList<String>> summaryKeys, String[] header, String filename, String... titles) throws IOException {

        FileWriter csvWriter = new FileWriter(filename);

        CSVWriter writer = new CSVWriter(csvWriter);

        for (String title : titles) {
            writer.writeNext(new String[]{"", title});
        }

        if (titles.length != 0)
            writer.writeNext(new String[]{""});

        writer.writeNext(header);

        for (ArrayList<String> value : summaryKeys)
            writer.writeNext(toStringArray(value));

        writer.close();

        File file = new File(System.getProperty("user.dir"));

        if (!Desktop.isDesktopSupported()) {
            System.out.println("Desktop is not supported");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) desktop.open(file);

    }

    public void writeToDocx(Patient patient, ArrayList<String> keys, ArrayList<String> values, String filename, boolean hasFullDetails, int page, LocalDate date, String... titles) {
        try {
            Document doc = new Document();
            Section section = doc.addSection();

            ParagraphStyle style = new ParagraphStyle(doc);
            style.setName("titleStyle");
            style.getCharacterFormat().setBold(true);
            doc.getStyles().add(style);


            ParagraphStyle fontTitleStyle = new ParagraphStyle(doc);
            fontTitleStyle.setName("fontTitleStyle");
            fontTitleStyle.getCharacterFormat().setFontSize(17);
            fontTitleStyle.getCharacterFormat().setBold(true);
            doc.getStyles().add(fontTitleStyle);

            ParagraphStyle fontDetailsStyle = new ParagraphStyle(doc);
            fontDetailsStyle.setName("fontDetailsStyle");
            fontDetailsStyle.getCharacterFormat().setFontSize(17);
            doc.getStyles().add(fontDetailsStyle);

            ParagraphStyle fontUnderlinedStyle = new ParagraphStyle(doc);
            fontUnderlinedStyle.setName("fontUnderlinedStyle");
            fontUnderlinedStyle.getCharacterFormat().setFontSize(17);
            fontUnderlinedStyle.getCharacterFormat().setBold(true);
            fontUnderlinedStyle.getCharacterFormat().setUnderlineStyle(UnderlineStyle.Dash);
            doc.getStyles().add(fontUnderlinedStyle);

            boolean changeFontSize = false;

            if (keys == null)
                addImage(section, false);
            else if (keys.size() < 19)
                addImage(section, false);
            else if (keys.size() < 23) {
                changeFontSize = true;
                addImage(section, true);
            } else {
                changeFontSize = true;
                addImage(section, true);
            }
            for (String title : titles)
                addHeading(section, title);
            addHorizontalTable(section, patient, page, date);

                addVerticalTable(section, keys, values, hasFullDetails, changeFontSize);

            File path = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\vmc\\");
            if (!path.exists()) {
                //noinspection ResultOfMethodCallIgnored
                path.mkdir();
            }
            doc.saveToFile("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\vmc\\" + filename, FileFormat.Docx_2013);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addImage(Section section, boolean isSmall) {
        Paragraph paragraph = section.addParagraph();
        InputStream is = PatientRepository.class.getResourceAsStream("/hospital/resources/images/clg-logo.png");
        DocPicture picture = paragraph.appendPicture(is);
        if (isSmall) {
            picture.setWidth(200f);
            picture.setHeight(40f);
        } else {
            picture.setWidth(300f);
            picture.setHeight(60f);
        }
        paragraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
        paragraph.getFormat().setAfterSpacing(15f);
    }

    private void addHeading(Section section, String title) {
        Paragraph heading = section.addParagraph();
        TextRange tr = heading.appendText(title);

        heading.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
        heading.getFormat().setAfterSpacing(8f);

        tr.getCharacterFormat().setBold(true);
    }

    private void addHorizontalTable(Section section, Patient patient, int page, LocalDate date) {
//        try {
        Table hTable = section.addTable(false);
        hTable.setColumnWidth(new float[]{60f, 110f, 40f, 70f, 100f, 70f});
        hTable.getTableFormat().setHorizontalAlignment(RowAlignment.Center);

        hTable.resetCells(2, 6);

        hTable.applyVerticalMerge(0, 0, 1);
        hTable.applyVerticalMerge(1, 0, 1);

        TableRow row = hTable.getRows().get(0);


        Paragraph p = row.getCells().get(0).addParagraph();
        row.getCells().get(0).getCellFormat().setBackColor(Color.lightGray);
        TextRange txtRange;
        txtRange = p.appendText("Patient name");
        txtRange.getCharacterFormat().setBold(true);

        p = row.getCells().get(1).addParagraph();
        txtRange = p.appendText(patient.getValues().get(0));
        txtRange.getCharacterFormat().setBold(true);

        p = row.getCells().get(2).addParagraph();
        row.getCells().get(2).getCellFormat().setBackColor(Color.lightGray);
        txtRange = p.appendText("Age");
        txtRange.getCharacterFormat().setBold(true);

        p = row.getCells().get(3).addParagraph();
        row.getCells().get(3).getCellFormat().setBackColor(Color.lightGray);
        txtRange = p.appendText("Sex");
        txtRange.getCharacterFormat().setBold(true);

        p = row.getCells().get(4).addParagraph();
        row.getCells().get(4).getCellFormat().setBackColor(Color.lightGray);
        txtRange = p.appendText("UHID");
        txtRange.getCharacterFormat().setBold(true);

        p = row.getCells().get(5).addParagraph();
        row.getCells().get(5).getCellFormat().setBackColor(Color.lightGray);
        if (page != 1)
            txtRange = p.appendText("DOP");
        else
            txtRange = p.appendText("DOA");

        txtRange.getCharacterFormat().setBold(true);

        row = hTable.getRows().get(1);
        p = row.getCells().get(2).addParagraph();
        p.appendText(patient.getValues().get(2));
        p = row.getCells().get(3).addParagraph();
        p.appendText(patient.getValues().get(3));
        p = row.getCells().get(4).addParagraph();
        p.appendText(patient.getValues().get(1));
        p = row.getCells().get(5).addParagraph();
        p.appendText(date==null ? "" : date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        Paragraph paragraph = section.addParagraph();
        paragraph.getFormat().setAfterSpacing(5f);
    }

    private void addVerticalTable(Section section, ArrayList<String> keys, ArrayList<String> values, boolean isFirst, boolean changeFontSize) {
        try {
            Table table = section.addTable(false);
            table.setColumnWidth(new float[]{150f, 300f});
            table.getTableFormat().setHorizontalAlignment(RowAlignment.Center);
            table.resetCells(keys.size(), 2);
            table.getTableFormat().getBorders().setBorderType(BorderStyle.Cleared);

            int rowIndex = 0;
            for (int r = 0; r < keys.size(); r++) {
                if (!isFirst && (keys.get(r) == null || keys.get(r).trim().isEmpty())) {
                    continue;
                }

                if (isFirst && (values.get(r) == null || values.get(r).trim().isEmpty())) {
                    continue;
                }
                TableRow dataRow = table.getRows().get(rowIndex);
                dataRow.setHeightType(TableRowHeightType.Auto);
                dataRow.getRowFormat().setBackColor(Color.white);
                dataRow.getCells().get(0).getCellFormat().setVerticalAlignment(VerticalAlignment.Top);
                Paragraph tbcp = dataRow.getCells().get(0).addParagraph();
                tbcp.applyStyle("titleStyle");

                tbcp.appendText(keys.get(r) + "\n");

                dataRow.getCells().get(1).getCellFormat().setVerticalAlignment(VerticalAlignment.Top);

                Paragraph detailsParagraph = dataRow.getCells().get(1).addParagraph();
                detailsParagraph.appendText(values.get(r));

                if ( changeFontSize )
                    detailsParagraph.getStyle().getCharacterFormat().setFontSize(10);

                rowIndex++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printDocx(String filename) throws PrinterException {
        Document document = new Document();
        document.loadFromFile("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\vmc\\" + filename);

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.defaultPage();
        Paper paper = pageFormat.getPaper();
        paper.setImageableArea(0, 0, pageFormat.getWidth(), pageFormat.getHeight());
        pageFormat.setPaper(paper);
        printerJob.setPrintable(document, pageFormat);
        if (printerJob.printDialog()) {
            printerJob.print();
        }
    }

    public void printAllDocx(boolean cagExists, boolean pciExists) {
        try {
            Document document1 = new Document();
            document1.loadFromFile("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\vmc\\" + "document1.docx");

            Document document2 = new Document();
            if (cagExists) {
                document2.loadFromFile("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\vmc\\" + "document2.docx");
            }
            Document document3 = new Document();

            if (pciExists) {
                document3.loadFromFile("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\vmc\\" + "document3.docx");
            }

            PrinterJob printerJob = PrinterJob.getPrinterJob();
            PageFormat pageFormat = printerJob.defaultPage();
            Paper paper = pageFormat.getPaper();
            paper.setImageableArea(0, 0, pageFormat.getWidth(), pageFormat.getHeight());
            pageFormat.setPaper(paper);

            PrintableBook book = new PrintableBook();
            book.add(document1, pageFormat);
            if (cagExists)
                book.add(document2, pageFormat);
            if (pciExists)
                book.add(document3, pageFormat);

            printerJob.setPrintable(book, pageFormat);
            if (printerJob.printDialog()) {
                printerJob.print();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

