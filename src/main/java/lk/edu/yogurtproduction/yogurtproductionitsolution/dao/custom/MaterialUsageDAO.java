package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialUsageDto;

import java.sql.SQLException;

public interface MaterialUsageDAO extends CrudDAO<MatirialUsageDto> {

    public int getAllUsageAvg() throws SQLException;
    }
