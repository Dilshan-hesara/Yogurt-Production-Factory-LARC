package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.SuperBO;

import java.sql.SQLException;

public interface DashBoadMainBO extends SuperBO {
    Object getAllStockProdAvg() throws SQLException;
}
