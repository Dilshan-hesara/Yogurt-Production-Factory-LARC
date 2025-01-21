package lk.edu.yogurtproduction.yogurtproductionitsolution.model;

import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CreteAccDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.UserDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean createUser(UserDto user) throws SQLException {


        return SQLUtil.execute(

                "insert into user values (?, ?, ?)",
                user.getUsername(),
                user.getEmail(),
                user.getPassword()

        );

    }

    public static boolean execute(String query, Object... params) throws SQLException {

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            int index = 1;
            for (Object param : params) {
                statement.setObject(index++, param);
            }

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

        }
    }

    public boolean updatePassword(String username, String newPassword) throws SQLException {

        String query = "update user set password = ? where username = ?";
        boolean isUpdated = SQLUtil.execute(query, newPassword, username);

        if (isUpdated) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isValidUser(String username, String password) throws SQLException {
        String query = "select * from user where username = ?  and password = ?";

        ResultSet resultSet = SQLUtil.execute(query, username, password);

        return resultSet.next();
    }

    public boolean isValidUsername(String username) throws SQLException {
        String query = "select * from user where username = ?";

        ResultSet resultSet = SQLUtil.execute(query, username);

        return resultSet.next();
    }

    public boolean creatUser(CreteAccDto creteAccDto) throws SQLException {
        return SQLUtil.execute(

                "insert into user values (?, ?, ?)",

                creteAccDto.getUsername(),
                creteAccDto.getPassword(),
                creteAccDto.getEmail()

        );


    }
    public String GetUserMail(String username) throws SQLException {

        try {
            ResultSet resultSet = SQLUtil.execute("select email from user where username = ?", username);

            if (resultSet.next()) {
                return resultSet.getString("email");
            }
        } catch (SQLException e) {
        }

        return null;
    }


    public boolean UpdateUser(String usename, String newpass) throws SQLException {
        return SQLUtil.execute(
                "update user set password = ? where username = ?",
                newpass,
                usename
        );

   }
}

