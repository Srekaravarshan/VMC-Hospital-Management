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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/views/login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.getIcons()
                .add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("resources/images/logo.png"))));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Datasource.instance.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
