package lk.edu.yogurtproduction.yogurtproductionitsolution.util;

public class UserName {
    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserName.username = username;
    }
































//
//    public static  boolean execute(String query, Object... params) throws SQLException {
//
//        try (Connection connection = DBConnection.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            int index = 1;
//            for (Object param : params) {
//                statement.setObject(index++, param);
//            }
//
//            int affectedRows = statement.executeUpdate();
//            return affectedRows > 0;
//
//        }
//    }
}
