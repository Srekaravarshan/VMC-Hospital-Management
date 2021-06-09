package hospital;

import hospital.java.sources.Datasource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

<<<<<<< HEAD
//        if (Auth.isLoggedIn() || true) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/views/main.fxml"));
//            Parent root = loader.load();
//            primaryStage.setTitle("Hospital Database");
//            primaryStage.setScene(new Scene(root, 1000, 600));
//        } else {
=======
        // if (Auth.isLoggedIn()) {
        //     FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/views/main.fxml"));
        //     Parent root = loader.load();
        //     primaryStage.setTitle("Hospital Database");
        //     primaryStage.setScene(new Scene(root, 1000, 600));
        // } else {
>>>>>>> 67117836d2a97fe10036dc06ddffb442394d1e9d
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/views/login.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root, 500, 600));
<<<<<<< HEAD
//        }
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("resources/images/logo.png"))));
=======
        // }
        primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("resources/images/logo.png")));
>>>>>>> 67117836d2a97fe10036dc06ddffb442394d1e9d
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        if (!Datasource.instance.open()) {
            System.out.println("FATAL ERROR: Couldn't connect to database.");
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Datasource.instance.close();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
