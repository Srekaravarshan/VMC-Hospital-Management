package hospital.java.controllers;

import java.io.IOException;
import java.net.URL;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class MainController {

    @FXML
    private BorderPane navPane;

    public void initialize() {
        AllPatientsController.listPatients();
        PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
        delay.setOnFinished(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/views/form.fxml"));
                navPane.setCenter(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }

    @FXML
    private void handleShowView(ActionEvent e) {
        String view = (String) ((Node) e.getSource()).getUserData();
        System.out.println(e.getSource());
        System.out.println(view);
        loadFXML(getClass().getResource(view));
    }

    private void loadFXML(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            navPane.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}