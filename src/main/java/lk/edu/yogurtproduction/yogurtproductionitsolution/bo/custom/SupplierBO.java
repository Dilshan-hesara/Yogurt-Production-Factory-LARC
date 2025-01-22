package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.SuperBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.SuplierDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Employee;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {


    public String getNextId() throws SQLException ;

    public boolean save(SuplierDto suplierDTO) throws SQLException ;



    public boolean update(SuplierDto suplierDTO) throws SQLException, ClassNotFoundException;

    public ArrayList<SuplierDto> getAll() throws SQLException, ClassNotFoundException;


    public boolean delete(String supId) throws SQLException;
}
