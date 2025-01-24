package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.ResipesBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.ResipesDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.ResipesDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdMixDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Resipe;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResipesBOImpl implements ResipesBO {

    ResipesDAO resipesDAO = (ResipesDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.RESIPE);

    public boolean save(ProdMixDto prodMixDto) throws SQLException {

        return resipesDAO.save(new Resipe(
                prodMixDto.getProdName(),
                prodMixDto.getMilk(),
                prodMixDto.getSuguer(),
                prodMixDto.getJeliy()
        ));

    }



    public ArrayList<ProdMixDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<ProdMixDto> prodMixDtos = new ArrayList<>();
        ArrayList<Resipe> prodMixDtoArrayList =resipesDAO.getAll();

        for (Resipe resipe : prodMixDtoArrayList) {
            prodMixDtos.add(new ProdMixDto(new Resipe(
                    resipe.getProdName(),
                    resipe.getMilk(),
                    resipe.getSuguer(),
                    resipe.getJeliy()
            )));
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
