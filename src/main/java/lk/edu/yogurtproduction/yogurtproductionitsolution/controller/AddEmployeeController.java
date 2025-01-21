package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {


    @FXML
    private Button btnSave;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPhone;

    private EmployeeController employeeFormController;
    EmployeeModel employeeModel = new EmployeeModel();



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            loadNextEmployeeId();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    void loadNextEmployeeId() throws SQLException {

        String nextEmployeeId = employeeModel.getNextCustomerId();
        lblEmployeeId.setText(nextEmployeeId);


    }

    @FXML
    void btnSaveEmployeeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String EmployeId = lblEmployeeId.getText();
        String empName = txtName.getText();
        String empNic = txtNic.getText();
        String empEmail = txtEmail.getText();
        String empPhone = txtPhone.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");


        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = empName.matches(namePattern);
        boolean isValidNic = empNic.matches(nicPattern);
        boolean isValidEmail = empEmail.matches(emailPattern);
        boolean isValidPhone = empPhone.matches(phonePattern);

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
            EmployeeDto customerDTO = new EmployeeDto(
                    EmployeId,
                    empName,
                    empNic,
                    empEmail,
                    empPhone

            );
            boolean isSaved = employeeModel.saveEmpoyee(customerDTO);
            if (isSaved) {

                loadNextEmployeeId();
                txtName.setText("");
                txtNic.setText("");
                txtEmail.setText("");
                txtPhone.setText("");
                new Alert(Alert.AlertType.INFORMATION, "Employee saved...!").show();

                if (employeeFormController != null) {
                    employeeFormController.loadCustomerTable();
                }


            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Employee...!").show();
            }

        }
    }




    public void setEmployeeFormController(EmployeeController employeeController) {

        this.employeeFormController = employeeController;
    }
}