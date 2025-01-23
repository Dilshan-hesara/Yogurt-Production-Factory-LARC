package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.SuperBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.PckingDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdtionDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PackingBO extends SuperBO {

    public String getNextId() throws SQLException;

    public boolean save(PckingDto pckingDtos) throws SQLException;

    public boolean savePacking(PckingDto pckingDtos) throws SQLException;

    String getNextInvId() throws SQLException;

    int AvQtyFromSelectProd_ID(String getSelectedProdId) throws SQLException;

    ArrayList<String> getAllEmpIds() throws SQLException;

    Employee findByEmployeeID(String cmbEmpSelected) throws SQLException;

    ProdtionDto findProdById(String cmbProdSelected) throws SQLException;

    ArrayList<String> getAllProdtIds() throws SQLException;
}