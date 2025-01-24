package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.InventroyBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.InventroyDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.InventroyDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Inventroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventroyBOImpl implements InventroyBO {

    InventroyDAO inventroyDAO = (InventroyDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.INVENTROY);

    @Override
    public ArrayList<InventroyDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<InventroyDto> inventroyDtos = new ArrayList<>();

        ArrayList<Inventroy> inventroyDtoArrayList = inventroyDAO.getAll();

        for (Inventroy inventroy : inventroyDtoArrayList) {
            inventroyDtos.add(new InventroyDto(new Inventroy(
                    inventroy.getId(),
                    inventroy.getItemType(),
                    inventroy.getItemDescription(),
                    inventroy.getQty(),
                    inventroy.getProdId()
            )));
        }

        return inventroyDtos;
    }
}
