package lk.edu.yogurtproduction.yogurtproductionitsolution.model;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.InventroyDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.InventroyDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdtionDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProdtionModel {

    InventroyDAO inventoryModel = new InventroyDAOImpl();
    public String getNextProdtID() throws SQLException {
        ResultSet rst = SQLUtil.execute("select Prod_ID from production order by Prod_ID desc limit 1");
        if (rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i+1;
            return String.format("P%03d",newIdIndex);
        }
        return  "P001";
    }
    MatirialUsageModel matirialUsageModel = new MatirialUsageModel();

    public boolean saveProdt(ProdtionDto prodtionDto) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isProdtSaved = SQLUtil.execute(
                    "insert into production values (?,?,?,?)",
                    prodtionDto.getProd_ID(),
                    prodtionDto.getPro_Name(),
                    prodtionDto.getProd_Qty(),
                    prodtionDto.getProd_Name()
            );
            if (isProdtSaved) {
                System.out.println("dev");

                boolean isInvetriyUpdated = inventoryModel.saveInvetory(prodtionDto.getInventroyDTOS());
                if (isInvetriyUpdated) {


                    boolean isUpdateInverorySaved = inventoryModel.redusqtyOnInventroyOnItems(prodtionDto.getProdMixDTOS());
               if (isUpdateInverorySaved) {
                   boolean isMatUsageSaved = matirialUsageModel.saveMatUage(prodtionDto.getMatirialUsageDTOS());
                   if (isMatUsageSaved) {
                       connection.commit();
                       return true;
                   }



               }

                }

            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }

    }

    public ArrayList<String> getAllProdtIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM production LIMIT 1000;");

        ArrayList<String> ProdtIds = new ArrayList<>();

        while (rst.next()) {
            ProdtIds.add(rst.getString(1));
        }

        return ProdtIds;
    }

    public ProdtionDto findById(String cmbProdSelected) throws SQLException {


        ResultSet rst = SQLUtil.execute("select * from production where Prod_ID=?", cmbProdSelected);

        if (rst.next()) {
            return new ProdtionDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4)

            );


        }
        return null;
    }

    public ArrayList<ProdtionDto> getAllProdtionData() throws SQLException {

        ResultSet rst = SQLUtil.execute("select * from production");

        ArrayList<ProdtionDto> prodtionDTOS = new ArrayList<>();

        while (rst.next()) {
            ProdtionDto prodtionDTO = new ProdtionDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4)
            );
            prodtionDTOS.add(prodtionDTO);
        }
        return prodtionDTOS;
    }
}

