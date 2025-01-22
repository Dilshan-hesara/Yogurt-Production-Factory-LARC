package lk.edu.yogurtproduction.yogurtproductionitsolution.dao;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO{

     ArrayList<T> getAll() throws SQLException, ClassNotFoundException ;

     String getNextId() throws SQLException;

     boolean save( T dto) throws SQLException ;

     boolean update(T dto) throws SQLException, ClassNotFoundException;

     boolean delete(String empId) throws SQLException ;

     T findByID(String cmbEmpSelected) throws SQLException ;
}
