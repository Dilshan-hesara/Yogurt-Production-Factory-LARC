package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.SuplierDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.SuplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddSuplierController implements Initializable {


    @FXML
    private Label lblSupId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPhone;

    private SupplierCon supplierCon;

    SuplierModel suplierModel = new SuplierModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadNextSuplierId();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void loadNextSuplierId() throws SQLException {
        String nextSupId = suplierModel.getNextSuplierId();
        lblSupId.setText(nextSupId);

    }

    @FXML
    void btnSaveSupOnAction(ActionEvent event) throws Exception {
        String SupId = lblSupId.getText();
        String Name = txtName.getText();
        String Nic = txtNic.getText();
        String mail = txtEmail.getText();
        String empPhoneText = txtPhone.getText().trim();
        if (Name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name cannot be empty!").show();
            return;
        }

        if (Nic.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "NIC cannot be empty!").show();
            return;
        }

        if (mail.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Email cannot be empty!").show();
            return;
        }

        if (empPhoneText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Phone number cannot be empty!").show();
            return;
        }

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = Name.matches(namePattern);
        boolean isValidNic = Nic.matches(nicPattern);
        boolean isValidEmail = mail.matches(emailPattern);
        boolean isValidPhone = empPhoneText.matches(phonePattern);


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
        int empPhone = Integer.parseInt(empPhoneText);

        if (isValidName && isValidNic && isValidEmail && isValidPhone) {

            SuplierDto suplierDTO = new SuplierDto(
                    SupId,
                    Name,
                    Nic,
                    mail,
                    empPhone

            );
            boolean isSaved = suplierModel.saveSuplier(suplierDTO);
            if (isSaved) {
                loadNextSuplierId();
                txtName.setText("");
                txtNic.setText("");
                txtEmail.setText("");
                txtPhone.setText("");
                supplierCon.loadSuplierTable();
                new Alert(Alert.AlertType.INFORMATION, "Suplier saved...!").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Suplier...!").show();
            }
        }
        }


    public void setSupFormCon(SupplierCon supplierCon) {
        this.supplierCon = supplierCon;
    }
}




