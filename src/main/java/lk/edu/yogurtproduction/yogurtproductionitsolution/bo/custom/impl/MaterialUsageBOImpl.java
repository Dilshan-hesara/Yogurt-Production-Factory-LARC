package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.MaterialUsageBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.MaterialUsageDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialUsageDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialUsageBOImpl implements MaterialUsageBO {

    MaterialUsageDAO materialUsageDAO = (MaterialUsageDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.MATERIAL_USAGE);


    public ArrayList<MatirialUsageDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<MatirialUsageDto> matirialUsageDtos = new ArrayList<>();
        ArrayList<MatirialUsageDto> matirialUsageDtoArrayList = materialUsageDAO.getAll();

        for (MatirialUsageDto matirialUsageDto : matirialUsageDtoArrayList) {
            matirialUsageDtos.add(matirialUsageDto);
        }

        return matirialUsageDtos;
    }
}
