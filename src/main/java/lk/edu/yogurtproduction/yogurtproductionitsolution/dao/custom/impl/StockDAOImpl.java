package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.StockDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.StockDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockDAOImpl implements StockDAO {

    public String getNextId() throws SQLException {

        ResultSet rst = SQLUtil.execute("select Stock_ID from Stock order by Stock_ID desc limit 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(3);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("ST%03d", newIdIndex);
        }
        return "ST001";

    }



    private boolean savedStock(StockDto stockDTO) throws SQLException {
        return   SQLUtil.execute(

                "insert into Stock values (?, ?, ?, ?,?,?,?,?)",
                stockDTO.getStock_ID(),
                stockDTO.getPac_ID(),
                stockDTO.getProduct_Name(),
                stockDTO.getQty(),
                stockDTO.getManfac_date(),
                stockDTO.getExpire_date(),
                stockDTO.getPack_Type(),
                stockDTO.getUnit_Price()

        );

    }

    public ArrayList<StockDto> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Stock");

        ArrayList<StockDto> stockDTOS = new ArrayList<>();

        while (rst.next()) {
            StockDto stockDTO = new StockDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getDouble(8)
            );
            stockDTOS.add(stockDTO);
        }
        return stockDTOS;
    }

    public Object getAllProdAvg() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select sum(Daily_Avg) as Total_Avg from ( select avg(Qty) as Daily_Avg from Stock group by Manfac_date ) as DailyAvgs;");

        if (resultSet.next()) {
            return resultSet.getInt("Total_Avg");
        }
        return 0.0;
    }




    public boolean saveStock(ArrayList<StockDto> stockDTOS) throws SQLException {
        for (StockDto stockDTO : stockDTOS) {
            boolean isSaved = savedStock(stockDTO);

            if (!isSaved) {
                return false;
            }
        }

        return true;
    }


    @Override
    public boolean save(StockDto dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(StockDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String empId) throws SQLException {
        return false;
    }

    @Override
    public StockDto findByID(String cmbEmpSelected) throws SQLException {
        return null;
    }
}
