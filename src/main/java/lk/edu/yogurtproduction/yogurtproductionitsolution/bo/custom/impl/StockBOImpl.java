package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.StockBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.StockDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.StockDto;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockBOImpl implements StockBO {

    StockDAO stockDAO = (StockDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.STOCK);

    public ArrayList<StockDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<StockDto> stockDtos = new ArrayList<>();
        ArrayList<StockDto> stockDtoArrayList = stockDAO.getAll();

        for (StockDto stockDto : stockDtoArrayList) {
            stockDtos.add(stockDto);
        }
        return stockDtos;
    }

}
