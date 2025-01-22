package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.MaterialDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.MaterialDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.MatirialTM;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.MatiralMoadel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MatirialCon implements Initializable {


    @FXML
    private Label lblItId;

    @FXML
    private TableColumn<MatirialTM, String> matID;

    @FXML
    private TableColumn<MatirialTM, String> matName;

    @FXML
    private TableColumn<MatirialTM, Integer> matPrice;

    @FXML
    private TableColumn<MatirialTM, Integer> matQty;

    @FXML
    private TableView<MatirialTM> matTable;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;


    @FXML
    private ComboBox<String> cmbMatName;

    @FXML
    void cmbMatNameOnAction(ActionEvent event) {

    }

    MaterialDAO matiralMoadel = new MaterialDAOImpl();
  //  private MatiralMoadel matiralMoadel =  new MatiralMoadel();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        matID.setCellValueFactory(new PropertyValueFactory<>("matId"));
        matName.setCellValueFactory(new PropertyValueFactory<>("matName"));
        matPrice.setCellValueFactory(new PropertyValueFactory<>("matPrice"));
        matQty.setCellValueFactory(new PropertyValueFactory<>("matQty"));
        try {
            loadNextMatId();
            loadTable();
            loadcmbMat();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadcmbMat() {
        cmbMatName.setItems(FXCollections.observableArrayList("Sugar", "Milk","Gelat"));
    }

    void loadNextMatId() throws SQLException {
        String nextMatId = matiralMoadel.getNextId();
        lblItId.setText(nextMatId);
    }

    private void loadTable() throws SQLException, ClassNotFoundException {
        ArrayList<MatirialDto> matirialDTOS = matiralMoadel.getAll();

        ObservableList<MatirialTM> matirialTMS = FXCollections.observableArrayList();

        for (MatirialDto matirialDto : matirialDTOS) {
            MatirialTM matirialTM = new MatirialTM(
                    matirialDto.getMatId(),

                    matirialDto.getMatName(),

                    matirialDto.getMatQty(),
                    matirialDto.getMatPrice()

            );
            matirialTMS.add(matirialTM);
        }
matTable.setItems(matirialTMS);

    }

    @FXML
    void btnSave(ActionEvent event) throws SQLException, ClassNotFoundException {


        String selectedMat = cmbMatName.getValue();

        if (selectedMat == null) {
            new Alert(Alert.AlertType.ERROR, "Please select Matireal..!").show();
            return;
        }

        String qtyValida = txtQuantity.getText().trim();
        String qtyPattern = "^[0-9]+$";

        if (qtyValida.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "quantity cannot be empty..!").show();
            return;
        }

        if (!qtyValida.matches(qtyPattern)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid numeric quantity..!").show();
            return;
        }

        try {
            int qtyr = Integer.parseInt(qtyValida);

            if (qtyr == 0) {
                new Alert(Alert.AlertType.ERROR, "quantity cannot be zero..!").show();
                return;
            }



        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while processing the quantity. Please check the input..!").show();
        }

        String priceVali = String.valueOf(txtPrice.getText());
        String pricePattern = "^[0-9]+$";

        if (priceVali.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "price cannot be empty..!").show();
            return;
        }

        if (!priceVali.matches(pricePattern)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid numeric ..!").show();
            return;
        }

        try {
            int price = Integer.parseInt(priceVali);


            if (price == 0) {
                new Alert(Alert.AlertType.ERROR, "price cannot be zero..!").show();
                return;
            }



        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please check the input..!").show();
        }

        String matrId = lblItId.getText();
        String name = cmbMatName.getSelectionModel().getSelectedItem();
        int qty = Integer.parseInt(qtyValida);
        int price = Integer.parseInt(priceVali);


            MatirialDto matirialDto = new MatirialDto(
                    matrId,
                    name,
                    qty,
                    price

            );

            boolean isSaved = matiralMoadel.save(matirialDto);
            if (isSaved) {
                loadNextMatId();
                loadTable();
                cleF();
               cashBookController.referMat();

                new Alert(Alert.AlertType.INFORMATION, "Matirial saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save matireal...!").show();
            }
        }
    @FXML
    void tbleClick(MouseEvent event) {

    }

    private void cleF() {
        cmbMatName.getSelectionModel().clearSelection();

        txtQuantity.clear();
        txtPrice.clear();
    }

    CashBookController cashBookController = new CashBookController();

    public void setUpdatedCmde(CashBookController cashBookController) {
        this.cashBookController = cashBookController;

    }




}


