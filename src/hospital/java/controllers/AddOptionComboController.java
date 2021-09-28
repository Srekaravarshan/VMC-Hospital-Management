package hospital.java.controllers;

import hospital.java.models.AutoComplete;
import hospital.java.models.TabTraversal;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class AddOptionComboController {
    @FXML
    private ComboBox<String> titleField;
    @FXML
    private Label errorText;

    @FXML
    private TextArea descField;

    public void initialize () {
        descField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
    }

    public boolean validate (HashMap<String, String > map, boolean isEditing, Text current) {
        boolean exists = map.containsKey(titleField.getSelectionModel().getSelectedItem());
        if ( isEditing ) {
            return current != null && (!(map.containsKey(titleField.getSelectionModel().getSelectedItem())) || titleField.getSelectionModel().getSelectedItem().equals(current.getText()));
        }
        errorText.setVisible(exists);
        return !(exists);
    }

    public void addItems (ArrayList<String> list) {
        titleField.getItems().addAll(list);
        titleField.getSelectionModel().selectFirst();
        AutoComplete.autoCompleteComboBoxPlus(titleField, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

    }

    public void setData (Dictionary<String, String> dictionary) {
        titleField.getSelectionModel().select(dictionary.get("title"));
        descField.setText(dictionary.get("desc"));
    }

    public Dictionary<String, String> processResults () {
        Dictionary<String, String> dictionary = new Hashtable<>();
        dictionary.put("title", titleField.getSelectionModel().getSelectedItem());
        dictionary.put("desc", descField.getText().trim());
        return dictionary;
    }
}
