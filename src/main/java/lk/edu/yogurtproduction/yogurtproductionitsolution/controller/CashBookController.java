package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.BOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.CashBookBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.CashBookDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.InventroyDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.MaterialDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.SupplierDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.CashBookDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.InventroyDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.MaterialDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.SupplierDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CashBookDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.InventroyDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.SuplierDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.CashBookTM;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.CashBookModel;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.InventroyModel;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.MatiralMoadel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CashBookController {


    @FXML
    private ComboBox<String> cmbItemd;



    @FXML
    private ComboBox<String> cmbSupId;


    @FXML
    private Label lblPayAmount;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblSupplerName;

    @FXML
    private Label lblItemQty;
    @FXML
    private  Label lblItemPrice;
    @FXML
    private TextField txtQty;


    @FXML
    private TableColumn<String, CashBookTM> colCbNO;


    @FXML
    private TableColumn<String, CashBookTM> colSupID;

    @FXML
    private TableColumn<String, CashBookTM> colDate;


    @FXML
    private TableColumn<String, CashBookTM> colDESE;


    @FXML
    private TableColumn<Integer, CashBookTM> colQTY;


    @FXML
    private TableColumn<Double, CashBookTM> colAmo;


    @FXML
    private TableView<CashBookTM> tblCshBook;


    @FXML
    private TableView<CashBookTM> tblCashBook;


    CashBookBO cashBookBO = (CashBookBO) BOFactroy.getInstance().getBO(BOFactroy.BOType.CASHBOOK);

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
       setCellValues();
        refesh();
        LoadTabel();


    }


    @FXML
    void report(ActionEvent event) {
        try {

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();


            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/CashBookReport.jrxml"));

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
    private Label lblCBN;

    private void loadNextCBNOId() throws SQLException {
        String nextCBNOId = cashBookBO.getNextId();
        lblCBN.setText(nextCBNOId);
        System.out.println(nextCBNOId);
    }

    private void setCellValues() {
        colCbNO.setCellValueFactory(new PropertyValueFactory<>("CBNo"));
        colSupID.setCellValueFactory(new PropertyValueFactory<>("SupId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDESE.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colAmo.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }
    public void LoadTabel() throws SQLException, ClassNotFoundException {
        ArrayList<CashBookDto> cashBookDtos = cashBookBO.getAll();

        ObservableList<CashBookTM> cashBookTMS = FXCollections.observableArrayList();

        for (CashBookDto cashBookDto : cashBookDtos) {
            CashBookTM cashBookTM = new CashBookTM(
                    cashBookDto.getCBNo(),
                    cashBookDto.getSupId(),
                    cashBookDto.getDate(),
                    cashBookDto.getDesc(),
                    cashBookDto.getQty(),
                    cashBookDto.getAmount()


            );
            cashBookTMS.add(cashBookTM);
        }
        tblCashBook.setItems(cashBookTMS);

        System.out.println("load");
    }



    @FXML
    void btnPlaceIt(ActionEvent event) throws SQLException, ClassNotFoundException {
        loadNextInventryId();
        loadNextCBNOId();

        String selectedItemId = cmbItemd.getValue();


        if (selectedItemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select item..!").show();
            return;
        }

        String seltctedSupId = cmbSupId.getValue();
        if (seltctedSupId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select supId..!").show();
            return;
        }


        String qtyV = txtQty.getText().trim();
        String avQText = lblItemQty.getText().trim();
        String qtyPattern = "^[0-9]+$";

        if (qtyV.isEmpty() || avQText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "quantity cannot be empty..!").show();
            return;
        }

        if (!qtyV.matches(qtyPattern)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid numeric quantity..!").show();
            return;
        }

        try {
            int qtyr = Integer.parseInt(qtyV);
            int avQ = Integer.parseInt(avQText);

            if (qtyr == 0) {
                new Alert(Alert.AlertType.ERROR, "quantity cannot be zero..!").show();
                return;
            }

            if (avQ < qtyr) {
                new Alert(Alert.AlertType.ERROR, "Not enough items..!").show();
                return;
            }


        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while processing the quantity. Please check the input..!").show();
        }

        ArrayList<InventroyDto> inventroyDTOS = new ArrayList<>();

        double price = Double.parseDouble(lblItemPrice.getText());

        String CBNo =  lblCBN.getText();
        String SupId = cmbSupId.getSelectionModel().getSelectedItem();
        String matID =  cmbItemd.getSelectionModel().getSelectedItem();
        String inID =  invID;
        String desc = lblItemName.getText();
        int qty = Integer.parseInt(qtyV);
        double amount =  price * qty;
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String itemType = "Raw";
        String itemDescription =desc;
        String prodId = "----";
        String Qty = String.valueOf(qty);


        InventroyDto inventroyDTO  = new InventroyDto(
                inID,
                itemType,
                itemDescription,
                Qty,
                prodId


        );

        inventroyDTOS.add(inventroyDTO);

        CashBookDto cashBookDtos = new CashBookDto(
                CBNo,
                SupId,
                matID,
                inID,
                desc,
                qty,
                amount,
                date,
                   inventroyDTOS


        );

        boolean isSaved = cashBookBO.saveResept(cashBookDtos);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Saved successfully!").show();
            refesh();
        } else {
            new Alert(Alert.AlertType.ERROR, "Save failed! Please try again.").show();
        }

    }

    public void getAllAmount() throws SQLException {
        int am = cashBookBO.getAllPayAmount();
        System.out.println(am);
        lblPayAmount.setText(String.valueOf(am));
    }


    String invID;
    public void loadNextInventryId() throws SQLException {
        String nextInventryId = cashBookBO.getNextId();
        invID = nextInventryId;
        System.out.println(nextInventryId);
    }


    private void loadSupplierId() throws SQLException {
        ArrayList<String> supplierIds = cashBookBO.getAllSupIds();
        ObservableList<String> observableList = FXCollections.observableArrayList(supplierIds);
        cmbSupId.setItems(observableList);
    }

    private void loadItemId() throws SQLException {
        ArrayList<String>  ItemIds = cashBookBO.getAllItemIds();
        ObservableList<String> observableList = FXCollections.observableArrayList(ItemIds);
        cmbItemd.setItems(observableList);
    }


    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
        String selectID = (String) cmbItemd.getSelectionModel().getSelectedItem();

        MatirialDto matirialDto = cashBookBO.findById(selectID);
        if (matirialDto != null) {

            lblItemName.setText(matirialDto.getMatName());
            lblItemPrice.setText(String.valueOf(matirialDto.getMatPrice()));
            lblItemQty.setText(String.valueOf(matirialDto.getMatQty()));


        }
    }

    @FXML
    void cmbSupOnAction(ActionEvent event) throws SQLException {
        String selectID = (String) cmbSupId.getSelectionModel().getSelectedItem();

        SuplierDto suplierDto = cashBookBO.findByID(selectID);
        if (suplierDto != null) {
            lblSupplerName.setText(suplierDto.getSupName());
        }

    }
    public void referMat() throws SQLException {
        loadItemId();
    }


    private void refesh() throws SQLException, ClassNotFoundException {

        loadSupplierId();
        loadItemId();
        getAllAmount();
        loadNextCBNOId();
        LoadTabel();
        loadNextInventryId();
    }
    @FXML

    private Button btnMat;

    @FXML
    void AddMatireal(ActionEvent event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MatirialForm.fxml"));
                Parent load = loader.load();


                MatirialCon updateItemCmb = loader.getController();
                updateItemCmb.setUpdatedCmde(this);

                Stage stage = new Stage();
                stage.setScene(new Scene(load));
                stage.setTitle("Add Material");
                stage.setResizable(false);

                Image image = new Image(getClass().getResourceAsStream("/images/22.png"));
                stage.getIcons().add(image);

                stage.initOwner(btnMat.getScene().getWindow());


                stage.initModality(Modality.APPLICATION_MODAL);

                stage.showAndWait();

            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to load UI..!").show();
                e.printStackTrace();
            }
        }


    }


