package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.MaterialUsageDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialUsageDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialUsageDAOImpl implements MaterialUsageDAO {

    public String getNextId() throws SQLException {

        ResultSet rst = SQLUtil.execute("select MatUs_ID from material_usage order by MatUs_ID desc limit 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(4);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("MATU%03d", newIdIndex);
        }
        return "MATU001";
    }

    public ArrayList<MatirialUsageDto> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from material_usage");

        ArrayList<MatirialUsageDto> matirialUsageDTOS = new ArrayList<>();

        while (rst.next()) {
            MatirialUsageDto matirialUsageDTO = new MatirialUsageDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            matirialUsageDTOS.add(matirialUsageDTO);
        }
        return matirialUsageDTOS;

    }




    @Override
    public int getAllUsageAvg() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("select(sum(Mat_Milk)+sum(Mat_Suguer)+sum(Mat_Gelatin)) / (count(*) * 3) as AllAvg from material_usage;");

        if (resultSet.next()) {
            return resultSet.getInt("AllAvg");
        }
        return 0;
    }





    @Override
    public boolean save(MatirialUsageDto dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(MatirialUsageDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String empId) throws SQLException {
        return false;
    }

    @Override
    public MatirialUsageDto findByID(String cmbEmpSelected) throws SQLException {
        return null;
    }
}
