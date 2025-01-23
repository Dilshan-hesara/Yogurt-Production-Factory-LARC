package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.BOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.InventroyBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.InventroyDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.InventroyDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.InventroyDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.InventryTM;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.InventroyModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class InventryCon implements Initializable {

    @FXML
    private TableColumn<String, InventryTM> colDesc;

    @FXML
    private TableColumn<String, InventryTM> colInID;

    @FXML
    private TableColumn<String, InventryTM> colQty;

    @FXML
    private TableColumn<String, InventryTM> colType;

    @FXML
    private TableView<InventryTM> tblInventroy;

    @FXML
    private Button btnUsage;


    @FXML
    void btnMatUsageReportIN(ActionEvent event) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();


            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/material_usage.jrxml"));

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


    @FXML
    void btnUsageOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MatirialUsage.fxml"));
            Parent load = loader.load();


            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Material Usage");
            stage.setResizable(false);

            Image image = new Image(getClass().getResourceAsStream("/images/23.png"));
            stage.getIcons().add(image);

            stage.initOwner(btnUsage.getScene().getWindow());


            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load UI..!").show();
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        colInID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        try {
            loadTble();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    InventroyBO inventroyBO =(InventroyBO) BOFactroy.getInstance().getBO(BOFactroy.BOType.INVENTROY);

    private void loadTble() throws SQLException, ClassNotFoundException {
        ArrayList<InventroyDto> inventryDTOS = inventroyBO.getAll();

        ObservableList<InventryTM> inventryTMS = FXCollections.observableArrayList();


        for (InventroyDto inventroyDto : inventryDTOS) {
            InventryTM inventryTM = new InventryTM(
                    inventroyDto.getId(),

                    inventroyDto.getItemType(),

                    inventroyDto.getItemDescription(),
                    inventroyDto.getQty(),
                    inventroyDto.getProdId()
            );
            inventryTMS.add(inventryTM);
        }

        tblInventroy.setItems(inventryTMS);

    }




    @FXML
    void btnALLReportIN(ActionEvent event) throws SQLException {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();


            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/inventory.jrxml"));

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
