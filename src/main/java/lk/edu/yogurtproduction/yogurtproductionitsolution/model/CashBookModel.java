package lk.edu.yogurtproduction.yogurtproductionitsolution.model;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.CashBookDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.InventroyDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.MaterialDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.CashBookDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.InventroyDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.MaterialDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CashBookDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.InventroyDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.io.InvalidClassException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CashBookModel {

    InventroyDAO inventoryModel = new InventroyDAOImpl();
    MaterialDAO materialModel = new MaterialDAOImpl();

//    public boolean saveInvetory(ArrayList<InventroyDto> inventroyDTOS) throws SQLException {
//        for (InventroyDto inventroyDTO : inventroyDTOS) {
//            boolean isSaved = saveCashBook(inventroyDTO);
//
//            if (!isSaved) {
//                return false;
//            }
//        }
//
//        return true;
//    }

    CashBookDAO cashModel = new CashBookDAOImpl();

    private boolean saveCashBook(CashBookDto cashBookDto) throws SQLException {

        return SQLUtil.execute(
                "insert into Cash_Book values (?, ?, ?, ?, ?, ?, ?, ?)",
                cashBookDto.getCBNo(),
                cashBookDto.getSupId(),
                cashBookDto.getMatID(),
                cashBookDto.getInID(),
                cashBookDto.getDesc(),
                cashBookDto.getQty(),
                cashBookDto.getAmount(),
                cashBookDto.getDate()
        );
    }

    public Boolean saveResept(CashBookDto cashBookDto) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);


                boolean isRecpSaved =cashModel.save(cashBookDto);


            if (isRecpSaved) {

                boolean isInventroyUpdated = inventoryModel.saveInvetory(cashBookDto.getInventroyDTOS());
                if (isInventroyUpdated) {

                    boolean isMatirealUpdated = materialModel.updatedMatirialReduceQty(cashBookDto);
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




//            boolean isRecpSaved = SQLUtil.execute(
//                    "insert into Cash_Book values (?, ?, ?, ?, ?, ?, ?, ?)",
//                    cashBookDto.getCBNo(),
//                    cashBookDto.getSupId(),
//                    cashBookDto.getMatID(),
//                    cashBookDto.getInID(),
//                    cashBookDto.getDesc(),
//                    cashBookDto.getQty(),
//                    cashBookDto.getAmount(),
//                    cashBookDto.getDate()
//            );

    public String getNextCBNId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select CB_No from Cash_Book order by CB_No desc limit 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(3);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("CBN%03d", newIdIndex);
        }
        return "CBN001";

    }

    public ArrayList<CashBookDto> getAllCustomers() throws SQLException {

        ResultSet rst = SQLUtil.execute("select * from Cash_Book");

        ArrayList<CashBookDto> cashBookDTOS = new ArrayList<>();

        while (rst.next()) {
            CashBookDto cashBookDTO = new CashBookDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getDouble(7),
                    rst.getString(8)

            );

            ;

            cashBookDTOS.add(cashBookDTO);
        }
        return cashBookDTOS;
    }


    public int getAllPayAmount() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select sum(Amount) as Total_Amount from cash_book");

        if (resultSet.next()) {
            return resultSet.getInt("Total_Amount");
        }
        return 0;
    }
}


