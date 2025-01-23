package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.BOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.UserBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CreteAccDto;


import java.io.IOException;
import java.sql.SQLException;

public class CreateAccGetValController {

    @FXML
    private Button btnSave;

    @FXML
    private AnchorPane nextAcc;

    @FXML
    private TextField txtEmail;


    @FXML
    private TextField txtUserName;


    @FXML
    private PasswordField txtPassWord;

    @FXML
    private PasswordField txtREEnterPassWord;


    //UserModel userModel = new UserModel();

    UserBO userModel = (UserBO) BOFactroy.getInstance().getBO(BOFactroy.BOType.USER);


    @FXML
    void btnSaveAcc(ActionEvent event) throws IOException, SQLException {


        String username = txtUserName.getText().trim();
        String password = txtPassWord.getText().trim();
        String reEnterPassword = txtREEnterPassWord.getText().trim();
        String email = txtEmail.getText().trim();

        if (userModel.isValidUsername(username)) {
            showAlert("Validation Error", "Early exist!");
            return;


        }

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

        CreteAccDto creteAccDto = new CreteAccDto(
                username,
                password,
                email
        );
        nextAcc.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateAccVerfySave.fxml"));
        AnchorPane load = loader.load();

        CreateAccVerfySave senAccDetails = loader.getController();
        senAccDetails.sendAccDetails(creteAccDto);

        Stage stage = (Stage) nextAcc.getScene().getWindow();
        stage.setTitle("Create Account Very Mail");

        nextAcc.getChildren().add(load);

    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
