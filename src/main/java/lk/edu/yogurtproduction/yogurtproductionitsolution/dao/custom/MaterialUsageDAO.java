package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialUsageDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.MatirialUsage;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MaterialUsageDAO extends CrudDAO<MatirialUsage> {

    public int getAllUsageAvg() throws SQLException;

    boolean save(MatirialUsageDto matirialUsageDTO) throws SQLException;

    public boolean UMatUage(ArrayList<MatirialUsageDto> matirialUsageDTOS) throws SQLException ;
    }
