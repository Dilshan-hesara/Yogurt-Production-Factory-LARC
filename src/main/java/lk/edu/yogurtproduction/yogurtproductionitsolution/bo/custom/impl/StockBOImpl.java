package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.StockBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.StockDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.StockDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Stock;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockBOImpl implements StockBO {

    StockDAO stockDAO = (StockDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.STOCK);

    public ArrayList<StockDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<StockDto> stockDtos = new ArrayList<>();
        ArrayList<Stock> stockDtoArrayList = stockDAO.getAll();

        for (Stock stock : stockDtoArrayList) {
            stockDtos.add(new StockDto(new Stock(
                    stock.getStock_ID(),
                    stock.getPac_ID(),
                    stock.getProduct_Name(),
                    stock.getQty(),
                    stock.getManfac_date(),
                    stock.getExpire_date(),
                    stock.getPack_Type(),
                    stock.getUnit_Price()
            )));
        }
        return stockDtos;
    }

}
