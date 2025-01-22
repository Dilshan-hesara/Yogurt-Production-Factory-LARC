package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.SupplierBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.SupplierDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.SupplierDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.SuplierDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Employee;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Supplier;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.SUPPLIER);

    @Override
    public String getNextId() throws SQLException {


       return supplierDAO.getNextId();
    }

    public boolean save(SuplierDto suplierDTO) throws SQLException {

       return supplierDAO.save(suplierDTO);
  }



    public boolean update(SuplierDto suplierDTO) throws SQLException, ClassNotFoundException {

      return   supplierDAO.update(suplierDTO);
  }

    public ArrayList<SuplierDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<SuplierDto> suplierDTOArrayList = new ArrayList<>();

        ArrayList<SuplierDto> supplierList = supplierDAO.getAll();

        for (SuplierDto suplierDTO : supplierList) {
            suplierDTOArrayList.add(suplierDTO);
        }
        return suplierDTOArrayList;
    }


    public boolean delete(String supId) throws SQLException {

       return supplierDAO.delete(supId);
    }

}
