package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.MaterialUsageBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.ProductionBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.*;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdtionDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Prodtion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductionBOImpl implements ProductionBO {

    MaterialUsageDAO materialUsageDAO = (MaterialUsageDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.MATERIAL_USAGE);

    InventroyDAO inventroyDAO = (InventroyDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.INVENTROY);

    ProductionDAO productionDAO = (ProductionDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.PRODTION);

    ResipesDAO resipesDAO = (ResipesDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.RESIPE);


    public String getNextId() throws SQLException {

        return productionDAO.getNextId();

    }

    public ArrayList<ProdtionDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<ProdtionDto> prodtionDtos = new ArrayList<>();
        ArrayList<Prodtion> prodtionDtoArrayList = productionDAO.getAll();
        for (Prodtion prodtion : prodtionDtoArrayList) {
            prodtionDtos.add(new ProdtionDto(new Prodtion(
                    prodtion.getProd_ID(),
                    prodtion.getPro_Name(),
                    prodtion.getProd_Qty(),
                    prodtion.getProd_Name()
            )));
        }
        return prodtionDtos;
    }

    public boolean save(ProdtionDto prodtionDto) throws SQLException {

     return productionDAO.save(new Prodtion(  prodtionDto.getProd_ID(),
             prodtionDto.getPro_Name(),
             prodtionDto.getProd_Qty(),
             prodtionDto.getProd_Name()
     ));
    }


    public boolean saveProdt(ProdtionDto prodtionDto) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isProdtSaved = save(prodtionDto);

            if (isProdtSaved) {
                System.out.println("dev");

                boolean isInvetriyUpdated = inventroyDAO.saveInvetory(prodtionDto.getInventroyDTOS());
                if (isInvetriyUpdated) {


                    boolean isUpdateInverorySaved = inventroyDAO.redusqtyOnInventroyOnItems(prodtionDto.getProdMixDTOS());
                    if (isUpdateInverorySaved) {
                        boolean isMatUsageSaved = materialUsageDAO.UMatUage(prodtionDto.getMatirialUsageDTOS());
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

    MaterialUsageBO materialUsage =new MaterialUsageBOImpl();


    public String getNextMatId() throws SQLException {

        return materialUsageDAO.getNextId();
    }

    @Override
    public ArrayList<String> getAllProdName() throws SQLException {

        return resipesDAO.getAllProdName();
    }

    @Override
    public String getNextInventroyId() throws SQLException {
        return inventroyDAO.getNextId();
    }

    @Override
    public ArrayList<String> getAllAvItems() throws SQLException {

        ArrayList<String> avItems = new ArrayList<>();
        ArrayList<String>stringArrayList = inventroyDAO.getAllAVItems();

        for (String string : stringArrayList) {
            avItems.add(string);
        }
        return avItems;
    }


}
