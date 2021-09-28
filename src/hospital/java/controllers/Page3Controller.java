package hospital.java.controllers;

import hospital.java.models.AutoComplete;
import hospital.java.models.CagModel;
import hospital.java.models.TabTraversal;
import hospital.java.repositories.patient_repository.PatientRepository;
import hospital.java.sources.DropdownData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Page3Controller {

    @FXML
    private AnchorPane flash_msg;

    @FXML
    public TextField
            accessRouteValue,
            lmcaDiseaseValue,
            bipurificationDiseaseValue,
            bpField_1,
            bpField_2,
            bpField_3,
            hrField;

    @FXML
    private Button accessRouteAdd;
    @FXML
    public ComboBox<String> accessRouteBox,
            lmcaDiseaseBox,
            bipurificationDiseaseBox;

    @FXML
    private VBox cagVBox;

    @FXML
    private TextArea
//            vitalsField,
            lmcaField,
            ladField,
            d1Field,
            lcxField,
            omField,
            rcaField,
            diagnosisField,
            thrombusField,
            calcificationField,
            restenosisField,
            recommendationField,
            miscellaneousField,
            finalImpressionField;

    @FXML
    private Button addDetailBtn;
    @FXML
    private DatePicker dateOfProcedure;

    @FXML
    public void clear () {
        dateOfProcedure.setValue(null);
        bpField_1.setText("");
        bpField_2.setText("");
        bpField_3.setText("");
        hrField.setText("");
        lmcaField.setText("");
        ladField.setText("");
        d1Field.setText("");
        lcxField.setText("");
        omField.setText("");
        rcaField.setText("");
        diagnosisField.setText("");
        thrombusField.setText("");
        calcificationField.setText("");
        restenosisField.setText("");
        recommendationField.setText("");
        miscellaneousField.setText("");
        finalImpressionField.setText("");
        this.accessRouteBox.getSelectionModel().select("");
        this.accessRouteValue.setText("");
        this.lmcaDiseaseBox.getSelectionModel().select("");
        this.lmcaDiseaseValue.setText("");
        this.bipurificationDiseaseBox.getSelectionModel().select("");
        this.bipurificationDiseaseValue.setText("");

        buttonAddClear(accessRouteAdd, cag.getAccessRouteAdditional());
        if(cagVBox.getChildren().size() > 19)
            cagVBox.getChildren().subList(19, cagVBox.getChildren().size()).clear();

        cag.getAdditionalDetails().clear();
    }

    private void buttonAddClear(Button button, HashMap<String, String> map) {
        map.clear();
        VBox buttonVBox = (VBox) button.getParent().getParent();
        if (buttonVBox.getChildren().size() > 2)
            buttonVBox.getChildren().subList(2, buttonVBox.getChildren().size()).clear();
    }

    CagModel cag = new CagModel();

    public void addDetail (HashMap<String, String> map, boolean isEditing, Dictionary<String, String> editDictionary, Text title, Text desc) {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(cagVBox.getScene().getWindow());
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
                            Page3Controller.this.cagVBox.getChildren().add(hBox);

                            editButton.setOnAction(event -> {
                                map.remove(titleText.getText());
                                addDetail(map, true, new Hashtable<>() {{
                                    put("title", titleText.getText());
                                    put("desc", descText.getText());
                                }}, titleText, descText);
                            });
                            delButton.setOnAction(event -> {
                                map.remove(titleText.getText());
                                Page3Controller.this.cagVBox.getChildren().remove(hBox);
                            });

                        }
                    });
            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData (CagModel cag) {
        bpField_1.setText(cag.getBp_1());
        bpField_2.setText(cag.getBp_2());
        bpField_3.setText(cag.getBp_2());
        hrField.setText(cag.getHr());
        lmcaField.setText(cag.getLmca());
        ladField.setText(cag.getLad());
        d1Field.setText(cag.getD1());
        lcxField.setText(cag.getLcx());
        omField.setText(cag.getOm());
        rcaField.setText(cag.getRca());
        diagnosisField.setText(cag.getDiagnosis());
        thrombusField.setText(cag.getBifurcation());
        calcificationField.setText(cag.getCalcification());
        restenosisField.setText(cag.getRestenosis());
        recommendationField.setText(cag.getRecommendation());
        miscellaneousField.setText(cag.getMiscellaneous());
        finalImpressionField.setText(cag.getFinalImpression());
        accessRouteBox.getSelectionModel().select(cag.getAccessRoute());
        accessRouteValue.setText(cag.getAccessRouteField());
        lmcaDiseaseBox.getSelectionModel().select(cag.getLmcaDisease());
        lmcaDiseaseValue.setText(cag.getLmcaDiseaseField());
        bipurificationDiseaseBox.getSelectionModel().select(cag.getBipurificationDisease());
        bipurificationDiseaseValue.setText(cag.getBipurificationDiseaseField());

        addOptionView(accessRouteAdd, cag.getAccessRouteAdditional(), DropdownData.accessRouteList);
        this.cag.setAccessRouteAdditional(cag.getAccessRouteAdditional());

        this.cag.setAdditionalDetails(cag.getAdditionalDetails());

        this.dateOfProcedure.setValue(cag.getDateOfProcedure());
        addOptionView(cag.getAdditionalDetails());

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

    private void addOptionView(Button button, HashMap<String, String> map, ArrayList<String> list) {
        map.forEach((k, v) -> {
            Dictionary<String, String> dictionary = new Hashtable<>() {{
                put("title", k);
                put("desc", v);
            }};
            optionView(button, dictionary, map, list);
        });
    }

    private void addOptionView ( HashMap<String, String> map) {
        map.forEach((k, v) -> {
            Dictionary<String, String> dictionary = new Hashtable<>(){{put("title", k); put("desc", v);}};
            optionView(dictionary, map);
        });
    }

    private void optionView (Dictionary<String, String> dictionary, HashMap<String, String> map) {

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
                    Page3Controller.this.cagVBox.getChildren().add(hBox);

                    editButton.setOnAction(event -> {
                        map.remove(titleText.getText());
                        addDetail(map, true, new Hashtable<>() {{
                            put("title", titleText.getText());
                            put("desc", descText.getText());
                        }}, titleText, descText);
                    });
                    delButton.setOnAction(event -> {
                        map.remove(titleText.getText());
                        Page3Controller.this.cagVBox.getChildren().remove(hBox);
                    });
    }

    public CagModel processResults() {

        LocalDate dop = dateOfProcedure.getValue();

        cag.setBp_1(bpField_1.getText().trim());
        cag.setBp_2(bpField_2.getText().trim());
        cag.setBp_3(bpField_3.getText().trim());
        cag.setHr(hrField.getText().trim());
        cag.setLmca(lmcaField.getText().trim());
        cag.setLad(ladField.getText().trim());
        cag.setD1(d1Field.getText().trim());
        cag.setLcx(lcxField.getText().trim());
        cag.setOm(omField.getText().trim());
        cag.setRca(rcaField.getText().trim());
        cag.setDiagnosis(diagnosisField.getText().trim());
        cag.setBifurcation(thrombusField.getText().trim());
        cag.setCalcification(calcificationField.getText().trim());
        cag.setRestenosis(restenosisField.getText().trim());
        cag.setRecommendation(recommendationField.getText().trim());
        cag.setMiscellaneous(miscellaneousField.getText().trim());
        cag.setFinalImpression(finalImpressionField.getText().trim());

        String accessRoute = accessRouteBox.getSelectionModel().getSelectedItem();
        String accessRouteField = accessRouteValue.getText().trim();
        cag.setAccessRoute(accessRoute);
        cag.setAccessRouteField(accessRouteField);

        String lmcaDisease = lmcaDiseaseBox.getSelectionModel().getSelectedItem();
        String lmcaDiseaseField = lmcaDiseaseValue.getText().trim();
        cag.setLmcaDisease(lmcaDisease);
        cag.setLmcaDiseaseField(lmcaDiseaseField);

        String bipurificationDisease = bipurificationDiseaseBox.getSelectionModel().getSelectedItem();
        String bipurificationDiseaseField = bipurificationDiseaseValue.getText().trim();
        cag.setBipurificationDisease(bipurificationDisease);
        cag.setBipurificationDiseaseField(bipurificationDiseaseField);

        cag.setAccessRouteAdditional(this.cag.getAccessRouteAdditional());

        cag.setDateOfProcedure(dop);
        cag.setAdditionalDetails(this.cag.getAdditionalDetails());

        return cag;
    }

    public void initialize () {

        final int[] bp1 = {0};
        final int[] bp2 = {0};
        final int[] mean = {0};

//      mean = (bp1 + 2*(bp2))/3

        bpField_1.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                bp1[0] = parseInt(newValue);
            } catch (NumberFormatException ignored) {
            }

            mean[0] = (bp1[0] + (2 * (bp2[0]))) / 3;
            bpField_3.setText(String.valueOf(mean[0]));
        });

        bpField_2.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                bp2[0] = parseInt(newValue);
            } catch (NumberFormatException ignored) {
            }
            mean[0] = (bp1[0] + (2 * (bp2[0]))) / 3;
            bpField_3.setText(String.valueOf(mean[0]));
        });

        accessRouteBox.getItems().add("");
        accessRouteBox.getSelectionModel().select("");
        accessRouteBox.getItems().addAll(DropdownData.accessRouteList);
        AutoComplete.autoCompleteComboBoxPlus(accessRouteBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

        lmcaDiseaseBox.getItems().add("");
        lmcaDiseaseBox.getSelectionModel().select("");
        lmcaDiseaseBox.getItems().addAll(DropdownData.lmcaDiseaseList);
        AutoComplete.autoCompleteComboBoxPlus(lmcaDiseaseBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

        bipurificationDiseaseBox.getItems().add("");
        bipurificationDiseaseBox.getSelectionModel().select("");
        bipurificationDiseaseBox.getItems().addAll(DropdownData.bipurificationDiseaseList);
        AutoComplete.autoCompleteComboBoxPlus(bipurificationDiseaseBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

        lmcaField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        ladField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        d1Field.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        lcxField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        omField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        rcaField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        diagnosisField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        thrombusField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        calcificationField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        restenosisField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        recommendationField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        miscellaneousField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        finalImpressionField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());

        addDetailBtn.setOnAction(event -> addDetail(cag.getAdditionalDetails(), false, null, null, null));

                accessRouteAdd.setOnAction(event -> addBtnClicked(accessRouteAdd, cag.getAccessRouteAdditional(), false, null, null, null, DropdownData.accessRouteList));

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

    public void flashMessage() {
        flash_msg.setLayoutY(-50);
    }

    public void closeFlashMessage() {
        flash_msg.setLayoutY(0);
    }


}
