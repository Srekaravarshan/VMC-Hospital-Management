package hospital.java.controllers;

import hospital.java.models.AutoComplete;
import hospital.java.models.PciModel;
import hospital.java.models.TabTraversal;
import hospital.java.repositories.patient_repository.PatientRepository;
import hospital.java.sources.DropdownData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Page2Controller {

    @FXML
    public TextField
            accessRouteValue,
            stentValue,
            lmcaDiseaseValue,
            bipurificationDiseaseValue,
            bpField_1,
            bpField_2,
            bpField_3,
            hrField,
            syntaxScoreField;
    @FXML
    public ComboBox<String> accessRouteBox, stentBox, lmcaDiseaseBox,
            bipurificationDiseaseBox;
    public PciModel pci = new PciModel();
    @FXML
    private Button stentAdd, accessRouteAdd;
    @FXML
    private AnchorPane flash_msg;
    @FXML
    private VBox pciVBox;
    @FXML
    private Button addDetailBtn;
    @FXML
    private TextArea
            catheterField,
            wireField,
            aspirationField,
            predilationField,
            postDilationField,
            timiFlowField,
            timeFirstMedicalField,
            dbtField,
            complicationsField,
            bifurcationField,
            lmcaField,
            specialBalloonsField,
            guideCatheterField,
            ivusField,
            octField,
            rotablation,
            finalImpression;
    @FXML
    private Hyperlink syntaxCalc;
    @FXML
    private DatePicker dateOfProcedure;

    @FXML
    public void clear() {
        dateOfProcedure.setValue(null);
        bpField_1.setText("");
        bpField_2.setText("");
        bpField_3.setText("");
        hrField.setText("");
        syntaxScoreField.setText("");
        catheterField.setText("");
        wireField.setText("");
        aspirationField.setText("");
        predilationField.setText("");
        postDilationField.setText("");
        timiFlowField.setText("");
        timeFirstMedicalField.setText("");
        dbtField.setText("");
        complicationsField.setText("");
        bifurcationField.setText("");
        lmcaField.setText("");
        specialBalloonsField.setText("");
        guideCatheterField.setText("");
        ivusField.setText("");
        octField.setText("");
        rotablation.setText("");
        finalImpression.setText("");
        this.accessRouteBox.getSelectionModel().select("");
        this.accessRouteValue.setText("");
        this.stentBox.getSelectionModel().select("");
        this.stentValue.setText("");
        this.lmcaDiseaseBox.getSelectionModel().select("");
        this.lmcaDiseaseValue.setText("");
        this.bipurificationDiseaseBox.getSelectionModel().select("");
        this.bipurificationDiseaseValue.setText("");

        buttonAddClear(stentAdd, pci.getStentAdditional());
        buttonAddClear(accessRouteAdd, pci.getStentAdditional());
        if (pciVBox.getChildren().size() > 25)
            pciVBox.getChildren().subList(25, pciVBox.getChildren().size()).clear();

        pci.getAdditionalDetails().clear();
    }

    private void buttonAddClear(Button button, HashMap<String, String> map) {
        map.clear();
        VBox buttonVBox = (VBox) button.getParent().getParent();
        if (buttonVBox.getChildren().size() > 2)
            buttonVBox.getChildren().subList(2, buttonVBox.getChildren().size()).clear();
    }

    public void setData(PciModel pci) {
        bpField_1.setText(pci.getBp_1());
        bpField_2.setText(pci.getBp_2());
        bpField_3.setText(pci.getBp_3());
        hrField.setText(pci.getHr());
        syntaxScoreField.setText(pci.getSyntaxScore());
        catheterField.setText(pci.getCatheter());
        wireField.setText(pci.getWire());
        aspirationField.setText(pci.getThrombus());
        predilationField.setText(pci.getPredilation());
        postDilationField.setText(pci.getPostDilation());
        timiFlowField.setText(pci.getTimiFlow());
        timeFirstMedicalField.setText(pci.getTimeFirstMedical());
        dbtField.setText(pci.getDbt());
        complicationsField.setText(pci.getComplications());
        bifurcationField.setText(pci.getBifurcation());
        lmcaField.setText(pci.getLmca());
        specialBalloonsField.setText(pci.getSpecialBalloons());
        guideCatheterField.setText(pci.getGuideCatheter());
        ivusField.setText(pci.getIvusFindings());
        octField.setText(pci.getOctFindings());
        rotablation.setText(pci.getRotablation());
        finalImpression.setText(pci.getFinalImpression());
        this.accessRouteBox.getSelectionModel().select(pci.getAccessRoute());
        this.accessRouteValue.setText(pci.getAccessRouteField());
        this.stentBox.getSelectionModel().select(pci.getStent());
        this.stentValue.setText(pci.getStentField());
        this.lmcaDiseaseBox.getSelectionModel().select(pci.getLmcaDisease());
        this.lmcaDiseaseValue.setText(pci.getLmcaDiseaseField());
        this.bipurificationDiseaseBox.getSelectionModel().select(pci.getBipurificationDisease());
        this.bipurificationDiseaseValue.setText(pci.getBipurificationDiseaseField());

        addOptionView(stentAdd, pci.getStentAdditional(), DropdownData.stentList);
        this.pci.setStentAdditional(pci.getStentAdditional());

        addOptionView(accessRouteAdd, pci.getAccessRouteAdditional(), DropdownData.accessRouteList);
        this.pci.setAccessRouteAdditional(pci.getAccessRouteAdditional());

        this.dateOfProcedure.setValue(pci.getDateOfProcedure());
        this.pci.setAdditionalDetails(pci.getAdditionalDetails());
        addOptionView(pci.getAdditionalDetails());
    }

    public PciModel processResults() {

        LocalDate dop = dateOfProcedure.getValue();
        pci.setBp_1(bpField_1.getText().trim());
        pci.setBp_2(bpField_2.getText().trim());
        pci.setBp_3(bpField_3.getText().trim());
        pci.setHr(hrField.getText().trim());
        pci.setSyntaxScore(syntaxScoreField.getText().trim());
        pci.setCatheter(catheterField.getText().trim());
        pci.setWire(wireField.getText().trim());
        pci.setThrombus(aspirationField.getText().trim());
        pci.setPredilation(predilationField.getText().trim());
        pci.setPostDilation(postDilationField.getText().trim());
        pci.setTimiFlow(timiFlowField.getText().trim());
        pci.setTimeFirstMedical(timeFirstMedicalField.getText().trim());
        pci.setDbt(dbtField.getText().trim());
        pci.setComplications(complicationsField.getText().trim());
        pci.setBifurcation(bifurcationField.getText().trim());
        pci.setLmca(lmcaField.getText().trim());
        pci.setSpecialBalloons(specialBalloonsField.getText().trim());
        pci.setGuideCatheter(guideCatheterField.getText().trim());
        pci.setIvusFindings(ivusField.getText().trim());
        pci.setOctFindings(octField.getText().trim());
        pci.setRotablation(rotablation.getText().trim());
        pci.setFinalImpression(finalImpression.getText().trim());
        String accessRoute = accessRouteBox.getSelectionModel().getSelectedItem();
        String accessRouteField = accessRouteValue.getText().trim();
        pci.setAccessRoute(accessRoute);
        pci.setAccessRouteField(accessRouteField);

        String stent = stentBox.getSelectionModel().getSelectedItem();
        String stentField = stentValue.getText().trim();
        pci.setStent(stent);
        pci.setStentField(stentField);
        pci.setStentAdditional(this.pci.getStentAdditional());

        pci.setAccessRouteAdditional(this.pci.getAccessRouteAdditional());
        pci.setDateOfProcedure(dop);
        pci.setAdditionalDetails(this.pci.getAdditionalDetails());

        String lmcaDisease = lmcaDiseaseBox.getSelectionModel().getSelectedItem();
        String lmcaDiseaseField = lmcaDiseaseValue.getText().trim();
        pci.setLmcaDisease(lmcaDisease);
        pci.setLmcaDiseaseField(lmcaDiseaseField);

        String bipurificationDisease = bipurificationDiseaseBox.getSelectionModel().getSelectedItem();
        String bipurificationDiseaseField = bipurificationDiseaseValue.getText().trim();
        pci.setBipurificationDisease(bipurificationDisease);
        pci.setBipurificationDiseaseField(bipurificationDiseaseField);

        return pci;
    }

    public void initialize() {

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

        syntaxCalc.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("http://www.syntaxscore.org/calculator/start.htm"));
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
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

        stentBox.getItems().add("");
        stentBox.getSelectionModel().select("");
        stentBox.getItems().addAll(DropdownData.stentList);
        AutoComplete.autoCompleteComboBoxPlus(stentBox, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

        catheterField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        wireField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        aspirationField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        predilationField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        postDilationField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        timiFlowField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        timeFirstMedicalField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        dbtField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        complicationsField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        bifurcationField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        lmcaField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        specialBalloonsField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        guideCatheterField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        ivusField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        octField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        rotablation.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
        finalImpression.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());

        addDetailBtn.setOnAction(event -> addDetail(pci.getAdditionalDetails(), false, null, null, null));

        stentAdd.setOnAction(event -> addBtnClicked(stentAdd, pci.getStentAdditional(), false, null, null, null, DropdownData.stentList));

        accessRouteAdd.setOnAction(event -> addBtnClicked(accessRouteAdd, pci.getAccessRouteAdditional(), false, null, null, null, DropdownData.accessRouteList));
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

    private void addOptionView(Button button, HashMap<String, String> map, ArrayList<String> list) {
        map.forEach((k, v) -> {
            Dictionary<String, String> dictionary = new Hashtable<>() {{
                put("title", k);
                put("desc", v);
            }};
            optionView(button, dictionary, map, list);
        });
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

    private void addOptionView(HashMap<String, String> map) {
        map.forEach((k, v) -> {
            Dictionary<String, String> dictionary = new Hashtable<>() {{
                put("title", k);
                put("desc", v);
            }};
            optionView(dictionary, map);
        });
    }

    public void optionView(Dictionary<String, String> dictionary, HashMap<String, String> map) {

        GridPane hBox = new GridPane();

        Text titleText = new Text();
        Text descText = new Text();

        titleText.setText(dictionary.get("title"));
        descText.setText(dictionary.get("desc"));


        VBox titlePane = new VBox();
        VBox descPane = new VBox();

        titlePane.getStyleClass().add("key_label");
        descPane.getStyleClass().add("key_label");


        titleText.setWrappingWidth(150);
        descText.setWrappingWidth(250);
        titlePane.setPrefWidth(150);
        descPane.setPrefWidth(250);
        titlePane.setMaxWidth(150);
        descPane.setMaxWidth(350);

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
        Page2Controller.this.pciVBox.getChildren().add(hBox);

        editButton.setOnAction(event -> {
            map.remove(titleText.getText());
            addDetail(map, true, new Hashtable<>() {{
                put("title", titleText.getText());
                put("desc", descText.getText());
            }}, titleText, descText);
        });
        delButton.setOnAction(event -> {
            map.remove(titleText.getText());
            Page2Controller.this.pciVBox.getChildren().remove(hBox);
        });
    }

    public void addDetail(HashMap<String, String> map, boolean isEditing, Dictionary<String, String> editDictionary, Text title, Text desc) {
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(pciVBox.getScene().getWindow());
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


                    VBox titlePane = new VBox();
                    VBox descPane = new VBox();

                    titlePane.getStyleClass().add("key_label");
                    descPane.getStyleClass().add("key_label");


                    titleText.setWrappingWidth(150);
                    descText.setWrappingWidth(250);
                    titlePane.setPrefWidth(150);
                    descPane.setPrefWidth(250);
                    titlePane.setMaxWidth(150);
                    descPane.setMaxWidth(350);

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
                    Page2Controller.this.pciVBox.getChildren().add(hBox);

                    editButton.setOnAction(event -> {
                        map.remove(titleText.getText());
                        addDetail(map, true, new Hashtable<>() {{
                            put("title", titleText.getText());
                            put("desc", descText.getText());
                        }}, titleText, descText);
                    });
                    delButton.setOnAction(event -> {
                        map.remove(titleText.getText());
                        Page2Controller.this.pciVBox.getChildren().remove(hBox);
                    });

                }
            });
            Optional<ButtonType> result = dialog.showAndWait();


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
