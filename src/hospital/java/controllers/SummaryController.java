package hospital.java.controllers;

import hospital.java.models.AutoComplete;
import hospital.java.models.Patient;
import hospital.java.repositories.patient_repository.PatientRepository;
import hospital.java.sources.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class SummaryController {

    PatientRepository patientRepository = new PatientRepository();

    @FXML
    private Label count,
            procedureCount;

    @FXML
    private ComboBox<String> procedureBox,
            stentBox,
            genderBox,
            ageBox,
            doctorBox;

    @FXML
    private DatePicker fromDate, toDate;
    @FXML
    private TextField nameField;

    @FXML
    private TableView<Patient> patientDetails;
    @FXML
    private ContextMenu listContextMenu;

    void viewPatient() {
        Patient patient = patientDetails.getSelectionModel().getSelectedItem();

        ArrayList<String> values = new ArrayList<>(patient.getValues());
        values.addAll(
                Datasource
                        .getCags()
                        .get(patient.getId()
                        ).getValues(false));
        values.addAll(Datasource.getPcis().get(patient.getId()).getValues(false));
        values.addAll(Datasource.getPcis().get(patient.getId()).getValues(false));

        ArrayList<String> keys = new ArrayList<>();
        keys.addAll(patient.getKeys());
        keys.addAll(Datasource.getCags().get(patient.getId()).getKeys(false));
        keys.addAll(Datasource.getPcis().get(patient.getId()).getKeys(false));

        try {
            patientRepository.customPrintDialog(values, keys, patientDetails.getScene().getWindow(), null, patient.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void printAll() {
        ArrayList<ArrayList<String>> summaryKeys = new ArrayList<>();
        for (int i = 0; i < Datasource.getFilteredPatients().size(); i++) {
            ArrayList<String> patientData = new ArrayList<>(Arrays.asList(
                    String.valueOf(i + 1),
                    Datasource.getFilteredPatients().get(i).getName(),
                    String.valueOf(Datasource.getFilteredPatients().get(i).getAge()),
                    Datasource.getFilteredPatients().get(i).getSex(),
                    Datasource.getFilteredPatients().get(i).getUHID(),
                    Datasource.getFilteredPatients().get(i).getOperator()
            ));
            summaryKeys.add(patientData);
        }

        String[] header = {"S.No.", "Name", "Age", "Sex", "UHID", "Operator"};

        new Thread(() -> {
            try {

                String title = "CAG list ";
                if (fromDate.getValue() == null && toDate.getValue() == null) {
                    title += "overall";
                } else if (fromDate.getValue() != null && toDate.getValue() == null) {
                    title += "from ";
                    title += fromDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } else if (fromDate.getValue() == null && toDate.getValue() != null) {
                    title += "until ";
                    title += toDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } else {
                    title += "from ";
                    title += fromDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    title += "to ";
                    title += toDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                }

                patientRepository.getCSV(summaryKeys, header, "All patients.csv", "Department of Cardiology", title);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    void printPatient() {

        Patient patient = patientDetails.getSelectionModel().getSelectedItem();

        ArrayList<String> values = new ArrayList<>(patient.getValues());
        values.addAll(Datasource.getCags().get(patient.getId()).getValues(true));
        values.addAll(Datasource.getPcis().get(patient.getId()).getValues(true));

        ArrayList<String> keys = new ArrayList<>(patient.getKeys());
        keys.addAll(Datasource.getCags().get(patient.getId()).getKeys(true));
        keys.addAll(Datasource.getPcis().get(patient.getId()).getKeys(true));

        try {
            if (!patientRepository.customPrintDialog(values, keys, patientDetails.getScene().getWindow(), null, null)) {
                return;
            }

            new Thread(() -> patientRepository.printAllDetails(patient, Datasource.getCags().get(patient.getId()), Datasource.getPcis().get(patient.getId()))).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void customPrint(int mode) {

        Patient patient = patientDetails.getSelectionModel().getSelectedItem();
        ArrayList<String> values = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();

        if (mode == 1) {
            values.addAll(patient.getValues());
            keys.addAll(patient.getKeys());
        } else if (mode == 2) {
            values.add(patient.getName());
            values.add(patient.getUHID());
            values.add(String.valueOf(patient.getAge()));
            values.add(patient.getSex());
            values.add(patient.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            values.addAll(Datasource.getCags().get(patient.getId()).getValues(true));

            keys.add("Name");
            keys.add("UHID");
            keys.add("Age");
            keys.add("Sex");
            keys.add("Date");
            keys.addAll(Datasource.getCags().get(patient.getId()).getKeys(true));
        } else {
            values.add(patient.getName());
            values.add(patient.getUHID());
            values.add(String.valueOf(patient.getAge()));
            values.add(patient.getSex());
            values.add(patient.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            values.addAll(Datasource.getPcis().get(patient.getId()).getValues(true));

            keys.add("Name");
            keys.add("UHID");
            keys.add("Age");
            keys.add("Sex");
            keys.add("Date");
            keys.addAll(Datasource.getPcis().get(patient.getId()).getKeys(true));
        }

        try {
            if (!patientRepository.customPrintDialog(values, keys, patientDetails.getScene().getWindow(), null, null)) {
                return;
            }
            new Thread(() -> {
                try {
                    if (mode == 1) {
                        patientRepository.printPatientDetails(patient, null, null, "PATIENT DETAILS", false, 1, patient.getDate(), null, null);
                    } else if (mode == 2) {
                        patientRepository.printPatientDetails(patient, Datasource.getCags().get(patient.getId()).getKeys(true), Datasource.getCags().get(patient.getId()).getValues(true), "CARDIAC CATHETERIZATION AND CORONARY ANGIOGRAM REPORT", true, 2, Datasource.getCags().get(patient.getId()).getDateOfProcedure(), Datasource.getCags().get(patient.getId()), null);

                    } else {
                        patientRepository.printPatientDetails(patient, Datasource.getPcis().get(patient.getId()).getKeys(true), Datasource.getPcis().get(patient.getId()).getValues(true), "ANGIOPLASTY NOTE", true, 3, Datasource.getPcis().get(patient.getId()).getDateOfProcedure(), null, Datasource.getPcis().get(patient.getId()));
                    }
                } catch (InvalidFormatException | IOException | PrinterException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deletePatient() {
        patientRepository.deletePatient(null, patientDetails);
        search();
    }

    @FXML
    void editPatient() {

        try {
            patientRepository.editPatient(patientDetails.getScene().getWindow(),
                    AllPatientsController.class.getResource("/hospital/resources/views/form.fxml"),
                    null, null, patientDetails);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void

    updateTableRow(ArrayList<Patient> patients, TableView<Patient> patientDetails) {
        if (patients.size() != 0) {
            if (patientDetails != null) {
                patientDetails.getItems().clear();
                for (int i = 0; i < patients.size(); i++) {
                    patients.get(i).setSno(String.valueOf(i + 1));
                    patientDetails.getItems().add(patients.get(i));
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

    public void refresh() {
        doctorBox.getItems().clear();

        doctorBox.getItems().add("");
        doctorBox.getSelectionModel().selectFirst();
        doctorBox.getItems().addAll(DropdownData.getOperatorList());
    }

    public void initialize() {
        TableColumn<Patient, String> column1 = new TableColumn<>("S.No.");
        column1.setCellValueFactory(new PropertyValueFactory<>("sno"));
        column1.setPrefWidth(40);
        TableColumn<Patient, String> column2 = new TableColumn<>("Patient name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        column2.setPrefWidth(150);
        TableColumn<Patient, String> column3 = new TableColumn<>("Age");
        column3.setCellValueFactory(new PropertyValueFactory<>("age"));
        column3.setPrefWidth(40);
        TableColumn<Patient, String> column4 = new TableColumn<>("Sex");
        column4.setCellValueFactory(new PropertyValueFactory<>("sex"));
        column4.setPrefWidth(60);
        TableColumn<Patient, String> column5 = new TableColumn<>("Uhid");
        column5.setCellValueFactory(new PropertyValueFactory<>("UHID"));
        column5.setPrefWidth(150);
        TableColumn<Patient, String> column6 = new TableColumn<>("Operator");
        column6.setCellValueFactory(new PropertyValueFactory<>("operator"));
        column6.setPrefWidth(150);

        patientDetails.getColumns().add(column1);
        patientDetails.getColumns().add(column2);
        patientDetails.getColumns().add(column3);
        patientDetails.getColumns().add(column4);
        patientDetails.getColumns().add(column5);
        patientDetails.getColumns().add(column6);

        procedureBox.getItems().add("");
        procedureBox.getSelectionModel().selectFirst();
        procedureBox.getItems().addAll(DropdownData.procedureList);
        AutoComplete.autoCompleteComboBoxPlus(procedureBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

        stentBox.getItems().add("");
        stentBox.getSelectionModel().selectFirst();
        stentBox.getItems().addAll(DropdownData.stentList);
        AutoComplete.autoCompleteComboBoxPlus(stentBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

        genderBox.getItems().add("");
        genderBox.getSelectionModel().selectFirst();
        genderBox.getItems().addAll(DropdownData.sexList);
        AutoComplete.autoCompleteComboBoxPlus(genderBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

        ageBox.getItems().add("");
        ageBox.getSelectionModel().selectFirst();
        ageBox.getItems().addAll(DropdownData.ageList);
        AutoComplete.autoCompleteComboBoxPlus(ageBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

        doctorBox.getItems().add("");
        doctorBox.getSelectionModel().selectFirst();
        doctorBox.getItems().addAll(DropdownData.getOperatorList());
        AutoComplete.autoCompleteComboBoxPlus(doctorBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));


        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(actionEvent -> deletePatient());

        MenuItem editMenuItem = new MenuItem("Edit");
        editMenuItem.setOnAction(actionEvent -> editPatient());

        MenuItem viewMenuItem = new MenuItem("View");
        viewMenuItem.setOnAction(actionEvent -> viewPatient());

        MenuItem printMenuItem = new MenuItem("Print all");
        printMenuItem.setOnAction(actionEvent -> printPatient());

        MenuItem printDetailsMenuItem = new MenuItem("Print details");
        printDetailsMenuItem.setOnAction(actionEvent -> customPrint(1));

        MenuItem printCagMenuItem = new MenuItem("Print CAG");
        printCagMenuItem.setOnAction(actionEvent -> customPrint(2));

        MenuItem printPciMenuItem = new MenuItem("Print PCI");
        printPciMenuItem.setOnAction(actionEvent -> customPrint(3));

        listContextMenu.getItems().addAll(viewMenuItem, editMenuItem, printMenuItem, printDetailsMenuItem, printCagMenuItem, printPciMenuItem, deleteMenuItem);

        patientDetails.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        patientDetails.getSelectionModel().selectFirst();

        patientDetails.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if (t.getButton() == MouseButton.SECONDARY) {
                listContextMenu.show(patientDetails, t.getScreenX(), t.getScreenY());
            }
        });

    }

    public void clear() {
        this.fromDate.setValue(null);
        this.toDate.setValue(null);
        this.procedureBox.getSelectionModel().selectFirst();
        this.stentBox.getSelectionModel().selectFirst();
        this.genderBox.getSelectionModel().selectFirst();
        this.ageBox.getSelectionModel().selectFirst();
        this.doctorBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void search() {

//        SELECT * FROM patients
//        WHERE doa = '1627842600000' ;

//        SELECT * FROM patients
//        WHERE doa BETWEEN '1625423400000' AND '1627842600000';

//        SELECT * FROM patients
//        WHERE has_cag = 1;

//        SELECT * FROM patients
//        WHERE patients.has_cag = 1 AND patients.has_pci = 1;

//        Date vFromDate = fromDate;
//        Date vToDate = toDate;

//        Date.valueOf(fromDate.getValue());

//        SELECT * FROM patients WHERE name like '%sre%';

        LocalDate vFromDate = fromDate.getValue();
        LocalDate vToDate = toDate.getValue();

        String vProcedure = procedureBox.getSelectionModel().getSelectedItem();
        String vStent = stentBox.getSelectionModel().getSelectedItem();
        String vGender = genderBox.getSelectionModel().getSelectedItem();
        String vAge = ageBox.getSelectionModel().getSelectedItem();
        String vDoctor = doctorBox.getSelectionModel().getSelectedItem();

        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        query.append(PatientTable.TABLE_PATIENTS);
        query.append(" JOIN ");
        query.append(CagTable.TABLE_CAG);
        query.append(" ON ");
        query.append(PatientTable.TABLE_PATIENTS).append(".").append(PatientTable.COLUMN_PATIENT_ID);
        query.append(" = ");
        query.append(CagTable.TABLE_CAG).append(".").append(CagTable.COLUMN_CAG_ID);
        query.append(" JOIN ");
        query.append(PciTable.TABLE_PCI);
        query.append(" ON ");
        query.append(PatientTable.TABLE_PATIENTS).append(".").append(PatientTable.COLUMN_PATIENT_ID);
        query.append(" = ");
        query.append(PciTable.TABLE_PCI).append(".").append(PciTable.COLUMN_PCI_ID);
//        INNER JOIN cag ON patients.id = cag.id
//        JOIN pci
//        ON patients.id = pci.id
        query.append(" WHERE ");

        int conditions = 0;

        ////////////////////////////////////////////////////////////////

        long fromDateEpoch = 0;
        long toDateEpoch = 0;
        if (vFromDate != null)
            fromDateEpoch = vFromDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        if (vToDate != null)
            toDateEpoch = vToDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        if (vFromDate != null && vToDate != null) {
            query.append(" ( ");

            query.append(" ( ");

            query.append(PatientTable.TABLE_PATIENTS).append(".").append(PatientTable.COLUMN_PATIENT_DATE);
            query.append(" >= '").append(fromDateEpoch);

            query.append("' AND ");

            query.append(PatientTable.TABLE_PATIENTS).append(".").append(PatientTable.COLUMN_PATIENT_DATE);
            query.append(" <= '").append(toDateEpoch);

            query.append("') OR (");

            query.append(CagTable.TABLE_CAG).append(".").append(CagTable.COLUMN_CAG_DATE_OF_PROCEDURE);
            query.append(" >= '").append(fromDateEpoch);

            query.append("' AND ");

            query.append(CagTable.TABLE_CAG).append(".").append(CagTable.COLUMN_CAG_DATE_OF_PROCEDURE);
            query.append(" <= '").append(toDateEpoch);

            query.append("') OR (");

            query.append(PciTable.TABLE_PCI).append(".").append(PciTable.COLUMN_PCI_DATE_OF_PROCEDURE);
            query.append(" >= '").append(fromDateEpoch).append("' ");
            query.append(" AND ");
            query.append(PciTable.TABLE_PCI).append(".").append(PciTable.COLUMN_PCI_DATE_OF_PROCEDURE);
            query.append(" <= '").append(toDateEpoch).append("' ");
            query.append(") ");
            query.append(" ) ");
            conditions++;
        } else if (vFromDate != null) {
            query.append(" ( ");
            query.append(PatientTable.TABLE_PATIENTS).append(".").append(PatientTable.COLUMN_PATIENT_DATE);
            query.append(" >= '").append(fromDateEpoch).append("' OR ");
            query.append(CagTable.TABLE_CAG).append(".").append(CagTable.COLUMN_CAG_DATE_OF_PROCEDURE);
            query.append(" >= '").append(fromDateEpoch).append("' OR ");
            query.append(PciTable.TABLE_PCI).append(".").append(PciTable.COLUMN_PCI_DATE_OF_PROCEDURE);
            query.append(" >= '").append(fromDateEpoch).append("' ");
            query.append(" ) ");
            conditions++;
        } else if (vToDate != null) {
//            query.append(PatientTable.COLUMN_PATIENT_DATE);
//            query.append(" <= '").append(toDateEpoch).append("' ");

            query.append(" ( ");
            query.append(PatientTable.TABLE_PATIENTS).append(".").append(PatientTable.COLUMN_PATIENT_DATE);
            query.append(" <= '").append(toDateEpoch).append("' OR ");
            query.append(CagTable.TABLE_CAG).append(".").append(CagTable.COLUMN_CAG_DATE_OF_PROCEDURE);
            query.append(" <= '").append(toDateEpoch).append("' OR ");
            query.append(PciTable.TABLE_PCI).append(".").append(PciTable.COLUMN_PCI_DATE_OF_PROCEDURE);
            query.append(" <= '").append(toDateEpoch).append("' ");
            query.append(" ) ");
            conditions++;
        }

        ////////////////////////////////////////////////////////////////

        if (conditions != 0 && !vProcedure.trim().equals(""))
            query.append(" AND ");

        if (!vProcedure.trim().equals(""))
            conditions++;


        switch (vProcedure) {
            case "CAG":
                query.append(" " + PatientTable.COLUMN_PATIENT_HAS_CAG + " = 1 AND " + PatientTable.COLUMN_PATIENT_HAS_PCI + " = 0 ");
                break;
            case "PCI":
                query.append(" " + PatientTable.COLUMN_PATIENT_HAS_PCI + " = 1 AND " + PatientTable.COLUMN_PATIENT_HAS_CAG + " = 0 ");
                break;
            case "CAG + PCI":
                query.append(" " + PatientTable.COLUMN_PATIENT_HAS_CAG + " = 1 AND " + PatientTable.COLUMN_PATIENT_HAS_PCI + " = 1 ");
                break;
            case "LMCA":
                query.append(" " + PatientTable.COLUMN_PATIENT_HAS_LMCA + " = 1 ");
                break;
            case "Bifurcation":
                query.append(" " + PatientTable.COLUMN_PATIENT_HAS_BIFURICATION + " = 1 ");
                break;
        }

        ////////////////////////////////////////////////////////////////

        if (conditions != 0 && !vStent.trim().equals(""))
            query.append(" AND ");

        if (!vStent.trim().equals(""))
            conditions++;

        switch (vStent) {
            case "LAD":
                query.append(" " + PatientTable.COLUMN_PATIENT_HAS_STENT_LAD + " = 1 ");
                break;
            case "LCX":
                query.append(" " + PatientTable.COLUMN_PATIENT_HAS_STENT_LAX + " = 1 ");
                break;
            case "RCA":
                query.append(" " + PatientTable.COLUMN_PATIENT_HAS_STENT_RCA + " = 1 ");
                break;
            case "LMCA":
                query.append(" " + PatientTable.COLUMN_PATIENT_HAS_STENT_LMCA + " = 1 ");
                break;
            case "Diagonal":
                query.append(" " + PatientTable.COLUMN_PATIENT_HAS_STENT_DIAGONAL + " = 1 ");
                break;
            case "OM":
                query.append(" " + PatientTable.COLUMN_PATIENT_HAS_STENT_DM + " = 1 ");
                break;
        }

        ////////////////////////////////////////////////////////////////

        if (conditions != 0 && !vGender.trim().equals(""))
            query.append(" AND ");

        if (!vGender.trim().equals("")) {
            query.append(" " + PatientTable.COLUMN_PATIENT_SEX + " = '").append(vGender).append("'");
            conditions++;
        }

        ////////////////////////////////////////////////////////////////

        if (conditions != 0 && !vAge.trim().equals(""))
            query.append(" AND ");

        if (!vAge.trim().equals(""))
            conditions++;
        switch (vAge) {
            case "< 40":
                query.append(" " + PatientTable.COLUMN_PATIENT_AGE + " < 40 ");
                break;
            case "40 - 70":
                query.append(" " + PatientTable.COLUMN_PATIENT_AGE + " BETWEEN 40 AND 70 ");
                break;
            case "> 70":
                query.append(" " + PatientTable.COLUMN_PATIENT_AGE + " > 70 ");
                break;
        }

        ////////////////////////////////////////////////////////////////

        if (conditions != 0 && !nameField.getText().trim().equals(""))
            query.append(" AND ");

        if (!nameField.getText().trim().equals("")) {
            query.append(" " + PatientTable.COLUMN_PATIENT_NAME + " LIKE '%").append(nameField.getText()).append("%'");
            conditions++;
        }

        ////////////////////////////////////////////////////////////////

        if (conditions != 0 && !vDoctor.trim().equals(""))
            query.append(" AND ");

        if (!vDoctor.trim().equals("")) {
            query.append(" " + PatientTable.COLUMN_PATIENT_OPERATOR + " = '").append(vDoctor).append("' ");
            conditions++;
        }

        ////////////////////////////////////////////////////////////////

        if (conditions == 0)
            query.append(" 1=1 ");

        query.append(";");

//        SELECT sum(has_cag + has_pci) FROM patients
        StringBuilder procedureQuery = new StringBuilder();
        procedureQuery.append("SELECT SUM(");
        procedureQuery.append(PatientTable.COLUMN_PATIENT_HAS_CAG);
        procedureQuery.append(" + ");
        procedureQuery.append(PatientTable.COLUMN_PATIENT_HAS_PCI);
        procedureQuery.append(") FROM ");
        procedureQuery.append(PatientTable.TABLE_PATIENTS);
        procedureQuery.append(";");

        try {
            System.out.println(query);
            Datasource.instance.queryFilteredPatient(query.toString());
            System.out.println(procedureQuery);

            procedureCount.setText("Loading");


            Platform.runLater(() -> {
            int procedures = 0;
            for ( Patient filteredPatient: Datasource.getFilteredPatients() ) {
                if(filteredPatient.isHasCag())
                    procedures+=1;
                if(filteredPatient.isHasPci())
                    procedures+=1;
            }
                System.out.println("procedure count: " + procedures);
                procedureCount.setText(String.valueOf(procedures));

            });

        } catch (SQLException | IOException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        updateTableRow(new ArrayList<>(Datasource.getFilteredPatients()), patientDetails);
        count.setText(String.valueOf(Datasource.getFilteredPatients().size()));

    }

    @FXML
    void delFromDate() {
        fromDate.setValue(null);
    }

    @FXML
    void delToDate() {
        toDate.setValue(null);
    }

}
