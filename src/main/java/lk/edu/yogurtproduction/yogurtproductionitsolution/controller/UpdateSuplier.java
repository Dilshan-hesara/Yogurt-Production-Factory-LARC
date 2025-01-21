package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.SupplierDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.SupplierDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.SuplierDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.SuplierTM;

public class UpdateSuplier {

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

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

    private SuplierTM suplierTM;

    private SupplierCon supplierCon;

    SupplierDAO suplierModel = new SupplierDAOImpl();

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        String supId = lblSupId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String emPhone = txtPhone.getText().trim();

        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name cannot be empty!").show();
            return;
        }

        if (nic.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "NIC cannot be empty!").show();
            return;
        }

        if (email.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Email cannot be empty!").show();
            return;
        }

        if (emPhone.isEmpty()) {
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

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = emPhone.matches(phonePattern);


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
        int emphone = Integer.parseInt(emPhone);

        if (isValidName && isValidNic && isValidEmail && isValidPhone) {


            SuplierDto suplierDto = new SuplierDto(
                    supId,
                    name,
                    nic,
                    email,
                    emphone
            );


            boolean isUpdate = suplierModel.update(suplierDto);
            if (isUpdate) {
                supplierCon.loadSuplierTable();

                new Alert(Alert.AlertType.INFORMATION, "Suplier update...!").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Suplier...!").show();
            }

        }
    }

    public void setSuplierData(SuplierTM suplierTM) {
        this.suplierTM = suplierTM;

        txtName.setText(suplierTM.getSupName());
        txtNic.setText(suplierTM.getSupNic());
        txtEmail.setText(suplierTM.getSupEmail());
        txtPhone.setText(String.valueOf(suplierTM.getSupPhone()));
        lblSupId.setText(suplierTM.getSupId());

    }

    public void setSupierReloadTable(SupplierCon supplierCon) {
        this.supplierCon = supplierCon;

    }
}
