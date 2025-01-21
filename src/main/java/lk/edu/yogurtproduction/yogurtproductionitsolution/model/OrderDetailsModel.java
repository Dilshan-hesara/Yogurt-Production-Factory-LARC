package lk.edu.yogurtproduction.yogurtproductionitsolution.model;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.OrderDetailsDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;


public class OrderDetailsModel {

    StockModel stockModel = new StockModel();
    InventroyModel inventoryModel = new InventroyModel();

    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDto> orderDetailsDTOS) throws SQLException {

        for (OrderDetailsDto orderDetailsDTO : orderDetailsDTOS) {
            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                return false;
            }


            boolean isStockUpdated = stockModel.onOderRedQty(orderDetailsDTO);
            if (!isStockUpdated) {
                return false;
            }

        }
        return true;
    }

    private boolean saveOrderDetail(OrderDetailsDto orderDetailsDTO) throws SQLException {

        return CrudUtil.execute(
                "insert into orderdetails values (?,?,?,?)",
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getItemId(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice()
        );
    }
}
