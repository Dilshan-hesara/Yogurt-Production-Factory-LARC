package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.EmployeeDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Employee;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public String getNextId() throws SQLException {

//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "select Emp_ID from employee order by Emp_ID desc LIMIT 1";
//        PreparedStatement pst = connection.prepareStatement(sql);
//        ResultSet rst = pst.executeQuery();

        ResultSet rst =SQLUtil.execute("select Emp_ID from employee order by Emp_ID desc LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;

            return String.format("EM%03d", newIdIndex);
        }

        return "EM001";


    }


    public boolean save(Employee entity) throws SQLException {

        return   SQLUtil.execute("insert into employee values (?,?,?,?,?)",
                entity.getEmpId(),entity.getEmpName(),entity.getEmpNic(),entity.getEmpEmail(),entity.getEmpPhone());


    }


    public boolean update(Employee employeeDto) throws SQLException, ClassNotFoundException{

        PreparedStatement statement;

        return SQLUtil.execute("update employee set Emp_Name = ?, Emp_Nic = ?, Emp_Email = ?, Emp_Phone = ? where Emp_ID = ?"
                ,employeeDto.getEmpName(),employeeDto.getEmpNic(),employeeDto.getEmpEmail(),employeeDto.getEmpPhone(),employeeDto.getEmpId());

    }

    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {


        ResultSet rst =SQLUtil.execute("select * from employee");
        ArrayList<Employee> employeeDTOArrayList = new ArrayList<>();

        while (rst.next()) {
            employeeDTOArrayList.add(new Employee(  rst.getString("Emp_ID"),
                    rst.getString("Emp_Name"),
                    rst.getString("Emp_Nic"),
                    rst.getString("Emp_Email"),
                    rst.getString("Emp_Phone")));
        }


        return employeeDTOArrayList;
    }



    public boolean delete(String empId) throws SQLException {
        return SQLUtil.execute("delete from employee where Emp_ID=?", empId);

    }

    public ArrayList<String> getAllEmpIds() throws SQLException {

        ResultSet rst = SQLUtil.execute("select Emp_ID from Employee");

        ArrayList<String> EmpIds = new ArrayList<>();

        while (rst.next()) {
            EmpIds.add(rst.getString(1));
        }

        return EmpIds;
    }

    public Employee findByID(String cmbEmpSelected) throws SQLException {


        ResultSet rst = SQLUtil.execute("select * from Employee where Emp_ID=?", cmbEmpSelected);

        if (rst.next()) {
            return new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}
