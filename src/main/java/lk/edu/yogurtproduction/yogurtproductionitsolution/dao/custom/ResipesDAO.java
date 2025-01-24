package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdMixDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Resipe;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ResipesDAO extends CrudDAO<Resipe> {

    public ArrayList<String> getAllProdName() throws SQLException ;
    public boolean updateRe(String prodName, int milk, int suguer, int jeliy) throws SQLException ;

    }
