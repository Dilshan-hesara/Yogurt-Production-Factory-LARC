package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CashBookDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.CashBook;

import java.sql.SQLException;

public interface CashBookDAO extends CrudDAO<CashBook> {


     int getAllPayAmount() throws SQLException;
}
