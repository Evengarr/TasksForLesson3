import java.sql.*;

public class LoginController {
    public static void main(String[] args) throws Exception {
       Class.forName("org.sqlite.JDBC");
       Connection connection = DriverManager.getConnection("jdbc:sqlite:Lesson2/Server/chat.db");
       Statement statement = connection.createStatement();
       ResultSet result = statement.executeQuery("select * from Users");
        printBD(result);

        result.close();
       statement.close();
       connection.close();
    }

    private static void printBD(ResultSet result) throws SQLException {
        while (result.next()){
            System.out.print(result.getLong("id") + " ");
            System.out.print("Логин: ");
            System.out.print(result.getString("login") + " ");
            System.out.print("Пароль: ");
            System.out.print(result.getString("password") + " ");
            System.out.print("Ник: ");
            System.out.println(result.getString("nickname"));
        }
    }
}
