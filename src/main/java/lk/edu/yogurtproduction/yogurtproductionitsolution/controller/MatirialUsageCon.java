package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.BOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.MaterialUsageBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.MaterialUsageDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.MaterialUsageDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialUsageDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.MatirialUsageTM;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.MatirialUsageModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatirialUsageCon {

    @FXML
    private TableColumn<String, MatirialUsageTM> cloMatID;

    @FXML
    private TableColumn<String, MatirialUsageTM> cloMilk;

    @FXML
    private TableColumn<String, MatirialUsageTM> colGela;

    @FXML
    private TableColumn<String, MatirialUsageTM> colProdID;

    @FXML
    private TableColumn<String, MatirialUsageTM> colSu;

    @FXML
    private TableView<MatirialUsageTM> tblMatUsage;


    public void initialize(){
        cloMatID.setCellValueFactory(new PropertyValueFactory<>("MatUs_ID"));
        colProdID.setCellValueFactory(new PropertyValueFactory<>("Prod_ID"));
        cloMilk.setCellValueFactory(new PropertyValueFactory<>("Mat_Milk"));
        colSu.setCellValueFactory(new PropertyValueFactory<>("Mat_Suguer"));
        colGela.setCellValueFactory(new PropertyValueFactory<>("Mat_Gelatin"));

        try {
            loadTble();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    MaterialUsageBO materialUsageBO =(MaterialUsageBO) BOFactroy.getInstance().getBO(BOFactroy.BOType.MATERIAL_USAGE);
  //  MatirialUsageModel matirialUsageModel = new MatirialUsageModel();
 // MaterialUsageDAO matirialUsageModel = new MaterialUsageDAOImpl();

    private void loadTble() throws SQLException, ClassNotFoundException {

        ArrayList<MatirialUsageDto> matirialUsageDTOS = materialUsageBO.getAll();

        ObservableList<MatirialUsageTM> matirialUsageTMS = FXCollections.observableArrayList();


        for (MatirialUsageDto matirialUsageDto : matirialUsageDTOS) {
            MatirialUsageTM matirialUsageTM = new MatirialUsageTM(
                    matirialUsageDto.getMatUs_ID(),
                    matirialUsageDto.getProd_ID(),
                    matirialUsageDto.getMat_Milk(),
                    matirialUsageDto.getMat_Suguer(),
                    matirialUsageDto.getMat_Gelatin()

            );
            matirialUsageTMS.add(matirialUsageTM);
        }

        tblMatUsage.setItems(matirialUsageTMS);


    }

    public void btnALLReportIN(javafx.event.ActionEvent actionEvent) {
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
}
