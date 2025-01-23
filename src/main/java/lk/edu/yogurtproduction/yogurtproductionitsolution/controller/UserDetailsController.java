package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.UserName;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserDetailsController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userName = UserName.getUsername();
        lblUserNama.setText(userName);

    }

    @FXML
    private Button btnChangePass;

    @FXML
    private Label lblUserNama;

    @FXML
    private AnchorPane nextPage;

    String userName;
    public void sendUserName(String userName) {
        this.userName = userName;
        lblUserNama.setText(userName);
    }

    @FXML
    void btnChangePass(ActionEvent event) throws IOException {

        nextPage.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserDetailsNewPassW.fxml"));
        AnchorPane load = loader.load();
        Image image = new Image(getClass().getResourceAsStream("/images/26.png"));


        UserDetailsNewPassW pasUserName = loader.getController();
        pasUserName.setUserDetails(userName);

        Stage stage = (Stage) nextPage.getScene().getWindow();
        stage.setTitle("Reset Password");
        stage.getIcons().add(image);

        nextPage.getChildren().add(load);
    }

    @FXML
    void Back(ActionEvent event) {
        closeWin();
    }

    private void closeWin() {
        Stage stage = (Stage) nextPage.getScene().getWindow();
        stage.close();
    }


}
