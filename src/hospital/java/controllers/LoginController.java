package hospital.java.controllers;

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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

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

        assert userData != null;
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
<<<<<<< HEAD
//            Auth.setUserData(email_field.getText().trim(),
//                    Integer.parseInt(userData.get("id")));
=======
            // Auth.setUserData(email_field.getText().trim(),
            //         Integer.valueOf(userData.get("id")));
>>>>>>> 67117836d2a97fe10036dc06ddffb442394d1e9d

            try {
                Stage loginStage = (Stage) nav_register_btn.getScene().getWindow();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/hospital/resources/views/main.fxml"));
                Parent root = loader.load();
                primaryStage.setTitle("Hospital Database");
                primaryStage.setScene(new Scene(root, 1000, 800));
                primaryStage.getIcons().add(new Image(Objects.requireNonNull(LoginController.class.getResourceAsStream("/hospital/resources/images/logo.png"))));
                loginStage.close();
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("password does not match");
        }
    }

    @FXML
    private void nav_register() {
        try {
            Stage loginStage = (Stage) nav_register_btn.getScene().getWindow();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/hospital/resources/views/register.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Register");
            primaryStage.setScene(new Scene(root, 500, 600));
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(LoginController.class.getResourceAsStream("/hospital/resources/images/logo.png"))));
            loginStage.close();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}