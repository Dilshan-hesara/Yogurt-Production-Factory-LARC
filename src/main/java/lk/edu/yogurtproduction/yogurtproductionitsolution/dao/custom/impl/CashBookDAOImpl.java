package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.CashBookDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.CashBook;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashBookDAOImpl implements CashBookDAO {


    public String getNextId() throws SQLException {
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

    public ArrayList<CashBook> getAll() throws SQLException {

        ResultSet rst = SQLUtil.execute("select * from Cash_Book");

        ArrayList<CashBook> cashBookDTOS = new ArrayList<>();

        while (rst.next()) {
            CashBook cashBookDTO = new CashBook(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getDouble(7),
                    rst.getString(8)

            );



            cashBookDTOS.add(cashBookDTO);
        }
        return cashBookDTOS;
    }

    public boolean save(CashBook cashBookDto) throws SQLException {

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



    public int getAllPayAmount() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select sum(Amount) as Total_Amount from cash_book");

        if (resultSet.next()) {
            return resultSet.getInt("Total_Amount");
        }
        return 0;
    }
















    @Override
    public boolean update(CashBook dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String empId) throws SQLException {
        return false;
    }

    @Override
    public CashBook findByID(String cmbEmpSelected) throws SQLException {
        return null;
    }


}
