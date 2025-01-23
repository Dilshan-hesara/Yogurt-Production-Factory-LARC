package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.SuperBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.StockDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StockBO extends SuperBO {
    public ArrayList<StockDto> getAll() throws SQLException, ClassNotFoundException;
    }
