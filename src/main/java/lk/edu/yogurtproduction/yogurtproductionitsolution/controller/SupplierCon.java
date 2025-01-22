package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.BOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.SupplierBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.SupplierDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.SupplierDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.SuplierDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.EmployeeTM;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.SuplierTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class SupplierCon implements Initializable {


    @FXML
    private TableView<SuplierTM> supTable;

    @FXML
    private Button addSupButt;

    @FXML
    private Button resetButt;

    @FXML
    private Button allReport;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnOpenMailSendModel;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<EmployeeTM, String> tbMail;

    @FXML
    private TableColumn<EmployeeTM, String> tbName;

    @FXML
    private TableColumn<EmployeeTM, String> tbNic;

    @FXML
    private TableColumn<EmployeeTM, Integer> tbPhone;

    @FXML
    private TableColumn<EmployeeTM, String> tbSupId;

   // SupplierDAO suplierModel = new SupplierDAOImpl();

    SupplierBO suplierModel = (SupplierBO) BOFactroy.getInstance().getBO(BOFactroy.BOType.SUPPLIER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tbSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        tbName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        tbNic.setCellValueFactory(new PropertyValueFactory<>("supNic"));
        tbPhone.setCellValueFactory(new PropertyValueFactory<>("supPhone"));
        tbMail.setCellValueFactory(new PropertyValueFactory<>("supEmail"));

        try {
            loadSuplierTable();
            btnDelete.setDisable(true);
            btnOpenMailSendModel.setDisable(true);
            btnUpdate.setDisable(true);
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load suplir data").show();
        }
    }

    void loadSuplierTable() throws Exception {
        ArrayList<SuplierDto>suplierDTOS =  suplierModel.getAll();
        ObservableList<SuplierTM> suplierTMS = FXCollections.observableArrayList();

        for(SuplierDto supDto : suplierDTOS) {
            SuplierTM suplierTM = new SuplierTM(
                    supDto.getSupId(),
                    supDto.getSupName(),
                    supDto.getSupNic(),
                    supDto.getSupEmail(),
                    supDto.getSupPhone()
            );
            suplierTMS.add(suplierTM);
        }
        supTable.setItems(suplierTMS);


    }
    @FXML
    void btnSupMail(ActionEvent event) {

        SuplierTM selectSup = supTable.getSelectionModel().getSelectedItem();

        if (selectSup != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SendMailSuplier.fxml"));
                if (loader.getLocation() == null) {
                    throw new IllegalStateException("FXML file not found.");
                }
                Parent load = loader.load();

                SendMailSupierController  sendMailSupierController = loader.getController();
                sendMailSupierController.sendMailData(selectSup);


                Stage stage = new Stage();
                stage.setScene(new Scene(load));
                stage.setTitle("Send Mail Suplier");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(btnUpdate.getScene().getWindow());
                Image image = new Image(getClass().getResourceAsStream("/images/49.png"));
                stage.getIcons().add(image);

                stage.showAndWait();
                stage.setResizable(false);

            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Fail to load UI: " + e.getMessage()).show();
                e.printStackTrace();
            } catch (IllegalStateException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select an suplier .").show();
        }

    }

    @FXML
    void butSupReport(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

             parameters.put("P_Date", LocalDate.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/SupplierOdredsFrom.jrxml"));

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
    void buttAddSup(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddSuplier.fxml"));
            Parent load = loader.load();

            AddSuplierController addSupCon = loader.getController();
            addSupCon.setSupFormCon(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Add Suplier");
            stage.setResizable(false);

            Image image = new Image(getClass().getResourceAsStream("/images/51.png"));
            stage.getIcons().add(image);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.initOwner(btnUpdate.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }
    }

    @FXML
    void buttDeleteSup(ActionEvent event) throws Exception {
        SuplierTM selecteSUP = supTable.getSelectionModel().getSelectedItem();

        String supId = selecteSUP.getSupId();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = suplierModel.delete (supId);
            if (isDeleted) {
                loadSuplierTable();
                new Alert(Alert.AlertType.INFORMATION, "Suplier deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Suplier...!").show();
            }
        }

    }

    @FXML
    void buttUpadeSup(ActionEvent event) {

        SuplierTM selectSup = supTable.getSelectionModel().getSelectedItem();
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateSuplier.fxml"));
            Parent load = loader.load();


            UpdateSuplier updateSupCon = loader.getController();
            updateSupCon.setSuplierData(selectSup);

            UpdateSuplier updateSuplierReloadTable = loader.getController();
            updateSuplierReloadTable.setSupierReloadTable(this);




            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Update Suplier");
            stage.setResizable(false);
            Image image = new Image(getClass().getResourceAsStream("/images/50.png"));
            stage.getIcons().add(image);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.initOwner(btnUpdate.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }
    }



    public void tblClik(javafx.scene.input.MouseEvent mouseEvent) {

        addSupButt.setDisable(true);
        btnDelete.setDisable(false);
        btnOpenMailSendModel.setDisable(false);
        btnUpdate.setDisable(false);
        allReport.setDisable(true);

    }

    @FXML
    void resetButt(ActionEvent event) {

        reset();

    }
    void reset(){
        addSupButt.setDisable(false);
        btnDelete.setDisable(true);
        btnOpenMailSendModel.setDisable(true);
        btnUpdate.setDisable(true);
        allReport.setDisable(false);


    }
}
