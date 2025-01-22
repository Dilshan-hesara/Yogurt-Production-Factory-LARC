package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.BOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.ResipesBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdMixDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.ProdMixTM;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class ResipeController implements Initializable {


    @FXML
    private TableColumn<String, ProdMixTM> ColGeliy;

    @FXML
    private TableColumn<String, ProdMixTM> cloProdtName;

    @FXML
    private TableColumn<String, ProdMixTM> colMilk;

    @FXML
    private TableColumn<String, ProdMixTM> colSu;

    @FXML
    private TableView<ProdMixTM> tblprodtMix;

    @FXML
    private TextField txtJeliy;

    @FXML
    private TextField txtMilk;

    @FXML
    private TextField txtProdName;

    @FXML
    private TextField txtSugur;

    @FXML
    private Button addrecBtn;

    @FXML
    private Button deleteButt;

    @FXML
    private Button recipBtn;

    @FXML
    private Button resetButt;

    @FXML
    private Button updateBtn;

   //ProdMixModel prodMixModel = new ProdMixModel();

   // ResipesDAO prodMixModel = new ResipesDAOImpl();

    ResipesBO prodMixModel =(ResipesBO) BOFactroy.getInstance().getBO(BOFactroy.BOType.RESIPE);
    @FXML
    void btnAddProd(ActionEvent event) {

        String prodName = txtProdName.getText().trim() + "-Recipe";
        String suguerText = txtSugur.getText().trim();
        String jeliyText = txtJeliy.getText().trim();
        String milkText = txtMilk.getText().trim();


        if (!validateInputs(prodName, suguerText, jeliyText, milkText)) {
            return;
        }

        try {

            int suguer = Integer.parseInt(suguerText);
            int jeliy = Integer.parseInt(jeliyText);
            int milk = Integer.parseInt(milkText);


            ProdMixDto prodMixDto = new ProdMixDto(prodName, suguer, jeliy, milk);


            boolean isSaved = prodMixModel.save(prodMixDto);


            if (isSaved) {
                loadTble();
                cleField();
                prodtionCon.loadNewResip();

                new Alert(Alert.AlertType.INFORMATION, "Product mix saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save the product mix.").show();
            }
        } catch (NumberFormatException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Quantities must be valid integers.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "A database error occurred: " + e.getMessage()).show();
        }
    }

    private boolean validateInputs(String prodName, String suguerText, String jeliyText, String milkText) {
        if (prodName.isEmpty() || suguerText.isEmpty() || jeliyText.isEmpty() || milkText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields must be filled!").show();
            return false;
        }


        try {
            int suguer = Integer.parseInt(suguerText);
            int jeliy = Integer.parseInt(jeliyText);
            int milk = Integer.parseInt(milkText);

            if (suguer <= 0 || jeliy <= 0 || milk <= 0) {
                new Alert(Alert.AlertType.ERROR, "Quantities must be positive integers!").show();
                return false;
            }
        } catch (NumberFormatException e) {

            new Alert(Alert.AlertType.ERROR, "Quantities must be valid integers!").show();
            return false;
        }

        return true;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cloProdtName.setCellValueFactory(new PropertyValueFactory<>("prodName"));
        colMilk.setCellValueFactory(new PropertyValueFactory<>("milk"));
        colSu.setCellValueFactory(new PropertyValueFactory<>("suguer"));
        ColGeliy.setCellValueFactory(new PropertyValueFactory<>("jeliy"));

        deleteButt.setDisable(true);
        updateBtn.setDisable(true);
        resetButt.setDisable(true);

        try {
            loadTble();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTble() throws SQLException, ClassNotFoundException {
        ArrayList<ProdMixDto> prodMixDTOS = prodMixModel.getAll();

        ObservableList<ProdMixTM> prodMixTMS = FXCollections.observableArrayList();


        for (ProdMixDto prodMixDto : prodMixDTOS) {
            ProdMixTM prodMixTM = new ProdMixTM(
                    prodMixDto.getProdName(),
                    prodMixDto.getMilk(),
                    prodMixDto.getSuguer(),
                    prodMixDto.getJeliy()

            );
            prodMixTMS.add(prodMixTM);
        }

        tblprodtMix.setItems(prodMixTMS);

    }


    @FXML
    void btnALLReportResip(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();


            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/production_mix_recip.jrxml"));

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
    public void btnUpdateRecip(ActionEvent actionEvent) {

        ProdMixTM selectedRecipe = tblprodtMix.getSelectionModel().getSelectedItem();
        if (selectedRecipe == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a recipe to update!").show();
            return;
        }

        try {

            String currentProdName = txtProdName.getText().trim();
            if (!selectedRecipe.getProdName().equals(currentProdName)) {
                new Alert(Alert.AlertType.WARNING, "Recipe name cannot be changed!").show();
                txtProdName.setText(selectedRecipe.getProdName());
                return;
            }


            String milkQtyText = txtMilk.getText().trim();
            String suguerQtyText = txtSugur.getText().trim();
            String jeliyQtyText = txtJeliy.getText().trim();

            String qtyPattern = "^[0-9]+$";
            if (!milkQtyText.matches(qtyPattern) || !suguerQtyText.matches(qtyPattern) || !jeliyQtyText.matches(qtyPattern)) {
                new Alert(Alert.AlertType.ERROR, "Please enter valid numeric quantities!").show();
                return;
            }

            int milkQty = Integer.parseInt(milkQtyText);
            int suguerQty = Integer.parseInt(suguerQtyText);
            int jeliyQty = Integer.parseInt(jeliyQtyText);

            boolean isUpdated = prodMixModel.updateRe(
                    selectedRecipe.getProdName(),
                    milkQty,
                    suguerQty,
                    jeliyQty
            );

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Recipe updated successfully!").show();
                loadTble();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update recipe!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the recipe!").show();
        }
    }

    public void tblClik(MouseEvent mouseEvent) {
        ProdMixTM prodMixTM = tblprodtMix.getSelectionModel().getSelectedItem();
        if (prodMixTM != null) {
            txtProdName.setText(prodMixTM.getProdName());
            txtMilk.setText(String.valueOf(prodMixTM.getMilk()));
            txtSugur.setText(String.valueOf(prodMixTM.getSuguer()));
            txtJeliy.setText(String.valueOf(prodMixTM.getJeliy()));

            recipBtn.setDisable(true);
            addrecBtn.setDisable(true);

            deleteButt.setDisable(false);
            updateBtn.setDisable(false);
            resetButt.setDisable(false);
        }
    }

    @FXML
    void deleteButt(ActionEvent event) {
        ProdMixTM selectedRecipe = tblprodtMix.getSelectionModel().getSelectedItem();

        if (selectedRecipe == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a recipe to delete!").show();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete the selected recipe?", ButtonType.YES, ButtonType.NO);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Delete Recipe");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {

                boolean isDeleted = prodMixModel.delete(selectedRecipe.getProdName());

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Recipe deleted successfully!").show();
                    loadTble();
                    prodtionCon.loadNewResip();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete recipe!").show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "recepe is use Prodtion Can not delete").show();
            }
        }
    }
    private void cleField() {
        txtProdName.clear();
        txtSugur.clear();
        txtJeliy.clear();
        txtMilk.clear();
    }




    @FXML
    void resetButt(ActionEvent event) {

        addrecBtn.setDisable(false);
        recipBtn.setDisable(false);
        updateBtn.setDisable(true);
        deleteButt.setDisable(true);
        cleField();
    }
    ProdtionCon prodtionCon = new ProdtionCon();
    public void setUpdatedResipe(ProdtionCon prodtionCon) {
        this.prodtionCon = prodtionCon;
    }
}
