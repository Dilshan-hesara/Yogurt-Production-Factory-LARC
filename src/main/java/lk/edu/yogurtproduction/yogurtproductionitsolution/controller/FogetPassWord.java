package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.BOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.UserBO;

import java.io.IOException;
import java.sql.SQLException;

public class FogetPassWord {

    @FXML
    private Button btnSave;

    @FXML
    private AnchorPane nextPage;

    @FXML
    private TextField txtUserName;


   // UserModel userModel = new UserModel();

    UserBO userModel = (UserBO) BOFactroy.getInstance().getBO(BOFactroy.BOType.USER);


    @FXML
    void btnSaveRestPasss(ActionEvent event) throws IOException, SQLException {

        String UserName = txtUserName.getText();
        String regex = "^[a-zA-Z]+$";


        if (UserName == null || UserName.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Username cannot be empty").show();
            return;
        }
        if (!UserName.matches(regex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid username").show();
            return;
        }

        if (userModel.isValidUsername(UserName)) {
            nextPage.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FogetPassWordVerfy.fxml"));
            AnchorPane load = loader.load();


            Stage stage = (Stage) nextPage.getScene().getWindow();
            stage.setTitle("Very Email");


            FogetPassWordVerfyController passUser = loader.getController();
            passUser.setUserName(UserName);

            nextPage.getChildren().add(load);
        }else {
            new Alert(Alert.AlertType.ERROR, "Username does not exist").show();

        }


    }


}
