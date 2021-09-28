package hospital.java.controllers;

import hospital.java.models.SharedPref;
import hospital.java.sources.Datasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController {

    @FXML
    private TextField email_field;
    @FXML
    private Button ipAddressBtn;
    @FXML
    private PasswordField password_field;
    @FXML
    private Label login_error, ipLabel, ipLabelInfo;

    @FXML
    public void initialize () {
        updateUIForIP(SharedPref.instance.hasIP());
    }

    @FXML
    private void login() {
        if (email_field.getText().trim().equals("velammal") && password_field.getText().trim().equals("Vcet@")) {

            try {
                Datasource.setIpAddress(SharedPref.instance.getPreference());

                if (!Datasource.instance.checkIsConnected()) {
                    Datasource.instance.open();
                }

                login_error.setText("");
                Stage loginStage = (Stage) login_error.getScene().getWindow();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader(
                        LoginController.class.getResource("/hospital/resources/views/main.fxml"));
                Parent root = loader.load();
                primaryStage.setTitle("Hospital Database");
                Rectangle2D screenBounds = Screen.getPrimary().getBounds();
                primaryStage.setScene(new Scene(root, 1000, screenBounds.getHeight() - 50));
                primaryStage.getIcons().add(new Image(Objects.requireNonNull(
                        LoginController.class.getResourceAsStream("/hospital/resources/images/logo.png"))));

                loginStage.close();
                primaryStage.show();
            } catch (IOException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                login_error.setText("Unable to connect Database");
            }
        } else {
            login_error.setText("Username or Password is wrong.");
            System.out.println("password does not match");
        }

    }

    @FXML
    private void setIPAddress() {

        if(SharedPref.instance.hasIP() ) {
            SharedPref.instance.setPreference("0.0.0.0");
            updateUIForIP(false);
            return;
        }

        TextInputDialog td = new TextInputDialog("");
        td.setHeaderText("Enter IP Address");
        td.setTitle("IP Address");

        final Button okButton = (Button) td.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, ae -> {
            if (!isValid(td.getEditor().getText())) {
                ae.consume(); //not valid
            } else {
                SharedPref.instance.setPreference(td.getEditor().getText());
                updateUIForIP(true);
            }
        });

        td.showAndWait();

    }

    private boolean isValid (String ip) {

        String zeroTo255
                = "(\\d{1,2}|([01])\\"
                + "d{2}|2[0-4]\\d|25[0-5])";

        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;

        Pattern p = Pattern.compile(regex);

        if (ip == null) {
            return false;
        }
        Matcher m = p.matcher(ip);
        return m.matches() && !ip.equals("0.0.0.0");
    }

    private void updateUIForIP (boolean setIP) {

        if (setIP) {
            ipLabel.setText("IP Address have saved");
            ipLabelInfo.setText("Connect database remotely");
            ipAddressBtn.setText("Remove IP Address");
        } else {
            ipLabel.setText("IP Address haven't set");
            ipLabelInfo.setText("Accessing database locally");
            ipAddressBtn.setText("Set IP Address");
        }
    }

}