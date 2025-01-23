package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.PackingDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.PckingDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PackingDAOImpl implements PackingDAO {




    public String getNextId() throws SQLException {

        ResultSet rst = SQLUtil.execute("select Pac_ID from packing order by Pac_ID desc limit 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(3);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("PAC%03d", newIdIndex);
        }
        return "PAC001";


    }
    public boolean save(PckingDto pckingDtos) throws SQLException {

        return SQLUtil.execute(
                "insert into packing values (?, ?, ?, ?, ?, ?, ?,?)",
                pckingDtos.getPac_ID(),
                pckingDtos.getProd_ID(),
                pckingDtos.getPac_Type(),
                pckingDtos.getPac_Desc(),
                pckingDtos.getPac_Date(),
                pckingDtos.getExpire_Date(),
                pckingDtos.getQty(),
                pckingDtos.getEmp_ID()
        );
    }





















    @Override
    public ArrayList<PckingDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(PckingDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String empId) throws SQLException {
        return false;
    }

    @Override
    public PckingDto findByID(String cmbEmpSelected) throws SQLException {
        return null;
    }
}
