package lk.edu.yogurtproduction.yogurtproductionitsolution.util;

import lk.edu.yogurtproduction.yogurtproductionitsolution.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserUtil {
    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserUtil.username = username;
    }


    public  boolean execute(String query, Object... params) throws SQLException {

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
}
