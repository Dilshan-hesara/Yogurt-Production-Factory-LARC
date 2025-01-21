package lk.edu.yogurtproduction.yogurtproductionitsolution.model;

import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CashBookDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MatiralMoadel {


    public String getNextMatId() throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select Mat_ID from material order by Mat_ID desc limit 1";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;

            return String.format("MT%03d", newIdIndex);
        }
        return "MT001";
    }

    public boolean saveMatirial(MatirialDto matirialDto) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into material values (?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setObject(1,matirialDto.getMatId());
        pst.setObject(2,matirialDto.getMatName());
        pst.setObject(3,matirialDto.getMatQty());
        pst.setObject(4,matirialDto.getMatPrice());

        int result = pst.executeUpdate();
        boolean isSaved = result>0;
        return isSaved;

    }

    public ArrayList<MatirialDto> getAllMatireal() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select * from material";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<MatirialDto>  matirialDtos = new ArrayList<>();

        while (rst.next()) {
            MatirialDto matirialDto = new MatirialDto(
                    rst.getString("Mat_ID"),
                    rst.getString("Mat_Name"),
                    rst.getInt("Qty"),
                    rst.getInt("Price")
            );
            matirialDtos.add(matirialDto);

        }
        return matirialDtos;


    }



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



}
