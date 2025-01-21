package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.UserModel;

import java.sql.SQLException;

public class RestPassWordController {

    @FXML
    private Button btnSave;

    @FXML
    private PasswordField txtNewPassWord;

    @FXML
    private PasswordField txtOldPassWord;

    @FXML
    private PasswordField txtRenterNewPassWord;

    @FXML
    private TextField txtUserName;

    @FXML
    private AnchorPane nextPage;


    UserModel userModel = new UserModel();

    @FXML
    void btnSaveRestPasss(ActionEvent event) throws SQLException {
        String username = txtUserName.getText().trim();
        String oldPassword = txtOldPassWord.getText().trim();
        String newPassword = txtNewPassWord.getText().trim();
        String reenterPassword = txtRenterNewPassWord.getText().trim();

        if (username.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty() || reenterPassword.isEmpty()) {
            showAlert("Validation Error", "All fields are required!");
            return;
        }


        else if (!username.matches("[a-zA-Z ]+")) {
            showAlert("Validation Error", "User name can Only Use Lettrs & sapes !");
            return;
        }

        if (!newPassword.matches("\\d+")) {
            showAlert("Validation Error", "New password only digits!");
            return;
        }

        if (!newPassword.equals(reenterPassword)) {
            showAlert("Validation Error", "New passwords do not match!");
            return;
        }



        if (oldPassword.equals(newPassword)) {
            showAlert("Validation Error", "Old and new passwords cannot be the same!");
            return;
        }



        if (userModel.isValidUsername(username)) {

            if (userModel.isValidUser(username, oldPassword)) {

                if (newPassword.equals(reenterPassword)) {

                    boolean isUpdated = userModel.updatePassword(username, newPassword);

                    if (isUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, "Password updated successfully!").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Password update failed!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "New password and re-entered password do not match!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Incorrect old password!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Username does not exist").show();
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
