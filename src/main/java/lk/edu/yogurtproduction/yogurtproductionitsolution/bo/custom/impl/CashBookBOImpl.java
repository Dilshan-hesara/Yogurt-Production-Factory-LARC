package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.CashBookBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.CashBookDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.InventroyDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.MaterialDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.SupplierDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CashBookDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.CashBook;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Supplier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashBookBOImpl implements CashBookBO {

    CashBookDAO cashBookDAO =(CashBookDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.CASHBOOK);

    MaterialDAO materialDAO = (MaterialDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.MATERIAL);

    InventroyDAO inventroyDAO = (InventroyDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.INVENTROY);

    SupplierDAO supplierDAO = (SupplierDAO)  DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.SUPPLIER);

    //MaterialDAO materialDAO = (MaterialDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.MATERIAL);

  //  MaterialDAO materialDAO = (MaterialDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.MATERIAL);

    public String getNextId() throws SQLException {

        return cashBookDAO.getNextId();

    }

    public ArrayList<CashBookDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<CashBookDto> cashBookDtos = new ArrayList<>();
        ArrayList<CashBook> cashBookDtoArrayList = cashBookDAO.getAll();
        for (CashBook cashBookDto : cashBookDtoArrayList) {
            cashBookDtos.add(new CashBookDto(new CashBook(
                    cashBookDto.getCBNo(),
                    cashBookDto.getSupId(),
                    cashBookDto.getMatID(),
                    cashBookDto.getInID(),
                    cashBookDto.getDesc(),
                    cashBookDto.getQty(),
                    cashBookDto.getAmount(),
                    cashBookDto.getDate()
            )));
        }

        return cashBookDtos;
    }

    public boolean save(CashBookDto cashBookDto) throws SQLException {

       return cashBookDAO.save (new CashBook(
               cashBookDto.getCBNo(),
               cashBookDto.getSupId(),
               cashBookDto.getMatID(),
               cashBookDto.getInID(),
               cashBookDto.getDesc(),
               cashBookDto.getQty(),
               cashBookDto.getAmount(),
               cashBookDto.getDate()
       ));
    }


    @Override
    public int getAllPayAmount() throws SQLException {
        return cashBookDAO.getAllPayAmount();

    }

    @Override
    public Boolean saveResept(CashBookDto cashBookDto) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);


            boolean isRecpSaved = save(cashBookDto);


            if (isRecpSaved) {

                boolean isInventroyUpdated = inventroyDAO.saveInvetory(cashBookDto.getInventroyDTOS());
                if (isInventroyUpdated) {

                    boolean isMatirealUpdated = materialDAO.updatedMatirialReduceQty(cashBookDto);
                    if (isMatirealUpdated) {
                        connection.commit();


                        return true;
                    }

                }

            }

            connection.rollback();
            return false;
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }


    public ArrayList<String> getAllSupIds() throws SQLException {
       return supplierDAO.getAllSupIds();

    }
    public Supplier findByID(String selectID) throws SQLException {

        return supplierDAO.findByID(selectID);
    }

    public ArrayList<String> getAllItemIds() throws SQLException {

        ArrayList<String> itemIds = new ArrayList<>();

        ArrayList<String>stringArrayList = materialDAO.getAllItemIds();

        for (String string : stringArrayList){
            itemIds.add(string);

        }
        return itemIds;
    }

     public MatirialDto findById(String selectID) throws SQLException {

        return materialDAO.findById(selectID);


    }

}
