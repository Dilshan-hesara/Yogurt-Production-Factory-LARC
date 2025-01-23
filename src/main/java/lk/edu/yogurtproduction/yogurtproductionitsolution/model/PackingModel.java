package lk.edu.yogurtproduction.yogurtproductionitsolution.model;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.InventroyDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.StockDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.InventroyDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.StockDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.PckingDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PackingModel {

    InventroyDAO inventoryModel = new InventroyDAOImpl();
   //InventroyModel inventoryModel = new InventroyModel();
   // StockModel stockModel = new StockModel();
    StockDAO stockModel = new StockDAOImpl();
    public boolean savePacking(PckingDto pckingDtos) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isRecpSaved = SQLUtil.execute(
                    "insert into packing values (?, ?, ?, ?, ?, ?, ?,?)",
                    pckingDtos.getPac_ID(),
                    pckingDtos.getProd_ID(),
                    pckingDtos.getPac_Type(),
                    pckingDtos.getPac_Desc(),
                    pckingDtos.getPac_Date(),
                    pckingDtos.getExpire_Date(),
                    pckingDtos.getQty(),
                    pckingDtos.getEmp_ID()
            );

            if (isRecpSaved) {


                    boolean isStockUpdated = stockModel.saveStock(pckingDtos.getStockDTOS());
                    if (isStockUpdated) {
                        boolean redusePackedQty = inventoryModel.saveredusPackedQty(pckingDtos);
                        if (redusePackedQty) {
                            boolean isInventroyUpdated = inventoryModel.saveInvetory(pckingDtos.getInventroyDTOS());
                            if (isInventroyUpdated) {
                                connection.commit();
                                return true;
                            }



                    }

                }



            }

            connection.rollback();
            return false;
        } catch(SQLException e){
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally{
            connection.setAutoCommit(true);
        }

    }

    public String getPackId() throws SQLException {

        ResultSet rst = SQLUtil.execute("select Pac_ID from packing order by Pac_ID desc limit 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(3);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("PAC%03d", newIdIndex);
        }
        return "PAC001";


    }
}
