import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.*;

public class SimpleJdbcFX extends Application {

    private final String DB_URL = "jdbc:mysql://localhost/javabook";
    private final String USER = "scott";
    private final String PASS = "tiger";

    @Override
    public void start(Stage stage) {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        BorderPane pane = new BorderPane(textArea);
        Scene scene = new Scene(pane, 500, 300);

        stage.setTitle("Simple JDBC JavaFX");
        stage.setScene(scene);
        stage.show();

        connectToDatabase(textArea);
    }

    private void connectToDatabase(TextArea textArea) {
        String sql = "SELECT firstName, mi, lastName FROM Student WHERE lastName = 'Smith'";

        try (
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
        ) {
            textArea.appendText("Database connected\n\n");

            while (resultSet.next()) {
                textArea.appendText(
                        resultSet.getString(1) + " " +
                        resultSet.getString(2) + " " +
                        resultSet.getString(3) + "\n"
                );
            }

        } catch (SQLException ex) {
            textArea.appendText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
