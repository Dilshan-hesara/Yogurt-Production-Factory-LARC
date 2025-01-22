package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.SuperBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdMixDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ResipesBO extends SuperBO {

    public boolean save(ProdMixDto prodMixDto) throws SQLException ;

    public ArrayList<ProdMixDto> getAll() throws SQLException, ClassNotFoundException;

    public boolean updateRe(String prodName, int milk, int suguer, int jeliy) throws SQLException ;

    public boolean delete(String prodName) throws SQLException ;
}
