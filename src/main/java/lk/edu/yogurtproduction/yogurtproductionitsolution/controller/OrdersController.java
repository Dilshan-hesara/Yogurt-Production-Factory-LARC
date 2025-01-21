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
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CustomerDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.OrderDetailsDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.OrdersDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.StockDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.TM.CartTM;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.CustomerModel;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.OrderModel;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.StockModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private Button btnAddCustomer;

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbProd;

    @FXML
    private TableColumn<String, CartTM> colAction;

    @FXML
    private TableColumn<String, CartTM> colIProdId;

    @FXML
    private TableColumn<String, CartTM> colName;

    @FXML
    private TableColumn<String, CartTM> colPrice;

    @FXML
    private TableColumn<String, CartTM> colQuantity;

    @FXML
    private TableColumn<String, CartTM> colTotal;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblIProdtPrice;

    @FXML
    private Label lblOderID;

    @FXML
    private Label lblProdName;

    @FXML
    private Label lblProdtQty;

    @FXML
    private Label orderDate;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private TextField txtAddToCartQty;

    @FXML
    void btnAddCustomer(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerFrom.fxml"));
            Parent load = loader.load();

            CustomerController addCustController = loader.getController();
            addCustController.setCustomerFormCon(this);


            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Add Customer");


            Image image = new Image(getClass().getResourceAsStream("/images/48.png"));
            stage.getIcons().add(image);

            stage.initOwner(btnAddCustomer.getScene().getWindow());
            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load UI..!").show();
            e.printStackTrace();
        }

    }

    @FXML
    void btnAddToCart(ActionEvent event) {
        String selectedItemId = (String) cmbProd.getSelectionModel().getSelectedItem();

        if (selectedItemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a product..!").show();
            return;
        }

        String itemName = lblProdName.getText();
        String input = txtAddToCartQty.getText().trim();

        if (input.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "qty cannot be empty!").show();
            return;
        }

        if (!input.matches("\\d+")) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid integer qty!").show();
            return;
        }

        int cartQty = Integer.parseInt(input);

        if (cartQty <= 0) {
            new Alert(Alert.AlertType.ERROR, "Qty must > 0!").show();
            return;
        }

        double quantity = Double.parseDouble(AVqty);

        if (quantity < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough products available!").show();
            return;
        }
        txtAddToCartQty.setText("");

        double unitPrice = Double.parseDouble(lblIProdtPrice.getText());
        double total = unitPrice * cartQty;

        for (CartTM cartTM : cartTMS) {
            if (cartTM.getItemId().equals(selectedItemId)) {
                int newQty = cartTM.getCartQuantity() + cartQty;
                cartTM.setCartQuantity(newQty);
                cartTM.setTotal(unitPrice * newQty);
                tblCart.refresh();
                return;
            }

        }
        Button btn = new Button("Remove");

        CartTM newCartTM = new CartTM(
                selectedItemId,
                itemName,
                cartQty,
                unitPrice,
                total,
                btn
        );
        btn.setOnAction(actionEvent -> {

            cartTMS.remove(newCartTM);

            tblCart.refresh();
        });

        cartTMS.add(newCartTM);


    }

    @FXML
    void btnPlaceOrder(ActionEvent event) throws SQLException {

        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add prodts to cart..!").show();
            return;
        }
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place order..!").show();
            return;
        }

        String orderId = lblOderID.getText();
        Date dateOfOrder = Date.valueOf(orderDate.getText());
        String customerId = cmbCustomerId.getValue();

        ArrayList<OrderDetailsDto> orderDetailsDTOS = new ArrayList<>();

        for (CartTM cartTM : cartTMS) {

            OrderDetailsDto orderDetailsDTO = new OrderDetailsDto(
                    orderId,
                    cartTM.getItemId(),
                    cartTM.getCartQuantity(),
                    cartTM.getUnitPrice()
            );

            orderDetailsDTOS.add(orderDetailsDTO);
        }

        OrdersDto orderDTO = new OrdersDto(
                orderId,
                customerId,
                dateOfOrder,
                orderDetailsDTOS
        );

        boolean isSaved = orderModel.saveOrder(orderDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order saved..!").show();

            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order fail..!").show();
        }



    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String selectedCustomerId = (String) cmbCustomerId.getSelectionModel().getSelectedItem();
        CustomerDto customerDTO = customerModel.findById(selectedCustomerId);

        if (customerDTO != null) {

            lblCustomerName.setText(customerDTO.getName());
        }
    }

    String AVqty;
    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
        String selectedProdt = (String) cmbProd.getSelectionModel().getSelectedItem();
        StockDto stockDto = stockModel.findById(selectedProdt);

        if (stockDto != null) {

            lblProdName.setText(stockDto.getProduct_Name());
            lblProdtQty.setText(String.valueOf(stockDto.getQty()));
            lblIProdtPrice.setText(String.valueOf(stockDto.getUnit_Price()));
            AVqty= String.valueOf(stockDto.getQty());
        }

    }
    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    OrderModel orderModel = new OrderModel();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colIProdId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        tblCart.setItems(cartTMS);
        orderDate.setText(LocalDate.now().toString());


        try {
            lblOderID.setText(orderModel.getNextOrderId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            loadProdtId();
            loadCustomerIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    StockModel stockModel = new StockModel();

    private void loadProdtId() throws SQLException {
        ArrayList<String> itemIds = stockModel.getAllProdIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(itemIds);
        cmbProd.setItems(observableList);
    }



    CustomerModel customerModel = new CustomerModel();

    public void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerModel.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cmbCustomerId.setItems(observableList);
    }
    private void refreshPage() throws SQLException {

        lblOderID.setText(orderModel.getNextOrderId());

        loadCustomerIds();
        loadProdtId();

        orderDate.setText(LocalDate.now().toString());


        cmbCustomerId.getSelectionModel().clearSelection();
        cmbProd.getSelectionModel().clearSelection();
        lblProdName.setText("");
        lblProdtQty.setText("");
        lblIProdtPrice.setText("");
        txtAddToCartQty.setText("");
        lblCustomerName.setText("");

        cartTMS.clear();

        tblCart.refresh();
    }
}
