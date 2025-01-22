package lk.edu.yogurtproduction.yogurtproductionitsolution.bo;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.EmployeeDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.EmployeeDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

   // EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.EMPLOYEE);

    @Override
    public String getNextId() throws SQLException {


      return employeeDAO.getNextId();
    }


    public boolean save(EmployeeDto employeeDto) throws SQLException {
        employeeDAO.save(new EmployeeDto(   employeeDto.getEmpId(),employeeDto.getEmpName(),employeeDto.getEmpNic(),employeeDto.getEmpEmail(),employeeDto.getEmpPhone()));

        return true;

    }


    public boolean update(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException{

        employeeDAO.update(employeeDto);
        return false;
    }

    public ArrayList<EmployeeDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        ArrayList<EmployeeDto> employes = employeeDAO.getAll();
        for (EmployeeDto employeeDto : employes) {
            employeeDtos.add(employeeDto);
        }
        return employeeDtos;

    }

//    ArrayList<CustomerDTO> customerDTOs = new ArrayList<>();
//    ArrayList<Customer> customers = customerDAO.getAll();
//        for (Customer customer : customers) {
//        customerDTOs.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress()));
//    }
//        return customerDTOs;

    public boolean delete(String empId) throws SQLException {

        return employeeDAO.delete(empId);


    }

}
