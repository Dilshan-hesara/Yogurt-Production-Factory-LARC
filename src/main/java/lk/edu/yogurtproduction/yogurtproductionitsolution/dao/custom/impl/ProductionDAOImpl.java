package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.ProductionDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdtionDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductionDAOImpl implements ProductionDAO {

    public String getNextId() throws SQLException {
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

    public ArrayList<ProdtionDto> getAll() throws SQLException {

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

    public boolean save(ProdtionDto prodtionDto) throws SQLException {

        return SQLUtil.execute(
                "insert into production values (?,?,?,?)",
                prodtionDto.getProd_ID(),
                prodtionDto.getPro_Name(),
                prodtionDto.getProd_Qty(),
                prodtionDto.getProd_Name()
        );
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





















    @Override
    public boolean update(ProdtionDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String empId) throws SQLException {
        return false;
    }

    @Override
    public ProdtionDto findByID(String cmbEmpSelected) throws SQLException {
        return null;
    }
}
