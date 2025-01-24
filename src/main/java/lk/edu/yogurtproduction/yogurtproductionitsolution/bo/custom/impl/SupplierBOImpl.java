package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.SupplierBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.SupplierDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.SupplierDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.SUPPLIER);

    @Override
    public String getNextId() throws SQLException {


       return supplierDAO.getNextId();
    }

    public boolean save(SupplierDto suplierDTO) throws SQLException {

        return supplierDAO.save(new Supplier(suplierDTO.getSupId(),suplierDTO.getSupName(),suplierDTO.getSupNic(),suplierDTO.getSupEmail(),suplierDTO.getSupPhone()));
  }



    public boolean update(SupplierDto suplierDTO) throws SQLException, ClassNotFoundException {


        return supplierDAO.update(new Supplier(suplierDTO.getSupId(),suplierDTO.getSupName(),suplierDTO.getSupNic(),suplierDTO.getSupEmail(),suplierDTO.getSupPhone()));
    }

    public ArrayList<SupplierDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<SupplierDto> suplierDTOArrayList = new ArrayList<>();

        ArrayList<Supplier>suplierDTOList = supplierDAO.getAll();

        for (Supplier suplierDTO : suplierDTOList) {
            suplierDTOArrayList.add(new SupplierDto(new Supplier(suplierDTO.getSupId(),suplierDTO.getSupName(),suplierDTO.getSupNic(),suplierDTO.getSupEmail(),suplierDTO.getSupPhone())));

        }
        return suplierDTOArrayList;
    }


    public boolean delete(String supId) throws SQLException {

       return supplierDAO.delete(supId);
    }

}
