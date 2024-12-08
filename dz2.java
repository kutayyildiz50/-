import java.sql.*;
import java.util.Scanner;

public class dz2 {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Kutay";

    public static void main(String[] args) {
        String sqlInsertUser = "INSERT INTO driver (isim, soyisim, yas) VALUES (?, ?, ?)";
        String sqlSelect = "SELECT * FROM driver WHERE yas > 21";

        try (
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);
                Statement statement = connection.createStatement();
        ) {
            // Kullanıcıdan veri almak
            Scanner scanner = new Scanner(System.in);
            int affectedRows = 0;

            // Şartlı veri seçimi
            ResultSet result = statement.executeQuery(sqlSelect);
            while (result.next()) {
                System.out.println(result.getString("isim") + " " +
                        result.getString("soyisim") + " " +
                        result.getInt("yas"));
            }

            // Kullanıcıdan veri alıp tabloya eklemek
            for (int i = 0; i < 6; i++) {
                System.out.print("İsim: ");
                String firstName = scanner.next();

                System.out.print("Soyisim: ");
                String secondName = scanner.next();

                System.out.print("Yaş: ");
                int age = scanner.nextInt();

                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, secondName);
                preparedStatement.setInt(3, age);

                affectedRows += preparedStatement.executeUpdate();
            }

            System.out.println("Etkilenen Satır Sayısı: " + affectedRows);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
