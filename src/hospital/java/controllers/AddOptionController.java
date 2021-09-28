package hospital.java.controllers;

import hospital.java.models.TabTraversal;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class AddOptionController {

    @FXML
    private TextField titleField;
    @FXML
    private Label errorText;

    @FXML
    private TextArea descField;

    public void initialize () {
        descField.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversal());
    }

    public boolean validate (HashMap<String, String > map, boolean isEditing, Text current) {
        boolean exists = map.containsKey(titleField.getText().trim());
        if ( isEditing ) {
            return current != null && (!(map.containsKey(titleField.getText())) || titleField.getText().trim().equals(current.getText()));
        }
        errorText.setVisible(exists);
        return !(exists);
    }

    public void setData (Dictionary<String, String> dictionary) {
        titleField.setText(dictionary.get("title"));
        descField.setText(dictionary.get("desc"));
    }

    public Dictionary<String, String> processResults () {
        Dictionary<String, String> dictionary = new Hashtable<>();
        dictionary.put("title", titleField.getText().trim());
        dictionary.put("desc", descField.getText().trim());
        return dictionary;
    }

}
