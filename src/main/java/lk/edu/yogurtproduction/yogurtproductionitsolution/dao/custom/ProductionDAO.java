package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdtionDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Prodtion;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductionDAO extends CrudDAO<Prodtion> {

    public ArrayList<String> getAllProdtIds() throws SQLException;

    public ProdtionDto findProdById(String cmbProdSelected) throws SQLException;
}
