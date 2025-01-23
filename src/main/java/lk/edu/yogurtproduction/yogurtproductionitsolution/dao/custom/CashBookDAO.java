package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CashBookDto;

import java.sql.SQLException;

public interface CashBookDAO extends CrudDAO<CashBookDto> {


    public int getAllPayAmount() throws SQLException;
}
