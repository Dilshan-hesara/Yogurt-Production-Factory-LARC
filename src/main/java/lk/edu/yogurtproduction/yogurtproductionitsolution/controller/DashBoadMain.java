package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.CashBookDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.MaterialUsageDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.StockDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.CashBookDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.MaterialUsageDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.StockDAOImpl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.UserName;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DashBoadMain implements Initializable {


        @FXML
        private PieChart chartProduction;

        @FXML
        private AreaChart<String, Number> chartStock;

        @FXML
        private Text txtDate;

        @FXML
        private Text txtTime;

        @FXML
        private Text txtUser;

    @FXML
    private Label lblMatUsage;

    @FXML
    private Label lblProd;

    @FXML
    private Label lblbAmount;

    void displayUsername() {
        String username = UserName.getUsername();
            txtUser.setText(username);
            userName = UserName.getUsername();
        }


    @SneakyThrows
    @Override
        public void initialize(URL location, ResourceBundle resources) {
        
        loadChartData();
        startClock();
        addYogurtStockData();
        displayUsername();

        try {
            LoadLbl();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    String userName;

    public void setUserName(String userName) {

        this.userName = userName;
        txtUser.setText(userName);


    }


    private void LoadLbl() throws SQLException {
        laodAount();
        laodMatUsage();
        loadprod();
 
    }

    @FXML
    private Label lblProdAv;

    StockDAO stockModel = new StockDAOImpl();

    private void loadprod() throws SQLException {
        int pr = (int) stockModel.getAllProdAvg();
        String prd = pr +" %";
        lblProdAv.setText(String.valueOf(prd));
    }

    MaterialUsageDAO matirialUsageModel = new MaterialUsageDAOImpl();

    //    MatirialUsageModel matirialUsageModel = new MatirialUsageModel();
    private void laodMatUsage() throws SQLException {
        Double us = (double) matirialUsageModel.getAllUsageAvg();
        String usa = us+" %";
        lblMatUsage.setText(String.valueOf(usa));

    }

    CashBookDAO cashBookModel = new CashBookDAOImpl();
    private void laodAount() throws SQLException {
        int am = cashBookModel.getAllPayAmount();
        String Am = "LKR."+am;
        lblbAmount.setText(String.valueOf(Am));

    }

    @FXML
    private Button btnAccDe;

    public void btnEditAcc(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserDetailsFrom.fxml"));
            Parent load = loader.load();

            Image image = new Image(getClass().getResourceAsStream("/images/25.png"));


            UserDetailsController sendUSERnAME = loader.getController();
            sendUSERnAME.sendUserName(userName);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.getIcons().add(image);
            stage.setResizable(false);
            stage.setTitle("User Details");

            stage.initOwner(btnAccDe.getScene().getWindow());


            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load UI..!").show();
            e.printStackTrace();
        }
    }


    public void loadChartData() {

        try {
            String query = "select Item_Type, sum(Qty) as TotalQty from inventory where Item_Type in ('Raw', 'UN Packed', 'END Prodt') group by Item_Type";

            ResultSet rs = SQLUtil.execute(query);

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            while (rs.next()) {
                String itemType = rs.getString("Item_Type");
                int totalQty = rs.getInt("TotalQty");

                pieChartData.add(new PieChart.Data(itemType , totalQty));
            }

            chartProduction.setData(pieChartData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
    private SimpleDateFormat daterun = new SimpleDateFormat("MMMM dd, yyyy");

    private void updateDateLabel() {


        String currentTime = dateFormat.format(new Date()).toUpperCase();

        txtTime.setText(currentTime);
        String currentdate = daterun.format(new Date());
        txtDate.setText(currentdate);
    }

    private void startClock() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateDateLabel()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }



    private void addYogurtStockData() {

        try {
            String Qury = "SELECT Manfac_date, SUM(Qty) AS total_qty " +
                    "FROM Stock " +
                    "GROUP BY Manfac_date " +
                    "ORDER BY Manfac_date";

            ResultSet rs = SQLUtil.execute(Qury);

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis(0, 12, 1);
            AreaChart<String, Number> areaChart = new AreaChart<>(xAxis, yAxis);

            XYChart.Series<String, Number> yogurtStockSeries = new XYChart.Series<>();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            while (rs.next()) {
                Date manfacDate = rs.getDate("Manfac_date");
                double totalQty = rs.getDouble("total_qty");
                String formattedDate = dateFormat.format(manfacDate);
               yogurtStockSeries.getData().add(new XYChart.Data<>(formattedDate, totalQty));
            }

           areaChart.getData().add(yogurtStockSeries);

            chartStock.getData().clear();
           chartStock.getData().addAll(yogurtStockSeries);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}




