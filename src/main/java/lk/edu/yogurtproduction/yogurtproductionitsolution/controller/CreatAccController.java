package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.UserDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class CreatAccController {

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassWord;

    @FXML
    private TextField txtREEnterPassWord;

    @FXML
    private TextField txtUserName;

    UserModel userModel = new UserModel();

    @FXML
    private AnchorPane nextAcc;

    @FXML
    void btnSaveAcc(ActionEvent event) {
        try {
            String username = txtUserName.getText().trim();
            String password = txtPassWord.getText().trim();
            String reEnterPassword = txtREEnterPassWord.getText().trim();
            String email = txtEmail.getText().trim();

            if (username.isEmpty() || password.isEmpty() || reEnterPassword.isEmpty() || email.isEmpty()) {
                showAlert("Validation Error", "All fields are required!");
                return;
            }


            else if (!username.matches("[a-zA-Z ]+")) {
                showAlert("Validation Error", " User name can Only Use Lettrs & sapes !");
                return;
            }

            if (!password.matches("\\d+")) {
                showAlert("Validation Error", " password only digits!");
                return;
            }


            if (!password.equals(reEnterPassword)) {
                showAlert("Validation Error", "Passwords do not match!");
                return;
            }


            if (!isValidEmail(email)) {
                showAlert("Validation Error", "Invalid email address format!");
                return;
            }

            UserDto user = new UserDto(username, password, email);


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MailVerfy.fxml"));
            AnchorPane load = loader.load();

            VerfyMailController verifyController = loader.getController();
            verifyController.setUserDetails(user);

            Stage stage = new Stage();
            stage.setTitle("Email Verification");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(load));
            stage.showAndWait();


            if (verifyController.isVerified()) {

                boolean isSaved = userModel.createUser(user);

                if (isSaved) {
                    showAlert("Success", "Account saved successfully!");
                } else {
                    showAlert("Error", "Failed to save account. Please try again.");
                }
            } else {
                showAlert("Error", "Email verification failed. Please try again.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while loading the email verification form.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error saving the account.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An unexpected error: " + e.getMessage());
        }
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
