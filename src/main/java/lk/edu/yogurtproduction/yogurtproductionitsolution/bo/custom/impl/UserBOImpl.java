package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.UserBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.UserDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CreteAccDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.UserDto;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {

    UserDAO userDAO= (UserDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.USER);


    @Override
    public boolean createUser(UserDto user) throws SQLException {
        return userDAO.createUser(user);
    }

//    @Override
//    public boolean execute(String query, Object... params) throws SQLException {
//        return userDAO.execute(query, params);
//    }

    @Override
    public boolean updatePassword(String username, String newPassword) throws SQLException {
        return userDAO.updatePassword(username, newPassword);
    }

    @Override
    public boolean isValidUser(String username, String password) throws SQLException {
        return userDAO.isValidUser(username, password);
    }

    @Override
    public boolean isValidUsername(String username) throws SQLException {
        return userDAO.isValidUsername(username);
    }

    @Override
    public boolean creatUser(CreteAccDto creteAccDto) throws SQLException {
        return userDAO.creatUser(creteAccDto);
    }

    @Override
    public String GetUserMail(String username) throws SQLException {
        return userDAO.GetUserMail(username);
    }

    @Override
    public boolean UpdateUser(String usename, String newpass) throws SQLException {

        return userDAO.UpdateUser(usename, newpass);
    }
}
