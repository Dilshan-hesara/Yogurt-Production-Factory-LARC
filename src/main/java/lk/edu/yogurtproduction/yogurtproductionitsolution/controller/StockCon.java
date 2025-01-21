package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.StockDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.StockTM;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.StockModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class StockCon implements Initializable {


    @FXML
    private TableColumn<String, StockTM> colExDate;

    @FXML
    private TableColumn<String, StockTM> colPacID;

    @FXML
    private TableColumn<String, StockTM> colPackType;

    @FXML
    private TableColumn<String, StockTM> colProdName;

    @FXML
    private TableColumn<String, StockTM> colQty;

    @FXML
    private TableColumn<String, StockTM> colStockID;

    @FXML
    private TableColumn<String, StockTM> coldate;

    @FXML
    private TableView<StockTM> tblStoclk;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colStockID.setCellValueFactory(new PropertyValueFactory<>("Stock_ID"));
        colPacID.setCellValueFactory(new PropertyValueFactory<>("Pac_ID"));
        colProdName.setCellValueFactory(new PropertyValueFactory<>("Product_Name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colPackType.setCellValueFactory(new PropertyValueFactory<>("Pack_Type"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("Manfac_date"));
        colExDate.setCellValueFactory(new PropertyValueFactory<>("Expire_date"));



        try {
            loadTble();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    StockModel stockModel = new StockModel();

    private void loadTble() throws SQLException {

        ArrayList<StockDto> stockDTOS = stockModel.getAllStockData();

        ObservableList<StockTM> stockTMS = FXCollections.observableArrayList();


        for (StockDto stockDto : stockDTOS) {
            StockTM stockTM = new StockTM(
                    stockDto.getStock_ID(),
                    stockDto.getPac_ID(),
                    stockDto.getProduct_Name(),
                    stockDto.getQty(),
                    stockDto.getPack_Type(),
                    stockDto.getManfac_date(),
                    stockDto.getExpire_date()
            );
            stockTMS.add(stockTM);
        }

        tblStoclk.setItems(stockTMS);

    }

    public void reoprtBtn(javafx.event.ActionEvent actionEvent) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();


            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/stock.jrxml"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );


            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load report..!");
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Data empty..!");
            e.printStackTrace();
        }

    }
}
