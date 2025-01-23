package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.BOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.UserBO;


import java.io.IOException;
import java.sql.SQLException;

public class FogetPassWordSave {

    @FXML
    private Button VeffiMail;

    @FXML
    private AnchorPane nextPage;

    @FXML
    private Label txtMail;

    @FXML
    private PasswordField txtNewpassord;

    @FXML
    private Label txtUser;

    @FXML
    private PasswordField txtnewpassword;

  //  UserModel userModel = new UserModel();

    UserBO userModel = (UserBO) BOFactroy.getInstance().getBO(BOFactroy.BOType.USER);


    @FXML
    void VeffiMail(ActionEvent event) throws SQLException {
        System.out.println(GetUseName);

        String usename = GetUseName;
        String newpass = txtnewpassword.getText();
        String rePassword = txtNewpassord.getText();


        if ( newpass.isEmpty() || rePassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Validation Error All fields are required!").show();
            return;
        }

        if (!newpass.matches("\\d+")) {
            new Alert(Alert.AlertType.ERROR, "Validation Error password only digits!").show();
            return;
        }


        if (!newpass.equals(rePassword)) {
            new Alert(Alert.AlertType.ERROR, " Validation Error Passwords do not match! ").show();
            return;
        }

        boolean isUpdateUse = userModel.UpdateUser(usename,newpass);
        if (isUpdateUse) {
            new Alert(Alert.AlertType.INFORMATION, "PassWord Save Susses..!").show();

            closeCurrentWindow();

        }else{
            new Alert(Alert.AlertType.ERROR, "Failed Save Password..!").show();

        }

    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) nextPage.getScene().getWindow();
        stage.close();

    }


    @FXML
    void VeffiMailExit(ActionEvent event) throws IOException {
        nextPage.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FogetPassWord.fxml"));
        AnchorPane load = loader.load();

        Stage stage = (Stage) nextPage.getScene().getWindow();
        stage.setTitle("Foget Password");

        nextPage.getChildren().add(load);

    }


    String GetUseName;
    String GetMail;

    public void userName(String userNmae) throws SQLException {
        this.GetUseName = userNmae;
        GetMail=  userModel.GetUserMail(userNmae);
        txtMail.setText(GetMail);
        txtUser.setText(GetUseName);

    }
}
