package lk.edu.yogurtproduction.yogurtproductionitsolution.model;

import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.TM.EmployeeTM;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {

    public String getNextCustomerId() throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select Emp_ID from employee order by Emp_ID desc LIMIT 1";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;

            return String.format("EM%03d", newIdIndex);
        }

        return "EM001";


    }

    public boolean saveEmpoyee(EmployeeDto employeeDto) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into employee values (?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setObject(1,employeeDto.getEmpId());
        pst.setObject(2,employeeDto.getEmpName());
        pst.setObject(3,employeeDto.getEmpNic());
        pst.setObject(4,employeeDto.getEmpEmail());
        pst.setObject(5,employeeDto.getEmpPhone());
        int result = pst.executeUpdate();
        boolean isSaved = result>0;
        return isSaved;
    }


    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select * from employee";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();

        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();

        while (rst.next()) {
            EmployeeDto employeeDto = new EmployeeDto(
                    rst.getString("Emp_ID"),
                    rst.getString("Emp_Name"),
                    rst.getString("Emp_Nic"),
                    rst.getString("Emp_Email"),
                    rst.getString("Emp_Phone")
            );
            employeeDtos.add(employeeDto);
        }

        return employeeDtos;
    }



    public boolean updateCustomer(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException{
        String sql = "update employee set Emp_Name = ?, Emp_Nic = ?, Emp_Email = ?, Emp_Phone = ? where Emp_ID = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql); {

            statement.setString(1, employeeDto.getEmpName());
            statement.setString(2, employeeDto.getEmpNic());
            statement.setString(3, employeeDto.getEmpEmail());
            statement.setString(4, employeeDto.getEmpPhone());
            statement.setString(5, employeeDto.getEmpId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;


        }

    }





    public boolean deleteCustomer(String empId) throws SQLException {
        return CrudUtil.execute("delete from employee where Emp_ID=?", empId);

    }

    public ArrayList<String> getAllEmpIds() throws SQLException {

        ResultSet rst = CrudUtil.execute("select Emp_ID from Employee");

        ArrayList<String> EmpIds = new ArrayList<>();

        while (rst.next()) {
            EmpIds.add(rst.getString(1));
        }

        return EmpIds;
    }

    public EmployeeDto findByID(String cmbEmpSelected) throws SQLException {


        ResultSet rst = CrudUtil.execute("select * from Employee where Emp_ID=?", cmbEmpSelected);

        if (rst.next()) {
            return new EmployeeDto(
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
