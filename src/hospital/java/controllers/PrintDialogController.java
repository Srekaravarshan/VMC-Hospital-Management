package hospital.java.controllers;

import hospital.java.models.PatientData;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class PrintDialogController {

    @FXML
    private TableView<PatientData> patientDetails;


    public void setData(ArrayList<String> values, ArrayList<String> keys, ArrayList<ArrayList<String>> summaryKeys) {
        TableColumn<PatientData, String> column1;
        if (summaryKeys == null) {
            column1 = new TableColumn<>("Title");
            column1.setCellValueFactory(new PropertyValueFactory<>("title"));
            column1.setPrefWidth(250);
            TableColumn<PatientData, String> column2 = new TableColumn<>("Detail");
            column2.setCellValueFactory(new PropertyValueFactory<>("detail"));
            column2.setPrefWidth(500);

            patientDetails.getColumns().add(column1);
            patientDetails.getColumns().add(column2);

            patientDetails.getItems().clear();
            for (int row = 0; row < keys.size(); row++) {
                if (values.get(row) != null && !values.get(row).trim().equals("") && !values.get(row).trim().equals("null"))
                    patientDetails.getItems().add(new PatientData(keys.get(row), values.get(row), null, null, null, null, null, null));
            }
        } else {
            column1 = new TableColumn<>("S.No.");
            column1.setCellValueFactory(new PropertyValueFactory<>("sno"));
            column1.setPrefWidth(40);
            TableColumn<PatientData, String> column2 = new TableColumn<>("Patient name");
            column2.setCellValueFactory(new PropertyValueFactory<>("name"));
            column2.setPrefWidth(150);
            TableColumn<PatientData, String> column3 = new TableColumn<>("Age");
            column3.setCellValueFactory(new PropertyValueFactory<>("age"));
            column3.setPrefWidth(40);
            TableColumn<PatientData, String> column4 = new TableColumn<>("Sex");
            column4.setCellValueFactory(new PropertyValueFactory<>("sex"));
            column4.setPrefWidth(60);
            TableColumn<PatientData, String> column5 = new TableColumn<>("Uhid");
            column5.setCellValueFactory(new PropertyValueFactory<>("UHID"));
            column5.setPrefWidth(150);

            patientDetails.getColumns().add(column1);
            patientDetails.getColumns().add(column2);
            patientDetails.getColumns().add(column3);
            patientDetails.getColumns().add(column4);
            patientDetails.getColumns().add(column5);

            patientDetails.getItems().clear();
            for (ArrayList<String> summaryKey : summaryKeys) {
                patientDetails.getItems().add(new PatientData("", "", summaryKey.get(0), summaryKey.get(1), summaryKey.get(2), summaryKey.get(3), summaryKey.get(4), summaryKey.get(5)));
            }

        }
        if (patientDetails != null) {
            patientDetails.refresh();
        }
    }
}
