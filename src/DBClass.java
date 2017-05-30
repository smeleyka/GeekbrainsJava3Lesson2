import java.sql.*;

/**
 * Created by smele on 29.05.2017.
 */
public class DBClass {
    private Connection connection;
    private Statement stmt;
    private PreparedStatement ps;

    public DBClass() {
        try {
            connect();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:stuff.db");
        stmt = connection.createStatement();
    }

    public void createTable() throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet res = meta.getTables(null, null, "product",
                new String[] {"TABLE"});
        while (res.next()) {
            System.out.println(
                    "   "+res.getString("TABLE_CAT")
                            + ", "+res.getString("TABLE_SCHEM")
                            + ", "+res.getString("TABLE_NAME")
                            + ", "+res.getString("TABLE_TYPE")
                            + ", "+res.getString("REMARKS"));
        }

//        stmt.execute("CREATE TABLE product (\n" +
//                "id     INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//                "prodid INTEGER UNIQUE,\n" +
//                "title  STRING,\n" +
//                "cost   INTEGER" +
//                ");");
    }

    public void clearTable() throws SQLException {
        stmt.execute("DELETE FROM product");
    }

    public void disconnect() throws SQLException {
            stmt.close();
            connection.close();
    }
}
