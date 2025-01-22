
package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.BOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.EmployeeBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.EmployeeDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.EmployeeDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.EmployeeTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateEmployeeController implements Initializable {


    @FXML
    private Button btnUpdate;

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

    private EmployeeTM selectedEmployee;

    private EmployeeController employeeFormController;

   // EmployeeDAO employeeModel = new EmployeeDAOImpl();

    EmployeeBO employeeModel =  (EmployeeBO) BOFactroy.getInstance().getBO(BOFactroy.BOType.EMPLOYEE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    public void setEmployeeData(EmployeeTM employee) {
        this.selectedEmployee = employee;
        txtName.setText(selectedEmployee.getEmpName());
        txtNic.setText(selectedEmployee.getEmpNic());
        txtEmail.setText(selectedEmployee.getEmpEmail());
        txtPhone.setText(selectedEmployee.getEmpPhone());
        lblEmployeeId.setText(selectedEmployee.getEmpId());

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {


        String emId = lblEmployeeId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");


        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

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

            EmployeeDto employeeDto = new EmployeeDto(
                    emId,
                    name,
                    nic,
                    email,
                    phone
            );


            boolean isUpdate = employeeModel.update(employeeDto);
            if (isUpdate) {

                new Alert(Alert.AlertType.INFORMATION, "Employee update...!").show();
                employeeFormController.loadCustomerTable();

            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Employee...!").show();
            }

        }
    }

    public void setEmployeeReloadTable(EmployeeController employeeController) {
        this.employeeFormController = employeeController;
    }
}
