package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CreteAccDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.UserDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.User;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {

    public  boolean createUser(UserDto user) throws SQLException ;

    public  boolean execute(String query, Object... params) throws SQLException ;

    public boolean updatePassword(String username, String newPassword) throws SQLException ;

    public boolean isValidUser(String username, String password) throws SQLException ;

    public boolean isValidUsername(String username) throws SQLException ;

    public boolean creatUser(CreteAccDto creteAccDto) throws SQLException ;

    public String GetUserMail(String username) throws SQLException ;

    public boolean UpdateUser(String usename, String newpass) throws SQLException ;

}
