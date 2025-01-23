package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.DashBoadMainBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.StockDAO;

import java.sql.SQLException;

public class DashBoadMainBOImpl implements DashBoadMainBO {

    StockDAO stockDAO = (StockDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.STOCK);

    @Override
    public Object getAllStockProdAvg() throws SQLException {
        return stockDAO.getAllProdAvg();
    }
}
