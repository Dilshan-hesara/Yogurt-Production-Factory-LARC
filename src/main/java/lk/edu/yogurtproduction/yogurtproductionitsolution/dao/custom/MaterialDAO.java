package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CashBookDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface MaterialDAO extends CrudDAO<MatirialDto> {

    public MatirialDto findById(String selectID) throws SQLException ;

    public ArrayList<String> getAllItemIds() throws SQLException;

    public boolean updatedMatirialReduceQty(CashBookDto cashBookDto) throws SQLException ;
    }
