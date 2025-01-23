package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.ProductionBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.*;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdtionDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductionBOImpl implements ProductionBO {

    MaterialUsageDAO materialUsageDAO = (MaterialUsageDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.MATERIAL_USAGE);

    InventroyDAO inventroyDAO = (InventroyDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.INVENTROY);

    ProductionDAO productionDAO = (ProductionDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.PRODTION);


    public String getNextId() throws SQLException {

        return productionDAO.getNextId();

    }

    public ArrayList<ProdtionDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<ProdtionDto> prodtionDtos = new ArrayList<>();
        ArrayList<ProdtionDto> prodtionDtoArrayList = productionDAO.getAll();
        for (ProdtionDto prodtionDto : prodtionDtoArrayList) {
            prodtionDtos.add(prodtionDto);
        }
        return prodtionDtos;
    }

    public boolean save(ProdtionDto prodtionDto) throws SQLException {

     return productionDAO.save(prodtionDto);
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


}
