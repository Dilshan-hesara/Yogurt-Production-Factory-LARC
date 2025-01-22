package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.EmployeeBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.EmployeeDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Employee;

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
       // employeeDAO.save(new EmployeeDto(   employeeDto.getEmpId(),employeeDto.getEmpName(),employeeDto.getEmpNic(),employeeDto.getEmpEmail(),employeeDto.getEmpPhone()));

        employeeDAO.save(new Employee(employeeDto.getEmpId(),employeeDto.getEmpName(),employeeDto.getEmpNic(),employeeDto.getEmpEmail(),employeeDto.getEmpPhone()));
        return true;

    }


    public boolean update(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException{

       return employeeDAO.update(new Employee(employeeDto.getEmpId(),employeeDto.getEmpName(),employeeDto.getEmpNic(),employeeDto.getEmpEmail(),employeeDto.getEmpPhone()));
    }

    public ArrayList<EmployeeDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<EmployeeDto> employeeDTOs = new ArrayList<>();
        ArrayList<Employee> employes = employeeDAO.getAll();
        for (Employee employeeDto : employes) {
            employeeDTOs.add(new EmployeeDto(new Employee(employeeDto.getEmpId(),employeeDto.getEmpName(),employeeDto.getEmpNic(),employeeDto.getEmpEmail(),employeeDto.getEmpPhone())));
        }
        return employeeDTOs;


    }



    public boolean delete(String empId) throws SQLException {

        return employeeDAO.delete(empId);


    }

}
