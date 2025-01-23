package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.SuperBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CreteAccDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.UserDto;

import java.sql.SQLException;

public interface UserBO extends SuperBO {


    public  boolean createUser(UserDto user) throws SQLException;

    public  boolean execute(String query, Object... params) throws SQLException ;
    public boolean updatePassword(String username, String newPassword) throws SQLException ;
    public boolean isValidUser(String username, String password) throws SQLException ;

    public boolean isValidUsername(String username) throws SQLException ;

    public boolean creatUser(CreteAccDto creteAccDto) throws SQLException ;
    public String GetUserMail(String username) throws SQLException ;

    public boolean UpdateUser(String usename, String newpass) throws SQLException ;

}
