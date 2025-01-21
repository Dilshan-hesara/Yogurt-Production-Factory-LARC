package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.InventroyDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialUsageDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdMixDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdtionDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.ProdtionTM;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.InventroyModel;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.MatirialUsageModel;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.ProdMixModel;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.ProdtionModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProdtionCon {



    @FXML
    private ComboBox<String> cmbProdt;

    @FXML
    private Label lblProdID;

    @FXML
    private TextField txtQty;


    @FXML
    private Label jeliy;

    @FXML
    private Label lblMilk;

    @FXML
    private Label lblsuguer;

    @FXML
    private Label lblMilkAV;


    @FXML
    private Label lblgeliyAV;


    @FXML
    private Label lblsuguerAV;

    @FXML
    private TableColumn<String, ProdtionTM> colProName;

    @FXML
    private TableColumn<String, ProdtionTM> colProdID;

    @FXML
    private TableColumn<String, ProdtionTM> colQty;

    @FXML
    private TableColumn<String, ProdtionTM> colRecipe;

    @FXML
    private TableView<ProdtionTM> tblProdtion;

    @FXML
    private Button btnAddResipe;


ProdtionModel model = new ProdtionModel();
ProdMixModel prodMix = new ProdMixModel();
    public void initialize() throws SQLException {

        loadnextProdID();
        loadProdName();
        loadNextInventryId();
       loadNextmatirialUsageId();
        setCellVlause();
    }

    private void setCellVlause() {

        colProdID.setCellValueFactory(new PropertyValueFactory<>("Prod_ID"));
        colProName.setCellValueFactory(new PropertyValueFactory<>("Pro_Name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Prod_Qty"));
        colRecipe.setCellValueFactory(new PropertyValueFactory<>("Prod_Name"));

        try {
            loadTble();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
ProdtionModel prodtionModel = new ProdtionModel();
    private void loadTble() throws SQLException {

        ArrayList<ProdtionDto> prodtionDTOS = prodtionModel.getAllProdtionData();

        ObservableList<ProdtionTM> prodtionTMS = FXCollections.observableArrayList();


        for (ProdtionDto prodtionDto : prodtionDTOS) {
            ProdtionTM prodtionTM = new ProdtionTM(
                    prodtionDto.getProd_ID(),
                    prodtionDto.getPro_Name(),
                    prodtionDto.getProd_Qty(),
                    prodtionDto.getProd_Name()
            );
            prodtionTMS.add(prodtionTM);
        }

        tblProdtion.setItems(prodtionTMS);

    }

    private int DBAVMilk;
    private int DBAVSuguer;
    private int DBAVGelitin;

    private void loadAvelbItem() {
        try {
            ArrayList<String> avbleItem = inventroyModel.getAllAVItems();

            lblgeliyAV.setText(avbleItem.get(0));
            lblMilkAV.setText(avbleItem.get(1));
            lblsuguerAV.setText(avbleItem.get(2));

            DBAVMilk = Integer.parseInt(lblMilkAV.getText());
            DBAVSuguer = Integer.parseInt(lblsuguerAV.getText());
            DBAVGelitin = Integer.parseInt(lblgeliyAV.getText());


        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load available items.").show();
        }

    }


    private void loadnextProdID() throws SQLException {
        String nextProdtID = model.getNextProdtID();
        lblProdID.setText(nextProdtID);
        System.out.println(nextProdtID);


    }
    private void loadProdName() throws SQLException {
        ArrayList<String> prodName = prodMix.getAllProdName();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(prodName);
        cmbProdt.setItems(observableList);
    }

    @FXML
    private TextField txtProdtName;

    @FXML
    void btnAddPro(ActionEvent event) throws SQLException {

        String selectedProdt = cmbProdt.getSelectionModel().getSelectedItem();


        ArrayList<InventroyDto> inventroyDTOS = new ArrayList<>();
        ArrayList<ProdMixDto> prodMixDTOS = new ArrayList<>();
        ArrayList<MatirialUsageDto> matirialUsageDTOS = new ArrayList<>();

        if (selectedProdt == null) {
            new Alert(Alert.AlertType.ERROR, "Please select Prodtion Rescipe..!").show();
            return;
        }

        txtProdtName.setStyle(txtProdtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtQty.setStyle(txtQty.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String Dese = txtProdtName.getText().trim();

        String qtyPattern = "^[1-9]\\d*$";
        String txtqty = txtQty.getText().trim();

        boolean isValidDesc = Dese.matches(namePattern);
        boolean isValidQty = txtqty.matches(qtyPattern);

        if (!isValidDesc) {
            txtProdtName.setStyle(txtProdtName.getStyle() + ";-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Production Name can only contain letters and spaces!").show();
            return;
        }

        if (!isValidQty) {
            txtQty.setStyle(txtQty.getStyle() + ";-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Qty Cant 0 or Only Intiger").show();
            return;
        }

        txtProdtName.setStyle(txtProdtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtQty.setStyle(txtQty.getStyle() + ";-fx-border-color: #7367F0;");




        String Prod_ID =     lblProdID.getText();
        String Prod_Name = cmbProdt.getSelectionModel().getSelectedItem();
        String Pro_Name =  txtProdtName.getText();
        double Prod_Qty = Integer.parseInt(txtQty.getText());

         int P_milk = (int) (milk * Prod_Qty);
         int p_suguer = (int) (suguer * Prod_Qty);
         int p_jeley = (int) (jeley * Prod_Qty);
         String prodName = "prodtionResipi";

         String MatUs_ID = mtID;
         String Mat_Milk = String.valueOf(P_milk);
         String Mat_Suguer = String.valueOf(p_suguer);
         String Mat_Gelatin = String.valueOf(p_jeley);
        if (DBAVMilk < P_milk) {
            new Alert(Alert.AlertType.ERROR, "Not enough Milk available!").show();
            return;
        }

        if (DBAVSuguer < p_suguer) {
            new Alert(Alert.AlertType.ERROR, "Not enough Sugar available!").show();
            return;
        }

        if (DBAVGelitin < p_jeley) {
            new Alert(Alert.AlertType.ERROR, "Not enough Gelatin available!").show();
            return;
        }


        MatirialUsageDto matirialUsageDTO  = new MatirialUsageDto(

                     MatUs_ID,
                     Prod_ID,
                     Mat_Milk,
                     Mat_Suguer,
                     Mat_Gelatin


        );
        matirialUsageDTOS.add(matirialUsageDTO);

         ProdMixDto prodMixDTO  = new ProdMixDto(
                  prodName,
                 P_milk,
                 p_suguer,
                 p_jeley


        );
        prodMixDTOS.add(prodMixDTO);


         String InID = invID;
        String itemType = "UN Packed";
        String itemDescription =Pro_Name;
        String prodId = Prod_ID;
        String Qty = String.valueOf(Prod_Qty);

        InventroyDto inventroyDTO  = new InventroyDto(
                InID,
                itemType,
                itemDescription,
                Qty,
                prodId


        );
        inventroyDTOS.add(inventroyDTO);

        ProdtionDto prodtionDto = new ProdtionDto(
                Prod_ID,
                Pro_Name,
                Prod_Qty,
                Prod_Name,
                inventroyDTOS,
                prodMixDTOS,
                matirialUsageDTOS

        );
        boolean isSaved = model.saveProdt(prodtionDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, " saved..!").show();
            cleFi();


        } else {
            new Alert(Alert.AlertType.ERROR, " fail..!").show();
        }


    }

    String invID;
    InventroyModel inventroyModel = new InventroyModel();

    public void loadNextInventryId() throws SQLException {
        String nextInventryId = inventroyModel.getInventroyId();
        invID = nextInventryId;
        System.out.println(nextInventryId);
    }


    MatirialUsageModel matirialUsageModel = new MatirialUsageModel();
    String mtID;
    public void loadNextmatirialUsageId() throws SQLException {
        String matirialUsageId = matirialUsageModel.getmatirialUsageId();
        mtID = matirialUsageId;
        System.out.println(matirialUsageId);
    }


    ProdMixModel prodMixModel = new ProdMixModel();
    private int milk ;
    private int suguer;
    private int jeley;

    @FXML
    void cmbProdtOnAction(ActionEvent event) throws SQLException {

        String selectProd = cmbProdt.getSelectionModel().getSelectedItem();
        ProdMixDto prodMixDto = prodMixModel.findbyname(selectProd);

        if (prodMixDto != null) {

            lblMilk.setText(String.valueOf(prodMixDto.getMilk()));
            lblsuguer.setText(String.valueOf(prodMixDto.getSuguer()));
            jeliy.setText(String.valueOf(prodMixDto.getJeliy()));

            milk = prodMixDto.getMilk();
            jeley = prodMixDto.getJeliy();
            suguer = prodMixDto.getSuguer();

            loadAvelbItem();

        }
    }

    private void cleFi() throws SQLException {

        txtProdtName.clear();
        txtQty.clear();
        cmbProdt.getSelectionModel().clearSelection();
        lblMilk.setText("");
        lblsuguer.setText("");
        jeliy.setText("");
        loadAvelbItem();
        loadTble();
        loadNextInventryId();
        loadNextmatirialUsageId();
        loadnextProdID();


    }

    public void loadNewResip() throws SQLException {
        loadProdName();
    }

    @FXML
    void btnAddResipe(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProdMixFrom.fxml"));
            Parent load = loader.load();


            ProdMixController updateResipe = loader.getController();
            updateResipe.setUpdatedResipe(this);
            Image image = new Image(getClass().getResourceAsStream("/images/24.png"));


            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Add Resipes");
            stage.getIcons().add(image);
            stage.setResizable(false);

            stage.initOwner(btnAddResipe.getScene().getWindow());


            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load UI..!").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnALLReportResip(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();


            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/production_mix_recip.jrxml"));

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
