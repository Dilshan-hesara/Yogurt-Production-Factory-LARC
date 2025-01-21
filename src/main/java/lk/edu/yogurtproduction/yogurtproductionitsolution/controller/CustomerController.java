package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CustomerDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.TM.CustomerTM;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {


    @FXML
    private Button addCustButt;

    @FXML
    private Button allReport;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnOpenMailSendModel;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lbCustId;

    @FXML
    private Button resetButt;

    @FXML
    private TableView<CustomerTM> custTable;

    @FXML
    private TableColumn<String, CustomerDto> tbMail;

    @FXML
    private TableColumn<String, CustomerDto> tbName;

    @FXML
    private TableColumn<String, CustomerDto> tbNic;

    @FXML
    private TableColumn<String, CustomerDto> tbPhone;

    @FXML
    private TableColumn<String, CustomerDto> tbCustId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPhone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        tbName.setCellValueFactory(new PropertyValueFactory<>("custName"));

        tbMail.setCellValueFactory(new PropertyValueFactory<>("custEmail"));
        tbNic.setCellValueFactory(new PropertyValueFactory<>("custNic"));
        tbPhone.setCellValueFactory(new PropertyValueFactory<>("custPhone"));

        addCustButt.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);





        try {
            loadNextCustId();
            loadTble();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTble() throws SQLException {
        ArrayList<CustomerDto> customerDTOS = customerModel.getAllCustomers();

        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDto customerDto : customerDTOS) {
            CustomerTM customerTM = new CustomerTM(
                    customerDto.getCustomerId(),
                    customerDto.getName(),
                    customerDto.getEmail(),
                    customerDto.getNic(),
                    customerDto.getPhone()

                    );
            customerTMS.add(customerTM);
        }

        custTable.setItems(customerTMS);
    }


    CustomerModel customerModel = new CustomerModel();

    @FXML
    void buttAddSup(ActionEvent event) throws SQLException {

        String CustId = lbCustId.getText();
        String custName = txtName.getText();
        String custNic = txtNic.getText();
        String custEmail = txtEmail.getText();
        String custPhone = txtPhone.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");


        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = custName.matches(namePattern);
        boolean isValidNic = custNic.matches(nicPattern);
        boolean isValidEmail = custEmail.matches(emailPattern);
        boolean isValidPhone = custPhone.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidEmail && isValidPhone) {
            CustomerDto customerDTO = new CustomerDto(
                    CustId,
                    custName,
                    custNic,
                    custEmail,
                    custPhone

            );

            boolean isSaved = customerModel.saveCustomer(customerDTO);
            if (isSaved) {

                loadNextCustId();
                txtName.setText("");
                txtNic.setText("");
                txtEmail.setText("");
                txtPhone.setText("");
                new Alert(Alert.AlertType.INFORMATION, "Customer saved...!").show();

                refreshPage();
                ordersController.loadCustomerIds();

            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Customer...!").show();
            }

        }

    }

    private void loadNextCustId() throws SQLException {
        String nextCustomerId = customerModel.getNexCustomerId();
        lbCustId.setText(nextCustomerId);
    }

    @FXML
    void buttDeleteSup(ActionEvent event) throws SQLException {
        String customerId = lbCustId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = customerModel.deleteCustomer(customerId);
            if (isDeleted) {
                refreshPage();
                ordersController.loadCustomerIds();
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer...!").show();
            }
        }

    }

    private void refreshPage() throws SQLException {

        loadNextCustId();
        loadTble();
        addCustButt.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtNic.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
    }

    @FXML
    void buttUpadeSup(ActionEvent event) throws SQLException {
        String customerId = lbCustId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        CustomerDto customerDTO = new CustomerDto(
                customerId,
                name,
                nic,
                email,
                phone
        );

        boolean isUpdate = customerModel.updateCustomer(customerDTO);
        if (isUpdate) {
            refreshPage();
            ordersController.loadCustomerIds();
            new Alert(Alert.AlertType.INFORMATION, "Customer update...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
        }

    }

    @FXML
    void resetButt(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void tblClik(MouseEvent event) {
        CustomerTM customerTM = custTable.getSelectionModel().getSelectedItem();
        if (customerTM != null) {
            lbCustId.setText(customerTM.getCustId());
            txtName.setText(customerTM.getCustName());
            txtNic.setText(customerTM.getCustNic());
            txtEmail.setText(customerTM.getCustEmail());
            txtPhone.setText(customerTM.getCustPhone());

            addCustButt.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    OrdersController ordersController = new OrdersController();
    public void setCustomerFormCon(OrdersController ordersController) {
        this.ordersController = ordersController;
    }
}
