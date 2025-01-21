package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WelcomePage {

    @FXML
    private AnchorPane welcomePage;

    @FXML
    void logButtonWelocme(ActionEvent event) throws IOException {

        welcomePage.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        welcomePage.getChildren().add(load);

    }

    @FXML
    void exitButt(ActionEvent event) {
        System.exit(0);
    }
}
