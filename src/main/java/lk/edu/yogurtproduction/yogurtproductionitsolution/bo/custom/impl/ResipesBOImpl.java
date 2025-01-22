package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.ResipesBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.ResipesDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.ResipesDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdMixDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResipesBOImpl implements ResipesBO {

    ResipesDAO resipesDAO = (ResipesDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.RESIPE);

    public boolean save(ProdMixDto prodMixDto) throws SQLException {

        return resipesDAO.save(prodMixDto);

    }




    public ArrayList<ProdMixDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<ProdMixDto> prodMixDtos = new ArrayList<>();
        ArrayList<ProdMixDto> prodMixDtoArrayList =resipesDAO.getAll();

        for (ProdMixDto prodMixDto : prodMixDtoArrayList) {
            prodMixDtos.add(prodMixDto);
        }

        return prodMixDtos;
    }



    @Override
    public boolean updateRe(String prodName, int milk, int suguer, int jeliy) throws SQLException {

        return resipesDAO.updateRe(prodName, milk, suguer, jeliy);
    }

    @Override
    public boolean delete(String prodName) throws SQLException {

        return resipesDAO.delete(prodName);
    }


}
