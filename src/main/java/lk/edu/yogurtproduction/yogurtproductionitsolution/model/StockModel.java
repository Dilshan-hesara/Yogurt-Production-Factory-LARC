package lk.edu.yogurtproduction.yogurtproductionitsolution.model;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.OrderDetailsDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.StockDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockModel {

    public String getStockId() throws SQLException {

        ResultSet rst = CrudUtil.execute("select Stock_ID from Stock order by Stock_ID desc limit 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(3);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("ST%03d", newIdIndex);
        }
        return "ST001";

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

    private boolean savedStock(StockDto stockDTO) throws SQLException {
        return   CrudUtil.execute(

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

    public ArrayList<StockDto> getAllStockData() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Stock");

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
        ResultSet resultSet = CrudUtil.execute("select sum(Daily_Avg) as Total_Avg from ( select avg(Qty) as Daily_Avg from Stock group by Manfac_date ) as DailyAvgs;");

        if (resultSet.next()) {
            return resultSet.getInt("Total_Avg");
        }
        return 0.0;
    }


    public ArrayList<String> getAllProdIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Stock_ID from Stock");

        ArrayList<String> stockds = new ArrayList<>();

        while (rst.next()) {
            stockds.add(rst.getString(1));
        }

        return stockds;

    }

    public StockDto findById(String selectedProdt) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from stock where Stock_ID =?", selectedProdt);

        if (rst.next()) {
            return new StockDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getDouble(8)
            );

        }

        return null;

    }



    public boolean onOderRedQty(OrderDetailsDto orderDetailsDTO) throws SQLException {

        return CrudUtil.execute(
                "update stock set Qty = Qty - ? where Stock_ID = ?",
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getItemId()
        );
    }
}
