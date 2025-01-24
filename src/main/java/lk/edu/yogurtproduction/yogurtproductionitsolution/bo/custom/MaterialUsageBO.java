package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.SuperBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialUsageDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MaterialUsageBO extends SuperBO {


    public ArrayList<MatirialUsageDto> getAll() throws SQLException, ClassNotFoundException;
}
