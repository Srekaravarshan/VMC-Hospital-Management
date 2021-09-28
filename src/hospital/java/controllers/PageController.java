package hospital.java.controllers;

import hospital.java.models.AutoComplete;
import hospital.java.models.Patient;
import hospital.java.repositories.patient_repository.PatientRepository;
import hospital.java.sources.Datasource;
import hospital.java.sources.DropdownData;
import hospital.java.sources.PageSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PageController {

    public TextField
            complaintsValue,
            riskFactorsValue,
            otherComorbiditiesValue,
            pastCadValue,
            treatmentPastCadValue,
            echoValue,
            currentDiagnosisValue,
            complicationsValue;
    @FXML
    public ComboBox<String> operatorField;
    PageSource pageSource = PageSource.form.isEditing() ? PageSource.editForm : PageSource.form;
    Patient patient = new Patient();
    @FXML
    public CheckBox cagCheck, pciCheck;
    @FXML
    private TextField nameField, uhidField, ageField;
    @FXML
    private ComboBox<String> complaintsBox, riskFactorsBox, otherComorbiditiesBox, pastCadBox, treatmentForPastCadBox, echoBox,
            currentDiagnosisBox, complicationsPredischargeBox, sexField;
    @FXML
    private Label nameError, uhidError, ageError;
    @FXML
    private AnchorPane flash_msg;
    @FXML
    private DatePicker addedDate;
    @FXML
    private VBox detailsVBox;
    @FXML
    private Button complaintsAdd, riskFactorsAdd,
            otherComorbiditiesAdd,
            pastCadAdd,
            treatmentPastCadAdd,
            echoAdd,
            currentDiagnosisAdd,
            complicationsAdd;
    @FXML
    private Button addDetailBtn;

    public void addDetail(HashMap<String, String> map, boolean isEditing, Dictionary<String, String> editDictionary, Text title, Text desc) {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(detailsVBox.getScene().getWindow());
            dialog.setTitle("Add Detail");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(PageController.class.getResource("/hospital/resources/views/addOption.fxml"));

            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(
                    PatientRepository.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

            AddOptionController controller = fxmlLoader.getController();
            if (isEditing) {
                controller.setData(editDictionary);
            }
            final Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.addEventFilter(ActionEvent.ACTION, ae -> {
                if (!(controller.validate(map, isEditing, title))) {
                    ae.consume();
                } else {
                    Dictionary<String, String> dictionary = controller.processResults();
                    map.put(dictionary.get("title"), dictionary.get("desc"));

                    if (isEditing) {
                        title.setText(dictionary.get("title"));
                        desc.setText(dictionary.get("desc"));
                        return;
                    }

                    GridPane hBox = new GridPane();

                    Text titleText = new Text();
                    Text descText = new Text();

                    titleText.setText(dictionary.get("title"));
                    descText.setText(dictionary.get("desc"));

//                hBox.setSpacing(30);

                    VBox titlePane = new VBox();
                    VBox descPane = new VBox();


                    titleText.setWrappingWidth(150);
                    descText.setWrappingWidth(250);
                    titlePane.setPrefWidth(150);
                    descPane.setPrefWidth(250);
                    titlePane.setMaxWidth(150);
                    descPane.setMaxWidth(350);

                    titlePane.getStyleClass().add("key_label");
                    descPane.getStyleClass().add("key_label");

                    titlePane.getChildren().add(titleText);
                    descPane.getChildren().add(descText);

                    hBox.add(titlePane, 0, 0);
                    hBox.add(descPane, 1, 0);

                    Button editButton = new Button();
                    Button delButton = new Button();

                    editButton.setText("Edit");
                    delButton.setText("Delete");

                    editButton.setMinWidth(50);
                    delButton.setMinWidth(70);

                    hBox.add(editButton, 2, 0);
                    hBox.add(delButton, 3, 0);

                    hBox.setPadding(new Insets(10, 10, 10, 10));
                    hBox.setHgap(10);
                    PageController.this.detailsVBox.getChildren().add(hBox);

                    editButton.setOnAction(event -> {
                        map.remove(titleText.getText());
                        addDetail(map, true, new Hashtable<>() {{
                            put("title", titleText.getText());
                            put("desc", descText.getText());
                        }}, titleText, descText);
                    });
                    delButton.setOnAction(event -> {
                        map.remove(titleText.getText());
                        PageController.this.detailsVBox.getChildren().remove(hBox);
                    });

                }
            });
            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {



        sexField.getItems().add("");
        operatorField.getItems().add("");
        complaintsBox.getItems().add("");
        riskFactorsBox.getItems().add("");
        otherComorbiditiesBox.getItems().add("");
        pastCadBox.getItems().add("");
        treatmentForPastCadBox.getItems().add("");
        echoBox.getItems().add("");
        currentDiagnosisBox.getItems().add("");
        complicationsPredischargeBox.getItems().add("");
//        postPciBox.getItems().add("");


        sexField.getSelectionModel().selectFirst();
        operatorField.getSelectionModel().selectFirst();
        complaintsBox.getSelectionModel().selectFirst();
        riskFactorsBox.getSelectionModel().selectFirst();
        otherComorbiditiesBox.getSelectionModel().selectFirst();
        pastCadBox.getSelectionModel().selectFirst();
        treatmentForPastCadBox.getSelectionModel().selectFirst();
        echoBox.getSelectionModel().selectFirst();
        currentDiagnosisBox.getSelectionModel().selectFirst();
        complicationsPredischargeBox.getSelectionModel().selectFirst();
//        postPciBox.getSelectionModel().selectFirst();

        sexField.getItems().addAll(DropdownData.sexList);
        operatorField.getItems().addAll(DropdownData.getOperatorList());
        complaintsBox.getItems().addAll(DropdownData.complaintsList);
        riskFactorsBox.getItems().addAll(DropdownData.riskFactorsList);
        otherComorbiditiesBox.getItems().addAll(DropdownData.otherComorbiditiesList);
        pastCadBox.getItems().addAll(DropdownData.pastCadList);
        treatmentForPastCadBox.getItems().addAll(DropdownData.treatmentForPastCadList);
        echoBox.getItems().addAll(DropdownData.echoList);
        currentDiagnosisBox.getItems().addAll(DropdownData.currentDiagnosisList);
        complicationsPredischargeBox.getItems().addAll(DropdownData.complicationsPredischargeList);
//        postPciBox.getItems().addAll(DropdownData.postPsiList);
        addedDate.setValue(LocalDate.now());

        AutoComplete.autoCompleteComboBoxPlus(sexField, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
        AutoComplete.autoCompleteComboBoxPlus(operatorField, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
        AutoComplete.autoCompleteComboBoxPlus(complaintsBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
        AutoComplete.autoCompleteComboBoxPlus(riskFactorsBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
        AutoComplete.autoCompleteComboBoxPlus(otherComorbiditiesBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
        AutoComplete.autoCompleteComboBoxPlus(pastCadBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
        AutoComplete.autoCompleteComboBoxPlus(treatmentForPastCadBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
        AutoComplete.autoCompleteComboBoxPlus(echoBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
        AutoComplete.autoCompleteComboBoxPlus(currentDiagnosisBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
        AutoComplete.autoCompleteComboBoxPlus(complicationsPredischargeBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
//        AutoComplete.autoCompleteComboBoxPlus(postPciBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

        complaintsAdd.setOnAction(event -> addBtnClicked(complaintsAdd, patient.getComplaintsAdditional(), false, null, null, null, DropdownData.complaintsList));
        riskFactorsAdd.setOnAction(event -> addBtnClicked(riskFactorsAdd, patient.getRiskFactorsAdditional(), false, null, null, null, DropdownData.riskFactorsList));
        otherComorbiditiesAdd.setOnAction(event -> addBtnClicked(otherComorbiditiesAdd, patient.getOtherComorbiditiesAdditional(), false, null, null, null, DropdownData.otherComorbiditiesList));
        pastCadAdd.setOnAction(event -> addBtnClicked(pastCadAdd, patient.getPastCadAdditional(), false, null, null, null, DropdownData.pastCadList));
        treatmentPastCadAdd.setOnAction(event -> addBtnClicked(treatmentPastCadAdd, patient.getTreatmentPastCadAdditional(), false, null, null, null, DropdownData.treatmentForPastCadList));
        echoAdd.setOnAction(event -> addBtnClicked(echoAdd, patient.getEchoAdditional(), false, null, null, null, DropdownData.echoList));
        currentDiagnosisAdd.setOnAction(event -> addBtnClicked(currentDiagnosisAdd, patient.getCurrentDiagnosisAdditional(), false, null, null, null, DropdownData.currentDiagnosisList));
        complicationsAdd.setOnAction(event -> addBtnClicked(complicationsAdd, patient.getComplicationsAdditional(), false, null, null, null, DropdownData.complicationsPredischargeList));

        addDetailBtn.setOnAction(event -> addDetail(patient.getAdditionalDetails(), false, null, null, null));

    }

    private void optionView(Button button, Dictionary<String, String> dictionary, HashMap<String, String> map, ArrayList<String> list) {
        VBox vBox = (VBox) button.getParent().getParent();

        GridPane hBox = new GridPane();

        Text titleText = new Text();
        Text descText = new Text();

        titleText.setText(dictionary.get("title"));
        descText.setText(dictionary.get("desc"));


        VBox titlePane = new VBox();
        VBox descPane = new VBox();

        titleText.setWrappingWidth(150);
        descText.setWrappingWidth(250);
        titlePane.setPrefWidth(150);
        descPane.setPrefWidth(250);
        titlePane.setMaxWidth(150);
        descPane.setMaxWidth(350);

        titlePane.getStyleClass().add("key_label");
        descPane.getStyleClass().add("key_label");

        titlePane.getChildren().add(titleText);
        descPane.getChildren().add(descText);

        hBox.add(titlePane, 0, 0);
        hBox.add(descPane, 1, 0);

        Button editButton = new Button();
        Button delButton = new Button();

        editButton.setText("Edit");
        delButton.setText("Delete");

        editButton.setMinWidth(50);
        delButton.setMinWidth(70);

        hBox.add(editButton, 2, 0);
        hBox.add(delButton, 3, 0);

        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setHgap(10);

        vBox.getChildren().add(hBox);

        editButton.setOnAction(event -> {
            map.remove(titleText.getText());
            addBtnClicked(button, map, true, new Hashtable<>() {{
                put("title", titleText.getText());
                put("desc", descText.getText());
            }}, titleText, descText, list);
        });
        delButton.setOnAction(event -> {
            map.remove(titleText.getText());
            vBox.getChildren().remove(hBox);
        });
    }

    private void addBtnClicked(Button button, HashMap<String, String> map, boolean isEditing, Dictionary<String, String> editDictionary, Text title, Text desc, ArrayList<String> list) {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(button.getScene().getWindow());
            dialog.setTitle("Add Option");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(PageController.class.getResource("/hospital/resources/views/addOptionCombo.fxml"));

            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(
                    PatientRepository.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

            AddOptionComboController controller = fxmlLoader.getController();
            controller.addItems(list);
            if (isEditing) {
                controller.setData(editDictionary);
            }

            final Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.addEventFilter(ActionEvent.ACTION, ae -> {
                if (!(controller.validate(map, isEditing, title))) {
                    ae.consume();
                } else {

                    Dictionary<String, String> dictionary = controller.processResults();
                    map.put(dictionary.get("title"), dictionary.get("desc"));

                    if (isEditing) {
                        title.setText(dictionary.get("title"));
                        desc.setText(dictionary.get("desc"));
                        return;
                    }

                    VBox vBox = (VBox) button.getParent().getParent();

                    GridPane hBox = new GridPane();

                    Text titleText = new Text();
                    Text descText = new Text();

                    titleText.setText(dictionary.get("title"));
                    descText.setText(dictionary.get("desc"));


                    VBox titlePane = new VBox();
                    VBox descPane = new VBox();

                    titleText.setWrappingWidth(150);
                    descText.setWrappingWidth(250);
                    titlePane.setPrefWidth(150);
                    descPane.setPrefWidth(250);
                    titlePane.setMaxWidth(150);
                    descPane.setMaxWidth(350);

                    titlePane.getStyleClass().add("key_label");
                    descPane.getStyleClass().add("key_label");

                    titlePane.getChildren().add(titleText);
                    descPane.getChildren().add(descText);

                    hBox.add(titlePane, 0, 0);
                    hBox.add(descPane, 1, 0);

                    Button editButton = new Button();
                    Button delButton = new Button();

                    editButton.setText("Edit");
                    delButton.setText("Delete");

                    editButton.setMinWidth(50);
                    delButton.setMinWidth(70);

                    hBox.add(editButton, 2, 0);
                    hBox.add(delButton, 3, 0);

                    hBox.setPadding(new Insets(10, 10, 10, 10));
                    hBox.setHgap(10);
                    vBox.getChildren().add(hBox);

                    editButton.setOnAction(event -> {
                        map.remove(titleText.getText());
                        addBtnClicked(button, map, true, new Hashtable<>() {{
                            put("title", titleText.getText());
                            put("desc", descText.getText());
                        }}, titleText, descText, list);
                    });
                    delButton.setOnAction(event -> {
                        map.remove(titleText.getText());
                        vBox.getChildren().remove(hBox);
                    });


                }
            });
            dialog.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(Patient patient) {
        this.nameField.setText(patient.getName());
        this.uhidField.setText(patient.getUHID());
        this.ageField.setText(String.valueOf(patient.getAge()));
        this.sexField.getSelectionModel().select(patient.getSex());
        this.operatorField.getSelectionModel().select(patient.getOperator());
        this.complaintsBox.getSelectionModel().select(patient.getComplaints());
        this.riskFactorsBox.getSelectionModel().select(patient.getRiskFactors());
        this.otherComorbiditiesBox.getSelectionModel().select(patient.getOtherComorbidities());
        this.pastCadBox.getSelectionModel().select(patient.getCad());
        this.treatmentForPastCadBox.getSelectionModel().select(patient.getTreatmentForPastCad());
        this.echoBox.getSelectionModel().select(patient.getEcho());
        this.currentDiagnosisBox.getSelectionModel().select(patient.getCurrentDiagnosis());
        this.complicationsPredischargeBox.getSelectionModel().select(patient.getComplicationsInHospitalPredischarge());

        this.complaintsValue.setText(patient.getComplaintsField());
        this.riskFactorsValue.setText(patient.getRiskFactorsField());
        this.otherComorbiditiesValue.setText(patient.getOtherComorbiditiesField());
        this.pastCadValue.setText(patient.getPastCadField());
        this.treatmentPastCadValue.setText(patient.getTreatmentPastCadField());
        this.echoValue.setText(patient.getEchoField());
        this.currentDiagnosisValue.setText(patient.getCurrentDiagnosisField());
        this.complicationsValue.setText(patient.getComplicationsField());

        this.addedDate.setValue(patient.getDate());

        this.cagCheck.setSelected(patient.isHasCag());
        this.pciCheck.setSelected(patient.isHasPci());
//        this.dateOfProcedure.setValue(patient.getDateOfProcedure());

        addOptionView(complaintsAdd, patient.getComplaintsAdditional(), DropdownData.complaintsList);
        addOptionView(riskFactorsAdd, patient.getRiskFactorsAdditional(), DropdownData.riskFactorsList);
        addOptionView(otherComorbiditiesAdd, patient.getOtherComorbiditiesAdditional(), DropdownData.otherComorbiditiesList);
        addOptionView(pastCadAdd, patient.getPastCadAdditional(), DropdownData.pastCadList);
        addOptionView(treatmentPastCadAdd, patient.getTreatmentPastCadAdditional(), DropdownData.treatmentForPastCadList);
        addOptionView(echoAdd, patient.getEchoAdditional(), DropdownData.echoList);
        addOptionView(currentDiagnosisAdd, patient.getCurrentDiagnosisAdditional(), DropdownData.currentDiagnosisList);
        addOptionView(complicationsAdd, patient.getComplicationsAdditional(), DropdownData.complicationsPredischargeList);

        this.patient.setComplaintsAdditional(patient.getComplaintsAdditional());
        this.patient.setRiskFactorsAdditional(patient.getRiskFactorsAdditional());
        this.patient.setOtherComorbiditiesAdditional(patient.getOtherComorbiditiesAdditional());
        this.patient.setPastCadAdditional(patient.getPastCadAdditional());
        this.patient.setTreatmentPastCadAdditional(patient.getTreatmentPastCadAdditional());
        this.patient.setEchoAdditional(patient.getEchoAdditional());
        this.patient.setCurrentDiagnosisAdditional(patient.getCurrentDiagnosisAdditional());
        this.patient.setComplicationsAdditional(patient.getComplicationsAdditional());

        this.patient.setAdditionalDetails(patient.getAdditionalDetails());

        addOptionView(patient.getAdditionalDetails());
    }


    private void addOptionView(HashMap<String, String> map) {
        map.forEach((k, v) -> {
            Dictionary<String, String> dictionary = new Hashtable<>() {{
                put("title", k);
                put("desc", v);
            }};
            optionView(dictionary, map);
        });
    }

    private void optionView(Dictionary<String, String> dictionary, HashMap<String, String> map) {

        GridPane hBox = new GridPane();

        Text titleText = new Text();
        Text descText = new Text();

        titleText.setText(dictionary.get("title"));
        descText.setText(dictionary.get("desc"));

//                hBox.setSpacing(30);

        VBox titlePane = new VBox();
        VBox descPane = new VBox();


        titleText.setWrappingWidth(150);
        descText.setWrappingWidth(250);
        titlePane.setPrefWidth(150);
        descPane.setPrefWidth(250);
        titlePane.setMaxWidth(150);
        descPane.setMaxWidth(350);

        titlePane.getStyleClass().add("key_label");
        descPane.getStyleClass().add("key_label");

        titlePane.getChildren().add(titleText);
        descPane.getChildren().add(descText);

        hBox.add(titlePane, 0, 0);
        hBox.add(descPane, 1, 0);

        Button editButton = new Button();
        Button delButton = new Button();

        editButton.setText("Edit");
        delButton.setText("Delete");

        editButton.setMinWidth(50);
        delButton.setMinWidth(70);

        hBox.add(editButton, 2, 0);
        hBox.add(delButton, 3, 0);

        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setHgap(10);
        PageController.this.detailsVBox.getChildren().add(hBox);

        editButton.setOnAction(event -> {
            map.remove(titleText.getText());
            addDetail(map, true, new Hashtable<>() {{
                put("title", titleText.getText());
                put("desc", descText.getText());
            }}, titleText, descText);
        });
        delButton.setOnAction(event -> {
            map.remove(titleText.getText());
            PageController.this.detailsVBox.getChildren().remove(hBox);
        });
    }

    private void addOptionView(Button button, HashMap<String, String> map, ArrayList<String> list) {
        map.forEach((k, v) -> {
            Dictionary<String, String> dictionary = new Hashtable<>() {{
                put("title", k);
                put("desc", v);
            }};
            optionView(button, dictionary, map, list);
        });
    }

    public Patient processResults() {
        String name = nameField.getText().trim();
        String uhid = uhidField.getText().trim();
        String age = ageField.getText().trim();
        String sex = sexField.getSelectionModel().getSelectedItem();
        String operator = operatorField.getSelectionModel().getSelectedItem();
        String complaints = complaintsBox.getSelectionModel().getSelectedItem();
        String riskFactors = riskFactorsBox.getSelectionModel().getSelectedItem();
        String otherComorbidities = otherComorbiditiesBox.getSelectionModel().getSelectedItem();
        String pastCad = pastCadBox.getSelectionModel().getSelectedItem();
        String treatmentForPastCad = treatmentForPastCadBox.getSelectionModel().getSelectedItem();
        String echo = echoBox.getSelectionModel().getSelectedItem();
        String currentDiagnosis = currentDiagnosisBox.getSelectionModel().getSelectedItem();
        String complicationsPredischarge = complicationsPredischargeBox.getSelectionModel().getSelectedItem();
//        String postPci = postPciBox.getSelectionModel().getSelectedItem();

        String complaintsField = complaintsValue.getText().trim();
        String riskFactorsField = riskFactorsValue.getText().trim();
        String otherComorbiditiesField = otherComorbiditiesValue.getText().trim();
        String pastCadField = pastCadValue.getText().trim();
        String treatmentPastCadField = treatmentPastCadValue.getText().trim();
        String echoField = echoValue.getText().trim();
        String currentDiagnosisField = currentDiagnosisValue.getText().trim();
        String complicationsField = complicationsValue.getText().trim();

        LocalDate date = addedDate.getValue();

        HashMap<String, String> stentAdditional = pageSource.page2Controller().pci.getStentAdditional();
        String stentBoxValue = pageSource.page2Controller().stentBox.getSelectionModel().getSelectedItem();
//        "LAD", "LCX", "RCA", "LMCA", "Diagonal", "OM"
        String lmcaBoxValuePci = pageSource.page2Controller().lmcaDiseaseBox.getSelectionModel().getSelectedItem();
        String bipurificationBoxValuePci = pageSource.page2Controller().bipurificationDiseaseBox.getSelectionModel().getSelectedItem();
        String lmcaBoxValueCag = pageSource.page3Controller().lmcaDiseaseBox.getSelectionModel().getSelectedItem();
        String bipurificationBoxValueCag = pageSource.page3Controller().bipurificationDiseaseBox.getSelectionModel().getSelectedItem();

        patient.setHasLmca(lmcaBoxValueCag.equals("Yes") || lmcaBoxValuePci.equals("Yes"));
        patient.setHasBifurication(bipurificationBoxValueCag.equals("Yes") || bipurificationBoxValuePci.equals("Yes"));

        patient.setHasStentLad(stentAdditional.containsKey("LAD"));
        patient.setHasStentLcx(stentAdditional.containsKey("LCX"));
        patient.setHasStentRca(stentAdditional.containsKey("RCA"));
        patient.setHasStentLmca(stentAdditional.containsKey("LMCA"));
        patient.setHasStentDiagonal(stentAdditional.containsKey("Diagonal"));
        patient.setHasStentOm(stentAdditional.containsKey("OM"));

        patient.setHasCag(cagCheck.isSelected());
        patient.setHasPci(pciCheck.isSelected());

        if (stentBoxValue.equals("LAD"))
            patient.setHasStentLad(true);
        if (stentBoxValue.equals("LCX"))
            patient.setHasStentLcx(true);
        if (stentBoxValue.equals("RCA"))
            patient.setHasStentRca(true);
        if (stentBoxValue.equals("LMCA"))
            patient.setHasStentLmca(true);
        if (stentBoxValue.equals("Diagonal"))
            patient.setHasStentDiagonal(true);
        if (stentBoxValue.equals("OM"))
            patient.setHasStentOm(true);


        patient.setName(name);
        patient.setUHID(uhid);
        patient.setAge(Integer.parseInt(age));
        patient.setSex(sex);
        patient.setOperator(operator);
        patient.setComplaints(complaints);
        patient.setRiskFactors(riskFactors);
        patient.setOtherComorbidities(otherComorbidities);
        patient.setCad(pastCad);
        patient.setTreatmentForPastCad(treatmentForPastCad);
        patient.setEcho(echo);
        patient.setCurrentDiagnosis(currentDiagnosis);
        patient.setComplicationsInHospitalPredischarge(complicationsPredischarge);

        patient.setComplaintsField(complaintsField);
        patient.setRiskFactorsField(riskFactorsField);
        patient.setOtherComorbiditiesField(otherComorbiditiesField);
        patient.setPastCadField(pastCadField);
        patient.setTreatmentPastCadField(treatmentPastCadField);
        patient.setEchoField(echoField);
        patient.setCurrentDiagnosisField(currentDiagnosisField);
        patient.setComplicationsField(complicationsField);


        patient.setComplaintsAdditional(this.patient.getComplaintsAdditional());
        patient.setRiskFactorsAdditional(this.patient.getRiskFactorsAdditional());
        patient.setOtherComorbiditiesAdditional(this.patient.getOtherComorbiditiesAdditional());
        patient.setPastCadAdditional(this.patient.getPastCadAdditional());
        patient.setTreatmentPastCadAdditional(this.patient.getTreatmentPastCadAdditional());
        patient.setEchoAdditional(this.patient.getEchoAdditional());
        patient.setCurrentDiagnosisAdditional(this.patient.getCurrentDiagnosisAdditional());
        patient.setComplicationsAdditional(this.patient.getComplicationsAdditional());

        patient.setDate(date);
//        patient.setDateOfProcedure(dop);

        return patient;
    }

    public boolean validate(boolean isPrinting) throws SQLException {
        boolean isValid = true;
        nameError.setVisible(false);
        uhidError.setVisible(false);
        ageError.setVisible(false);
        if (this.nameField.getText().trim().length() <= 0) {
            nameError.setText("Name should not be null.");
            nameError.setVisible(true);
            isValid = false;
        }
        if (!isPrinting) {
            if (!(Datasource.instance.isUnique(uhidField.getText().trim()))) {
                uhidError.setText("UHID must be unique.");
                uhidError.setVisible(true);
                isValid = false;
            }
        }
        if (this.ageField.getText().isEmpty()) {
            ageError.setText("Age cannot be null.");
            ageError.setVisible(true);
            isValid = false;
        }
        if (!this.ageField.getText().isEmpty()) {
            try {
                Integer.valueOf(ageField.getText().trim());
            } catch (Exception e) {
                ageError.setText("Age must be in numbers.");
                ageError.setVisible(true);
                isValid = false;
            }
        } else {
            ageError.setText("Age must be in numbers.");
            ageError.setVisible(true);
            isValid = false;
        }
        return isValid;
    }

    @FXML
    public void clear() {

        nameError.setVisible(false);
        uhidError.setVisible(false);
        ageError.setVisible(false);

        this.nameField.setText("");
        this.uhidField.setText("");
        this.ageField.setText("");
        this.sexField.getSelectionModel().select("");
        this.operatorField.getSelectionModel().select("");
        this.complaintsBox.getSelectionModel().select("");
        this.riskFactorsBox.getSelectionModel().select("");
        this.otherComorbiditiesBox.getSelectionModel().select("");
        this.pastCadBox.getSelectionModel().select("");
        this.treatmentForPastCadBox.getSelectionModel().select("");
        this.echoBox.getSelectionModel().select("");
        this.currentDiagnosisBox.getSelectionModel().select("");
        this.complicationsPredischargeBox.getSelectionModel().select("");
//        this.postPciBox.getSelectionModel().clearSelection();

        this.complaintsValue.setText("");
        this.riskFactorsValue.setText("");
        this.otherComorbiditiesValue.setText("");
        this.pastCadValue.setText("");
        this.treatmentPastCadValue.setText("");
        this.echoValue.setText("");
        this.currentDiagnosisValue.setText("");
        this.complicationsValue.setText("");

        this.cagCheck.setSelected(false);
        this.pciCheck.setSelected(false);

        patient.setHasLmca(false);
        patient.setHasBifurication(false);

        patient.setHasStentLad(false);
        patient.setHasStentLcx(false);
        patient.setHasStentRca(false);
        patient.setHasStentLmca(false);
        patient.setHasStentDiagonal(false);
        patient.setHasStentOm(false);

        if (detailsVBox.getChildren().size() > 16)
            detailsVBox.getChildren().subList(16, detailsVBox.getChildren().size()).clear();
        patient.getAdditionalDetails().clear();

        buttonAddClear(complaintsAdd, patient.getComplaintsAdditional());
        buttonAddClear(riskFactorsAdd, patient.getRiskFactorsAdditional());
        buttonAddClear(otherComorbiditiesAdd, patient.getOtherComorbiditiesAdditional());
        buttonAddClear(pastCadAdd, patient.getPastCadAdditional());
        buttonAddClear(treatmentPastCadAdd, patient.getTreatmentPastCadAdditional());
        buttonAddClear(echoAdd, patient.getEchoAdditional());
        buttonAddClear(currentDiagnosisAdd, patient.getCurrentDiagnosisAdditional());
        buttonAddClear(complicationsAdd, patient.getComplicationsAdditional());
    }

    private void buttonAddClear(Button button, HashMap<String, String> map) {
        map.clear();
        VBox buttonVBox = (VBox) button.getParent().getParent();
        if (buttonVBox.getChildren().size() > 2)
            buttonVBox.getChildren().subList(2, buttonVBox.getChildren().size()).clear();
    }

    public void flashMessage() {
        flash_msg.setLayoutY(-50);
    }

    public void closeFlashMessage() {
        flash_msg.setLayoutY(0);
    }
}
