package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.SuplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierDAO  extends CrudDAO<SuplierDto> {



    public ArrayList<String> getAllSupIds() throws SQLException;


}
