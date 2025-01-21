package lk.edu.yogurtproduction.yogurtproductionitsolution.model;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdMixDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdMixModel {
    public boolean saveProdtMaix(ProdMixDto prodMixDto) throws SQLException {

        boolean isSaved =  SQLUtil.execute(
                "insert into production_mix_recip values (?,?,?,?)",

                prodMixDto.getProdName(),
                prodMixDto.getMilk(),
                prodMixDto.getSuguer(),
                prodMixDto.getJeliy()
        );
        return isSaved;
    }

    public ArrayList<String> getAllProdName() throws SQLException {

        ResultSet rst = SQLUtil.execute("select Prod_Name from production_mix_recip");

        ArrayList<String> prodtName = new ArrayList<>();

        while (rst.next()) {
            prodtName.add(rst.getString(1));
        }

        return prodtName;
    }


    public ProdMixDto findbyname(String selectProd) throws SQLException {

        ResultSet rst = SQLUtil.execute("select * from production_mix_recip where Prod_Name=?", selectProd);

        if (rst.next()) {
            return new ProdMixDto(
                    rst.getString(1),
                    rst.getInt(2),
                    rst.getInt(3),
                    rst.getInt(4)
            );
        }
        return null;

    }

    public ArrayList<ProdMixDto> getAllInventoryData() throws SQLException {

        ResultSet rst = SQLUtil.execute("select * from production_mix_recip");

        ArrayList<ProdMixDto> prodMixDTOS = new ArrayList<>();

        while (rst.next()) {
            ProdMixDto prodMixDTO = new ProdMixDto(
                    rst.getString(1),
                    rst.getInt(2),
                    rst.getInt(3),
                    rst.getInt(4)
            );
            prodMixDTOS.add(prodMixDTO);
        }
        return prodMixDTOS;


    }

    public boolean updateQuantities(String prodName, int milk, int suguer, int jeliy) throws SQLException {
        return SQLUtil.execute(
                "update production_mix_recip set Milk_qty = ?, Sugur_qty = ?, Jeliy_qty = ? where Prod_Name = ?",
                milk,
                suguer,
                jeliy,
                prodName
        );
    }

    public boolean deleteRecipe(String prodName) throws SQLException {
        return SQLUtil.execute("delete from production_mix_recip where Prod_Name = ?", prodName);
    }

}
