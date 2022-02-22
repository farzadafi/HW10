import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "afshar");

        String createTable = "CREATE TABLE IF NOT EXISTS TEST(id serial PRIMARY KEY,numberFor integer)";
        PreparedStatement preparedStatement = connection.prepareStatement(createTable);
        preparedStatement.execute();


        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 500; i++) {
            final int finalI = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        String importToDB = "INSERT INTO TEST(numberFor) VALUES (?)";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(importToDB);
                        preparedStatement1.setInt(1, finalI);
                        preparedStatement1.executeUpdate();
                    } catch (SQLException exception) {
                        System.out.println(exception.getMessage());
                    }
                }
            });
        }
        executorService.shutdown();

    }
}
