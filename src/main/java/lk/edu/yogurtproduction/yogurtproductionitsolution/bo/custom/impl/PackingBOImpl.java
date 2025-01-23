package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.PackingBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.*;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.PckingDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdtionDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PackingBOImpl implements PackingBO {

        PackingDAO packingDAO = (PackingDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.PACKING);

        InventroyDAO inventroyDAO = (InventroyDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.INVENTROY);

        StockDAO stockDAO = (StockDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.STOCK);

        EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.EMPLOYEE);

        ProductionDAO productionDAO = (ProductionDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.PRODTION);

    public String getNextId() throws SQLException {

        return packingDAO.getNextId();

    }
    public boolean save(PckingDto pckingDtos) throws SQLException {

        return packingDAO.save(pckingDtos);

    }

    public boolean savePacking(PckingDto pckingDtos) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

//            boolean isRecpSaved = SQLUtil.execute(
//                    "insert into packing values (?, ?, ?, ?, ?, ?, ?,?)",
//                    pckingDtos.getPac_ID(),
//                    pckingDtos.getProd_ID(),
//                    pckingDtos.getPac_Type(),
//                    pckingDtos.getPac_Desc(),
//                    pckingDtos.getPac_Date(),
//                    pckingDtos.getExpire_Date(),
//                    pckingDtos.getQty(),
//                    pckingDtos.getEmp_ID()
//            );
            boolean isRecpSaved = save(pckingDtos);

            if (isRecpSaved) {


                boolean isStockUpdated = stockDAO.saveStock(pckingDtos.getStockDTOS());
                if (isStockUpdated) {
                    boolean redusePackedQty = inventroyDAO.saveredusPackedQty(pckingDtos);
                    if (redusePackedQty) {
                        boolean isInventroyUpdated = inventroyDAO.saveInvetory(pckingDtos.getInventroyDTOS());
                        if (isInventroyUpdated) {
                            connection.commit();
                            return true;
                        }



                    }

                }

            }

            connection.rollback();
            return false;
        } catch(SQLException e){
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally{
            connection.setAutoCommit(true);
        }

    }

    public  String getNextInvId() throws SQLException {

        return inventroyDAO.getNextId();
    }

    public int AvQtyFromSelectProd_ID(String getSelectedProdId) throws SQLException {

        return inventroyDAO.AvQtyFromSelectProd_ID(getSelectedProdId);
    }


   public ArrayList<String> getAllEmpIds() throws SQLException {

        ArrayList<String> empIds = new ArrayList<>();
        ArrayList<String> stringArrayList =employeeDAO.getAllEmpIds();

        for (String string : stringArrayList){
            empIds.add(string);
        }
        return empIds;
  }

   public Employee findByEmployeeID(String cmbEmpSelected) throws SQLException {
        return employeeDAO.findByID(cmbEmpSelected);

   }

    @Override
    public ProdtionDto findProdById(String cmbProdSelected) throws SQLException {
        return productionDAO.findProdById(cmbProdSelected);
    }


    public ArrayList<String> getAllProdtIds() throws SQLException {

        ArrayList<String> prodtIds = new ArrayList<>();

        ArrayList<String> stringArrayList = productionDAO.getAllProdtIds();
        for (String string : stringArrayList){
            prodtIds.add(string);
        }
    return prodtIds;

    }


}
