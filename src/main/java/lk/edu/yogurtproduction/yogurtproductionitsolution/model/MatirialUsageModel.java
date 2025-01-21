package lk.edu.yogurtproduction.yogurtproductionitsolution.model;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialUsageDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MatirialUsageModel {
    public boolean saveMatUage(ArrayList<MatirialUsageDto> matirialUsageDTOS) throws SQLException {

        for (MatirialUsageDto matirialUsageDTO : matirialUsageDTOS) {
            boolean isSaved = saveMatUsage(matirialUsageDTO);

            if (!isSaved) {
                return false;
            }
        }

        return true;



    }

    private boolean saveMatUsage(MatirialUsageDto matirialUsageDTO) throws SQLException {

        return   CrudUtil.execute(
                "insert into material_usage values (?, ?, ?, ?, ?)",
                matirialUsageDTO.getMatUs_ID(),
                matirialUsageDTO.getProd_ID(),
                matirialUsageDTO.getMat_Milk(),
                matirialUsageDTO.getMat_Suguer(),
                matirialUsageDTO.getMat_Gelatin()

        );

    }
    public String getmatirialUsageId() throws SQLException {

        ResultSet rst = CrudUtil.execute("select MatUs_ID from material_usage order by MatUs_ID desc limit 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(4);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("MATU%03d", newIdIndex);
        }
        return "MATU001";
        }

    public ArrayList<MatirialUsageDto> getAllMatUsageData() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from material_usage");

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


    public int getAllUsageAvg() throws SQLException {

        ResultSet resultSet = CrudUtil.execute("select(sum(Mat_Milk)+sum(Mat_Suguer)+sum(Mat_Gelatin)) / (count(*) * 3) as AllAvg from material_usage;");

        if (resultSet.next()) {
            return resultSet.getInt("AllAvg");
        }
        return 0;
    }
}

