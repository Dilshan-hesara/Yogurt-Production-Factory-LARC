package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.SuperBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.SupplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {


    public String getNextId() throws SQLException ;

    public boolean save(SupplierDto suplierDTO) throws SQLException ;



    public boolean update(SupplierDto suplierDTO) throws SQLException, ClassNotFoundException;

    public ArrayList<SupplierDto> getAll() throws SQLException, ClassNotFoundException;


    public boolean delete(String supId) throws SQLException;
}
