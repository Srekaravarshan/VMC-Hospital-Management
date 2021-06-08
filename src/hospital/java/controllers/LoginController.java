package hospital.java.controllers;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import hospital.java.sources.Auth;
import hospital.java.sources.Datasource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button nav_register_btn;
    @FXML
    private TextField email_field;
    @FXML
    private PasswordField password_field;

    @FXML
    private void login() {
        HashMap<String, String> userData = null;
        try {
            userData = Datasource.instance.getEnPassword(email_field.getText().trim());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (userData.get("enpass") == null) {
            return;
        }
        boolean matches = false;
        try {
            matches = Auth.matches(password_field.getText().trim(), userData.get("enpass"));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
                | NoSuchPaddingException | IOException e) {
            e.printStackTrace();
        }

        if (matches) {
            // Auth.setUserData(email_field.getText().trim(),
            //         Integer.valueOf(userData.get("id")));

            try {
                Stage loginStage = (Stage) nav_register_btn.getScene().getWindow();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/views/main.fxml"));
                Parent root = loader.load();
                primaryStage.setTitle("Hospital Database");
                primaryStage.setScene(new Scene(root, 500, 600));
                primaryStage.getIcons().add(new Image(LoginController.class.getResourceAsStream("../../resources/images/logo.png")));
                loginStage.close();
                primaryStage.show();
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
                System.out.println(e.getMessage());
                System.out.println(e.toString());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void nav_register() {
        try {
            Stage loginStage = (Stage) nav_register_btn.getScene().getWindow();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/views/register.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Register");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.getIcons().add(new Image(LoginController.class.getResourceAsStream("../../resources/images/logo.png")));
            loginStage.close();
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

}