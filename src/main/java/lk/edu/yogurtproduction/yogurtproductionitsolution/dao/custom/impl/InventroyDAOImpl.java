package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.InventroyDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.InventroyDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.PckingDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdMixDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventroyDAOImpl implements InventroyDAO {



    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select In_ID from inventory order by In_ID desc limit 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(3);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("INV%03d", newIdIndex);
        }
        return "INV001";
    }

    public ArrayList<InventroyDto> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from inventory");

        ArrayList<InventroyDto> inventroyDTOS = new ArrayList<>();

        while (rst.next()) {
            InventroyDto inventroyDTO = new InventroyDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            inventroyDTOS.add(inventroyDTO);
        }
        return inventroyDTOS;


    }

    public boolean saveredusPackedQty(PckingDto pckingDtos) throws SQLException {

        return   SQLUtil.execute(
                "update inventory set Qty = Qty - ? where Prod_ID = ?",
                pckingDtos.getRedusQty(),
                pckingDtos.getProd_ID()

        );

    }

    public int AvQtyFromSelectProd_ID(String getSelectedProdId) throws SQLException {

        ResultSet rst = SQLUtil.execute(
                "select Qty from inventory where Prod_ID = ?",
                getSelectedProdId
        );

        if (rst.next()) {

            return rst.getInt("Qty");
        } else {
            return 0;
        }
    }


    public boolean saveInvetory(ArrayList<InventroyDto> inventroyDTOS) throws SQLException {
        for (InventroyDto inventroyDTO : inventroyDTOS) {
            boolean isSaved = savedInventory(inventroyDTO);

            if (!isSaved) {
                return false;
            }
        }

        return true;
    }

    private boolean savedInventory(InventroyDto inventroyDTO) throws SQLException {

        return SQLUtil.execute(
                "insert into inventory  values (?,?,?,?,?)",
                inventroyDTO.getId(),
                inventroyDTO.getItemType(),
                inventroyDTO.getItemDescription(),
                inventroyDTO.getQty(),
                inventroyDTO.getProdId()
        );
    }



    public boolean redusqtyOnInventroyOnItems(ArrayList<ProdMixDto> prodMixDTOS) throws SQLException {

        for (ProdMixDto prodMixDTO : prodMixDTOS) {
            boolean isSaved = savedInventoryOnItemRedu(prodMixDTO);

            if (!isSaved) {
                return false;
            }
        }

        return true;

    }

    private boolean savedInventoryOnItemRedu(ProdMixDto prodMixDTO) throws SQLException {

        boolean milkUpdated = SQLUtil.execute("update inventory i join (select In_ID from inventory where Item_Description = 'Milk' and Qty > 0 limit 1) subquery on i.In_ID = subquery.In_ID set i.Qty = i.Qty - ?", prodMixDTO.getMilk());
        boolean gelatinUpdated = SQLUtil.execute("update inventory i join (select In_ID from inventory where Item_Description = 'Gelat' and Qty > 0 limit 1) subquery on i.In_ID = subquery.In_ID set i.Qty = i.Qty - ?", prodMixDTO.getJeliy());
        boolean sugarUpdated = SQLUtil.execute("update inventory i join (select In_ID from inventory where Item_Description = 'Sugar' and Qty > 0 limit 1) subquery on i.In_ID = subquery.In_ID set i.Qty = i.Qty - ?", prodMixDTO.getSuguer());

        if (milkUpdated && gelatinUpdated && sugarUpdated) {
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<String> getAllAVItems() throws SQLException {
        ArrayList<String> availableItems = new ArrayList<>();

        ResultSet rst1 = SQLUtil.execute("select sum(Qty) as Total from inventory where Item_Description = 'Gelat' and Qty > 0;");
        if (rst1.next()) {
            availableItems.add(rst1.getString("Total") != null ? rst1.getString("Total") : "0");
        }

        ResultSet rst2 = SQLUtil.execute("select sum(Qty) as Total from inventory where Item_Description = 'Milk' and Qty > 0;");
        if (rst2.next()) {
            availableItems.add(rst2.getString("Total") != null ? rst2.getString("Total") : "0");
        }

        ResultSet rst3 = SQLUtil.execute("select sum(Qty) as Total from inventory where Item_Description = 'Sugar' and Qty > 0;");
        if (rst3.next()) {
            availableItems.add(rst3.getString("Total") != null ? rst3.getString("Total") : "0");
        }

        return availableItems;
    }




    @Override
    public boolean save(InventroyDto dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(InventroyDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String empId) throws SQLException {
        return false;
    }

    @Override
    public InventroyDto findByID(String cmbEmpSelected) throws SQLException {
        return null;
    }
}
