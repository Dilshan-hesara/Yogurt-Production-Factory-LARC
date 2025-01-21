package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class UserDetailsNewPassW {

    @FXML
    private Button btnChangePass;

    @FXML
    private Label lblUserNama;

    @FXML
    private AnchorPane nextPage;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtUserName1;

    @FXML
    private PasswordField txtPassWord;

    @FXML
    private PasswordField txtREEnterPassWord;

    String UserName;
    @FXML
    void btnChangePass(ActionEvent event) throws IOException, SQLException {


        String password = txtPassWord.getText().trim();
        String reEnterPassword = txtREEnterPassWord.getText().trim();


        if ( password.isEmpty() || reEnterPassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Validation Error All fields are required!").show();
            return;
        }

        if (!password.matches("\\d+")) {
            new Alert(Alert.AlertType.ERROR, "Validation Error password only digits!").show();
            return;
        }


        if (!password.equals(reEnterPassword)) {
            new Alert(Alert.AlertType.ERROR, " Validation Error Passwords do not match! ").show();
            return;
        }


        String Password = txtPassWord.getText();

        nextPage.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserDetailsNewPassSave.fxml"));
        AnchorPane load = loader.load();
        UserDetailsNewPassSave pasworUserName = loader.getController();
        pasworUserName.setUserDetails(UserName,Password);

        Stage stage = (Stage) nextPage.getScene().getWindow();
        stage.setTitle("Reset Password");

        nextPage.getChildren().add(load);
    }



    public void setUserDetails(String userName) {
        UserName = userName;
        lblUserNama.setText(userName);
    }

    @FXML
    void Back(ActionEvent event) throws IOException {
        nextPage.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserDetailsFrom.fxml"));
        AnchorPane load = loader.load();



        Stage stage = (Stage) nextPage.getScene().getWindow();
        stage.setTitle("Use Details");

        nextPage.getChildren().add(load);
    }
}
