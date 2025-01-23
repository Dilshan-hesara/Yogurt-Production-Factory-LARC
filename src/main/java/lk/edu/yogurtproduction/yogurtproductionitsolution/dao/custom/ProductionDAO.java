package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdtionDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductionDAO extends CrudDAO<ProdtionDto> {

    public ArrayList<String> getAllProdtIds() throws SQLException;

    public ProdtionDto findById(String cmbProdSelected) throws SQLException;
}
