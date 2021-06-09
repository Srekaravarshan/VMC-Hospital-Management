package hospital.java.controllers;

import hospital.java.models.User;
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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

public class RegisterController {

    @FXML
    private Button nav_login_btn;
    @FXML
    private TextField email_field;
    @FXML
    private PasswordField password_field;

    @FXML
    private void register() {

        String password = null;
        try {
            password = Auth.encrypt(password_field.getText().trim());
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
                | IOException e) {
            e.printStackTrace();
        }
        
        User user = null;
        try {
            user = Datasource.instance.register(email_field.getText().trim(), password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        Auth.setUserData(email_field.getText().trim(), user.getId());

        try {
            Stage loginStage = (Stage) nav_login_btn.getScene().getWindow();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(RegisterController.class.getResource("/hospital/resources/views/main.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Hospital Database");
            primaryStage.setScene(new Scene(root, 1000, 800));
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(RegisterController.class.getResourceAsStream("/hospital/resources/images/logo.png"))));
            loginStage.close();
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
            System.out.println(e);
            e.printStackTrace();
        }

    }

    @FXML
    private void nav_login() {
        try {
            Stage loginStage = (Stage) nav_login_btn.getScene().getWindow();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(RegisterController.class.getResource("/hospital/resources/views/login.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Register");
            primaryStage.setScene(new Scene(root, 500, 600));
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(RegisterController.class.getResourceAsStream("/hospital/resources/images/logo.png"))));
            loginStage.close();
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
            System.out.println(e);
            e.printStackTrace();
        }
    }

}
