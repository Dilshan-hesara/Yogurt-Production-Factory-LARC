package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.EmployeeDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.EmployeeDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.*;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Employee;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PackingCon {

    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private ComboBox<String> cmbProdId;

    @FXML
    private TextField desetxt;

    @FXML
    private TextField qrytxt;


    @FXML
    private Label lblEmpName;

    @FXML
    private Label lblProdQty;

    @FXML
    private  Label lblPacID;

    @FXML
    private Label lblProdtName;

    @FXML
    private TextField pricetxt;


    @FXML
    private DatePicker expireDate;

    @FXML
    private ComboBox<String> cmbPacType;




    public  void initialize() throws SQLException {
        loadProdtId();
        loadEmpId();
        loadcmbPacType();
        loadNextInventryId();
        loadNextStockId();
        loadNextPackingId();
    }



    int AVqty;
    String AVqtyProdtName;


    PackingModel packingModel = new PackingModel();
    @FXML
    void btnAddStock(ActionEvent event) throws SQLException {

        ArrayList<InventroyDto> inventroyDTOS = new ArrayList<>();
        ArrayList<StockDto> stockDTOS = new ArrayList<>();

        String selectEmplye = cmbEmpId.getSelectionModel().getSelectedItem();
        String selectProd = cmbProdId.getSelectionModel().getSelectedItem();
        String seletPacktype = cmbPacType.getSelectionModel().getSelectedItem();

        if (selectEmplye == null) {
            new Alert(Alert.AlertType.ERROR, "Please select Employee  ..!").show();
            return;
        }

        if (selectProd == null) {
            new Alert(Alert.AlertType.ERROR, "Please select Prodtion ..!").show();
            return;
        }
        if (seletPacktype == null) {
            new Alert(Alert.AlertType.ERROR, "Please select PackType ..!").show();
            return;
        }

        if (expireDate.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Expire date is not selected or invalid!").show();
            return;
        }

        String PaDesc = desetxt.getText();
        if (PaDesc == null || PaDesc.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Package description is required!").show();
            return;
        }

        if (PaDesc.matches(".*\\d.*")) {
            new Alert(Alert.AlertType.ERROR, "Package description cannot use numbers!").show();
            return;
        }

        String qtyText = qrytxt.getText();
        if (qtyText == null || qtyText.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Qty is required!").show();
            return;
        }
        if (!qtyText.matches("\\d+")) {
            new Alert(Alert.AlertType.ERROR, "Invad Qty Enter a valid integer..").show();
            return;
        }
        double QtyVa = Double.parseDouble(qtyText);
        if (QtyVa <= 0) {
            new Alert(Alert.AlertType.ERROR, "Qty cant Zero valuse number use!").show();
            return;
        }

        int TxtQty = (int) (QtyVa *PacTypes);

        if (AVqty < TxtQty ) {
            new Alert(Alert.AlertType.ERROR, "Not enough "+" "+AVqtyProdtName+" "+" Prod available!").show();
            return;
        }

        String Pac_ID = lblPacID.getText();
         String Prod_ID = cmbProdId.getSelectionModel().getSelectedItem();
         String Pac_Type = cmbPacType.getSelectionModel().getSelectedItem();
         String Pac_Desc = desetxt.getText();
         String Pac_Date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
         String Expire_Date = String.valueOf(expireDate.getValue());
         double Qty = Double.parseDouble(qrytxt.getText());
         String Emp_ID = cmbEmpId.getSelectionModel().getSelectedItem();
         double RedusQty = Qty * PacTypes;
         double UnitPrice = pricetxt.getText().charAt(0);





        String Stock_ID = stID;
        String stockDescription =desetxt.getText() +"-"+" "+ Qty +"x"+Pac_Type ;;

        StockDto stockDTO  = new StockDto(

                Stock_ID,
                Pac_ID,
                stockDescription,
                Qty,
                Pac_Date,
                Expire_Date,
                Pac_Type,
                UnitPrice


        );
        stockDTOS.add(stockDTO);


        String InID = invID;
        String itemType = "END Prodt";
        String itemDescription =desetxt.getText() +"-"+" "+ Qty +"x"+Pac_Type ;;
        String prodId = Prod_ID;
        String qty = String.valueOf(Double.parseDouble(qrytxt.getText()));

        InventroyDto inventroyDTO  = new InventroyDto(
                InID,
                itemType,
                itemDescription,
                qty,
                prodId


        );
        inventroyDTOS.add(inventroyDTO);

        PckingDto pckingDtos = new PckingDto(

                Pac_ID,
                Prod_ID,
                Pac_Type,
                Pac_Desc,
                Pac_Date,
                Expire_Date,
                Qty,
                Emp_ID,
                RedusQty,
                inventroyDTOS,
                stockDTOS
        );
        boolean isSaved = packingModel.savePacking(pckingDtos);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Saved successfully!").show();
            clAll();
        } else {
            new Alert(Alert.AlertType.ERROR, "Save failed! Please try again.").show();
        }


    }



    private void clAll() throws SQLException {
        cmbProdId.getSelectionModel().clearSelection();
        cmbPacType.getSelectionModel().clearSelection();
        desetxt.clear();
        qrytxt.clear();
        cmbEmpId.getSelectionModel().clearSelection();
        expireDate.setValue(null);
        lblPacID.setText("");
        pricetxt.setText("");

        loadcmbPacType();
        loadEmpId();
        loadNextInventryId();
        loadNextStockId();
        loadNextPackingId();


    }


    String invID;
    InventroyModel inventroyModel = new InventroyModel();

    public void loadNextInventryId() throws SQLException {
        String nextInventryId = inventroyModel.getInventroyId();
        invID = nextInventryId;
        System.out.println(nextInventryId);
    }
    public void loadInvetroyAvQtyFromSelectProd_ID() throws SQLException {
        String getSelectedProdId = cmbProdId.getSelectionModel().getSelectedItem();
        String AvalbleQty = String.valueOf(inventroyModel.AvQtyFromSelectProd_ID(getSelectedProdId));
        lblProdQty.setText(AvalbleQty);
        AVqty = Integer.parseInt(AvalbleQty);

    }

    StockModel stockModel = new StockModel();

    String stID;
    public void loadNextStockId() throws SQLException {
        String nextStockId = stockModel.getStockId();
         stID = nextStockId;
        System.out.println(nextStockId);
    }

    public void loadNextPackingId() throws SQLException {
        String nextPackId = packingModel.getPackId();
        lblPacID.setText(nextPackId);
        System.out.println(nextPackId);
    }


    @FXML
    void btnPack(ActionEvent event) {
        System.out.println(PacTypes);
        String cmbPacTypeSelected = cmbPacType.getSelectionModel().getSelectedItem();
        System.out.println(cmbPacTypeSelected);
        System.out.println( expireDate.getValue());

    }



    @FXML
    void cmbEmpOnAction(ActionEvent event) throws SQLException {
        String cmbEmpSelected = cmbEmpId.getSelectionModel().getSelectedItem();
        Employee employeeDto = employeeModel.findByID(cmbEmpSelected);
        if (employeeDto != null) {
            lblEmpName.setText(employeeDto.getEmpName());
        }

    }


    @FXML
    void cmbProdOnAction(ActionEvent event) throws SQLException {
        String cmbProdSelected = cmbProdId.getSelectionModel().getSelectedItem();
        System.out.println(cmbProdSelected);
        ProdtionDto prodtionDto = prodtionModel.findById(cmbProdSelected);
        if (prodtionDto != null) {
            lblProdtName.setText(prodtionDto.getPro_Name());
            loadInvetroyAvQtyFromSelectProd_ID();
            AVqtyProdtName = prodtionDto.getPro_Name();

        }

    }

   // EmployeeModel employeeModel = new EmployeeModel();
    EmployeeDAO employeeModel = new EmployeeDAOImpl();
    ProdtionModel prodtionModel = new ProdtionModel();

    private void loadEmpId() throws SQLException {
            ArrayList<String> empIds = employeeModel.getAllEmpIds();
            ObservableList<String> observableList = FXCollections.observableArrayList(empIds);
            cmbEmpId.setItems(observableList);
        }

    private void loadProdtId() throws SQLException {
        ArrayList<String> prodIds = prodtionModel.getAllProdtIds();
        ObservableList<String> observableList = FXCollections.observableArrayList(prodIds);
        cmbProdId.setItems(observableList);
    }

    int PacTypes = 0;

    @FXML
    void CmbPacTypeOnAction(ActionEvent event) {


        String cmbPacTypeSelected = cmbPacType.getSelectionModel().getSelectedItem();
        if (cmbPacTypeSelected != null) {

            if (cmbPacTypeSelected == "08 PACK SET") {
                System.out.println(cmbPacTypeSelected);
                PacTypes = 8;
            } else if (cmbPacTypeSelected == "12 PACK SET") {
                System.out.println(cmbPacTypeSelected);
                PacTypes = 12;

            } else if (cmbPacTypeSelected == "48 PACK SET") {
                System.out.println(cmbPacTypeSelected);
                PacTypes = 48;
            }
        }

    }
    private void loadcmbPacType() {
        cmbPacType.setItems(FXCollections.observableArrayList("08 PACK SET","12 PACK SET", "48 PACK SET"));
    }



}
