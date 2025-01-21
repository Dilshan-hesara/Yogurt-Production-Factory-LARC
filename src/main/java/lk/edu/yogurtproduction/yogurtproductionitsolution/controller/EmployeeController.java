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
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.EmployeeTM;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.EmployeeModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class EmployeeController implements Initializable {


    @FXML
    private Button addEmpButt;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnOpenMailSendModel;

    @FXML
    private Button btnAlEmpReport;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button resetButt;

    @FXML
    private TableColumn<EmployeeTM, String> colMail;

    @FXML
    private TableColumn<EmployeeTM, String> col_name;

    @FXML
    private TableColumn<EmployeeTM, String> col_nic;

    @FXML
    private TableColumn<EmployeeTM, String> col_phone;

    @FXML
    private TableColumn<EmployeeTM, String> emIdta;

    @FXML
    private TableView<EmployeeTM> emTable;

    EmployeeModel employeeModel = new EmployeeModel();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emIdta.setCellValueFactory(new PropertyValueFactory<>("empId"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("empName"));
        col_nic.setCellValueFactory(new PropertyValueFactory<>("empNic"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("empEmail"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("empPhone"));

        try {
            loadCustomerTable();
            btnDelete.setDisable(true);
            btnOpenMailSendModel.setDisable(true);
            btnUpdate.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load employee data").show();
        }

    }
    void loadCustomerTable() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDto> employeeDTOS = employeeModel.getAllEmployees();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDto employeeDto : employeeDTOS) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDto.getEmpId(),
                    employeeDto.getEmpName(),
                    employeeDto.getEmpNic(),
                    employeeDto.getEmpEmail(),
                    employeeDto.getEmpPhone()
            );
            employeeTMS.add(employeeTM);
        }

        emTable.setItems(employeeTMS);
    }



    @FXML
    void buttAddEmp(ActionEvent event) throws IOException {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddEmployee.fxml"));
            Parent load = loader.load();

            AddEmployeeController addEmployeeController = loader.getController();
            addEmployeeController.setEmployeeFormController(this);
 


            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Add Employee");
            stage.setResizable(false);

            Image image = new Image(getClass().getResourceAsStream("/images/51.png"));
            stage.getIcons().add(image);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.initOwner(btnUpdate.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }
    }

    @FXML
    public void buttDeleteEmp() throws SQLException, ClassNotFoundException {
        EmployeeTM selectedEmployee = emTable.getSelectionModel().getSelectedItem();

        String empId = selectedEmployee.getEmpId();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            System.out.println(empId);
            boolean isDeleted = employeeModel.deleteCustomer (empId);
            if (isDeleted) {
                loadCustomerTable();
                new Alert(Alert.AlertType.INFORMATION, "Employee deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Emloyee...!").show();
            }
        }


    }



    @FXML
    void buttUpadeEmp(ActionEvent event) {

        EmployeeTM selectedEmployee = emTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpadeEmployee.fxml"));
                if (loader.getLocation() == null) {
                    throw new IllegalStateException("FXML file not found.");
                }
                Parent load = loader.load();

                UpdateEmployeeController updateEmployeeController = loader.getController();
                updateEmployeeController.setEmployeeData(selectedEmployee);// pass kara

                UpdateEmployeeController updateEmployeeReloadTable = loader.getController();
                updateEmployeeReloadTable.setEmployeeReloadTable(this);//table eka load pass kr

                Stage stage = new Stage();
                stage.setScene(new Scene(load));
                stage.setTitle("Update Employee");
                stage.setResizable(false);

                Image image = new Image(getClass().getResourceAsStream("/images/50.png"));
                stage.getIcons().add(image);


                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(btnUpdate.getScene().getWindow());

                stage.showAndWait();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Fail to load UI: " + e.getMessage()).show();
                e.printStackTrace();
            } catch (IllegalStateException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select an employee to update.").show();
        }
    }

    @FXML
    void btnOpenMailSendModelOnAction(ActionEvent event) {

        EmployeeTM selectedEmployee = emTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SendMailEmployee.fxml"));
                if (loader.getLocation() == null) {
                    throw new IllegalStateException("FXML file not found.");
                }
                Parent load = loader.load();

                SendMailEmloyeeController sendMailEmloyeeController = loader.getController();
                sendMailEmloyeeController.sendMailData(selectedEmployee);// pass kara

                Stage stage = new Stage();
                stage.setScene(new Scene(load));
                stage.setTitle("Send Mail Employee");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);

                Image image = new Image(getClass().getResourceAsStream("/images/49.png"));
                stage.getIcons().add(image);

                stage.initOwner(btnUpdate.getScene().getWindow());
                stage.showAndWait();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Fail to load UI: " + e.getMessage()).show();
                e.printStackTrace();
            } catch (IllegalStateException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select an employee .").show();
        }
    }
    @FXML
    void generateAllCustomerReportOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("P_Date", LocalDate.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/EmployeePakingFromjrxml.jrxml"));

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



    public void btntableClick(javafx.scene.input.MouseEvent mouseEvent) {


        addEmpButt.setDisable(true);
        btnDelete.setDisable(false);
        btnOpenMailSendModel.setDisable(false);
        btnUpdate.setDisable(false);
        btnAlEmpReport.setDisable(true);

    }


    @FXML
    void resetButt(ActionEvent event) {

        reset();
    }

    void reset(){
        addEmpButt.setDisable(false);
        btnDelete.setDisable(true);
        btnOpenMailSendModel.setDisable(true);
        btnUpdate.setDisable(true);
        btnAlEmpReport.setDisable(false);


    }
}