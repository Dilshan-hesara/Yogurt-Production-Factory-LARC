package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.SupplierDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierDAO  extends CrudDAO<Supplier> {


    public ArrayList<String> getAllSupIds() throws SQLException;


}
