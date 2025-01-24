package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.SuperDAO;

import java.sql.SQLException;

public interface QueryDAO extends SuperDAO {

    void findsByProdDerailID() throws SQLException, ClassNotFoundException;
}
