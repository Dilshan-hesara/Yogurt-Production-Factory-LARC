package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.BOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.UserBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.UserName;

import java.io.IOException;
import java.sql.SQLException;

public class LogingPage{




    @FXML
    private AnchorPane logpage;


    @FXML
    private PasswordField txtPass;

    @FXML
    private TextField txtUser;


   // private final UserModel userModel = new UserModel();

    UserBO userModel = (UserBO) BOFactroy.getInstance().getBO(BOFactroy.BOType.USER);





    @FXML
    void dashBoadButt(ActionEvent event) throws IOException, SQLException {
        String username = txtUser.getText().trim();
        String password = txtPass.getText().trim();



        if (userModel.isValidUsername(username)) {
            if (userModel.isValidUser(username, password)) {
                UserName.setUsername(txtUser.getText());

                logpage.getChildren().clear();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoad.fxml"));
                AnchorPane dashboardPane = loader.load();

                DashBoad dashController = loader.getController();
                dashController.setUserName(username);
                laodUserName();

                logpage.getChildren().add(dashboardPane);

            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid username or password").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Username does not exist").show();
        }
    }




    @FXML
    void showi(MouseEvent event) {}

    @FXML
    void showPass(ActionEvent event) {

    }

    private void laodUserName() {
    }



    @FXML
    void backButton(ActionEvent event) throws IOException {

        logpage.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/WelcomePage.fxml"));
        logpage.getChildren().add(load);
    }

    @FXML
    private Button btncreateAcc;
    @FXML
    private Label restPssword;

    @FXML
    private AnchorPane nextAcc;


    @FXML
    void CreatAcc(ActionEvent event) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateAccGetVal.fxml"));

            Parent load = loader.load();


            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Create Account");


            Image image = new Image(getClass().getResourceAsStream("/images/43.png"));
            stage.getIcons().add(image);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.initOwner(btncreateAcc.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }

    }


    public void resetPassword(MouseEvent mouseEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RestPassWord.fxml"));
            Parent load = loader.load();


            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Rest Password");

            Image image = new Image(getClass().getResourceAsStream("/images/44.png"));
            stage.getIcons().add(image);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.initOwner(restPssword.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }
    }




    @FXML
    private Label fogetPassword;

    @FXML
    void fogetPassword(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FogetPassWord.fxml"));
        Parent load = loader.load();


        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.setTitle("Foget Password");
        Image image = new Image(getClass().getResourceAsStream("/images/45.png"));
        stage.getIcons().add(image);

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.initOwner(restPssword.getScene().getWindow());
        stage.showAndWait();

    }


}
