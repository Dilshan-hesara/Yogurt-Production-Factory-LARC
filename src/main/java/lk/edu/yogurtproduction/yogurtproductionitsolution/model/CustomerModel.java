package lk.edu.yogurtproduction.yogurtproductionitsolution.model;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CustomerDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select cust_id from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }

    public CustomerDto findById(String selectedCustomerId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer where cust_id=?", selectedCustomerId);

        if (rst.next()) {
            return new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }



    public String getNexCustomerId() throws SQLException {

        ResultSet rst = CrudUtil.execute("select cust_id from customer order by cust_id desc limit 1");
        if (rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i+1;
            return String.format("C%03d",newIdIndex);
        }
        return  "C001";
    }



    public boolean saveCustomer(CustomerDto customerDTO) throws SQLException {
        boolean isSaved =  CrudUtil.execute(
                "insert into customer values (?,?,?,?,?)",
                customerDTO.getCustomerId(),
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getEmail(),
                customerDTO.getPhone()
        );
        return isSaved;
    }


    public boolean deleteCustomer(String customerId) throws SQLException {
        return CrudUtil.execute("delete from customer where cust_id=?", customerId);

    }

    public ArrayList<CustomerDto> getAllCustomers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<CustomerDto> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDto customerDTO = new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public boolean updateCustomer(CustomerDto customerDTO) throws SQLException {

        return CrudUtil.execute(
                "update customer set name=?, nic=?, email=?, phone=? where cust_id=?",
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getEmail(),
                customerDTO.getPhone(),
                customerDTO.getCustomerId()
        );
    }
}
