package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DashBoad implements Initializable {

    @FXML
    private AnchorPane mainAn;
    @FXML
    private AnchorPane nextPage;


    String UserName;
    public void setUserName(String us) {
        this.UserName = us;
    }


    @FXML
    void dashBoadButt(ActionEvent event) throws IOException {

        nextPage.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DahBoadMain.fxml"));
        AnchorPane dashboardPane = loader.load();

        DashBoadMain dashMain = loader.getController();
        dashMain.setUserName(UserName);

        nextPage.getChildren().add(dashboardPane);

    }


    @FXML
    void ordersButt(ActionEvent event) {
        navigateTo("/view/OrdersFrom.fxml");


    }


    public void emploButt(ActionEvent actionEvent) {
        navigateTo("/view/EmployeeForm.fxml");

    }

    @FXML
    void PackingButt(ActionEvent event) {
        navigateTo("/view/PackingForm.fxml");

    }



    @FXML
    void invetroyButt(ActionEvent event) {
        navigateTo("/view/InventryForm.fxml");

    }

    @FXML
    void matUsageButt(ActionEvent event) {
        navigateTo("/view/MatirialUsage.fxml");

    }

    @FXML
    void matirialButt(ActionEvent event) {
        navigateTo("/view/MatirialForm.fxml");

    }

    @FXML
    void prodtionButt(ActionEvent event) {
        navigateTo("/view/ProdtionForm.fxml");

    }

    @FXML
    void stockButt(ActionEvent event) {
        navigateTo("/view/StockForm.fxml");

    }

    @FXML
    void supplierButt(ActionEvent event) {
        navigateTo("/view/SupplierForm.fxml");

    }




    @FXML
    void cashBookButt(ActionEvent event) {
        navigateTo("/view/CashBForm.fxml");

    }

    @FXML
    void prodMixButt(ActionEvent event) {
        navigateTo("/view/ResipeFrom.fxml");
    }


    public void navigateTo(String fxmlPath) {
        try {
            nextPage.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));


            load.prefWidthProperty().bind(nextPage.widthProperty());
            load.prefHeightProperty().bind(nextPage.heightProperty());


            nextPage.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {

        mainAn.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/WelcomePage.fxml"));
        mainAn.getChildren().add(load);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigateTo("/view/DahBoadMain.fxml");


    }
}


