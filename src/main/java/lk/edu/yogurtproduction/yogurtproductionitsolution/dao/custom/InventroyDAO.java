package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.InventroyDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.PckingDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdMixDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Inventroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InventroyDAO extends CrudDAO<Inventroy> {

    public boolean saveredusPackedQty(PckingDto pckingDtos) throws SQLException ;

    public int AvQtyFromSelectProd_ID(String getSelectedProdId) throws SQLException;

    public boolean saveInvetory(ArrayList<InventroyDto> inventroyDTOS) throws SQLException ;

    public boolean redusqtyOnInventroyOnItems(ArrayList<ProdMixDto> prodMixDTOS) throws SQLException ;

    public ArrayList<String> getAllAVItems() throws SQLException ;
}
