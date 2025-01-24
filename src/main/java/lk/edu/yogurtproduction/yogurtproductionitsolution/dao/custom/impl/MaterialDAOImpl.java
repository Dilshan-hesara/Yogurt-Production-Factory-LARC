package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.MaterialDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CashBookDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Matirial;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialDAOImpl implements MaterialDAO {




    public String getNextId() throws SQLException {

//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "select Mat_ID from material order by Mat_ID desc limit 1";
//        PreparedStatement pst = connection.prepareStatement(sql);
//        ResultSet rst = pst.executeQuery();

        ResultSet rst= SQLUtil.execute("select Mat_ID from material order by Mat_ID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;

            return String.format("MT%03d", newIdIndex);
        }
        return "MT001";
    }

    public boolean save(Matirial matirialDto) throws SQLException {



        return SQLUtil.execute("insert into material values (?,?,?,?)",
                matirialDto.getMatId(),matirialDto.getMatName(),matirialDto.getMatQty(),matirialDto.getMatPrice());

    }

    public ArrayList<Matirial> getAll() throws SQLException {


        ResultSet rst = SQLUtil.execute("select * from material");
        ArrayList<Matirial> matirialDtos = new ArrayList<>();

        while (rst.next()) {
            Matirial matirialDto = new Matirial(
                    rst.getString("Mat_ID"),
                    rst.getString("Mat_Name"),
                    rst.getInt("Qty"),
                    rst.getInt("Price")
            );
            matirialDtos.add(matirialDto);
        }

        return matirialDtos;

    }

//ou

    @Override
    public MatirialDto findById(String selectID) throws SQLException {

        ResultSet rst = SQLUtil.execute("select * from material where Mat_ID=?", selectID);

        if (rst.next()) {
            return new MatirialDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getInt(4)
            );
        }
        return null;
    }

    @Override
    public ArrayList<String> getAllItemIds() throws SQLException {


        ResultSet rst = SQLUtil.execute("select Mat_ID from material");

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }

        return itemIds;
    }



    public boolean updatedMatirialReduceQty(CashBookDto cashBookDto) throws SQLException {
        return SQLUtil.execute(
                "update material set Qty = Qty - ? where Mat_ID = ?",
                cashBookDto.getQty(),
                cashBookDto.getMatID()
        );
    }




    @Override
    public boolean update(Matirial dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String empId) throws SQLException {
        return false;
    }

    @Override
    public Matirial findByID(String cmbEmpSelected) throws SQLException {
        return null;
    }


}
