import java.sql.*;

public class MainClass {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement ps;

    public static void main(String[] args) {
        try {
            connect();
            createTableEx();
            clearTableEx();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void rollbackEx() throws SQLException {
        connection.setAutoCommit(false);
        stmt.execute("INSERT INTO students (name, score) VALUES ('bob1', 10);");
        Savepoint sp1 = connection.setSavepoint();
        stmt.execute("INSERT INTO students (name, score) VALUES ('bob2', 20);");
        connection.rollback(sp1);
        stmt.execute("INSERT INTO students (name, score) VALUES ('bob3', 30);");
        connection.commit();
    }

    public static void batchOperationEx() throws SQLException {
        connection.setAutoCommit(false);
        ps = connection.prepareStatement("INSERT INTO students (name, score) VALUES (?, ?);");
        for (int i = 1; i <= 200; i++) {
            ps.setString(1, "bob" + i);
            ps.setInt(2, (i * 10) % 100 + 10);
            ps.addBatch();
        }
        ps.executeBatch();
        connection.commit();
    }

    public static void preparedStatementInsertEx() throws SQLException {
        connection.setAutoCommit(false);
        ps = connection.prepareStatement("INSERT INTO students (name, score) VALUES (?, ?);");
        for (int i = 1; i <= 2000; i++) {
            ps.setString(1, "bob" + i);
            ps.setInt(2, (i * 10) % 100 + 10);
            ps.executeUpdate();
        }
        connection.commit();
    }

    public static void prepareNewDataAndCommitEx() throws SQLException {
        connection.setAutoCommit(false);
        for (int i = 1; i <= 2000; i++) {
            stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('bob" + i + "', " + i * 10 + ");");
        }
        connection.commit();
    }


    public static void clearTableEx() throws SQLException {
        stmt.execute("DELETE FROM students");
    }

    public static void createTableEx() throws SQLException {
        stmt.execute("CREATE TABLE IF NOT EXISTS students (\n" +
                "    id    INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name  TEXT,\n" +
                "    score INTEGER\n" +
                ");");
    }

    public static void selectEx() throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM students WHERE score = 20;");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString("name") + " " + rs.getInt("score"));
        }
        rs.close();
    }

    public static void deleteEx() throws SQLException {
        stmt.executeUpdate("DELETE FROM students WHERE id = 8;");
    }

    public static void updateEx() throws SQLException {
        stmt.executeUpdate("UPDATE students SET score = 20 WHERE name = 'bob4';");
    }

    public static void insertEx() throws SQLException {
        stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('bob', 20);");
    }

    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
