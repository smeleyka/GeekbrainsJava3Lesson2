import java.sql.*;
import java.util.ArrayList;
import java.util.IdentityHashMap;

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
                new String[]{"TABLE"});
        while (res.next()) {
            System.out.println(
                    "   " + res.getString("TABLE_CAT")
                            + ", " + res.getString("TABLE_SCHEM")
                            + ", " + res.getString("TABLE_NAME")
                            + ", " + res.getString("TABLE_TYPE")
                            + ", " + res.getString("REMARKS"));
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

    public void fillinTables() throws SQLException {
        connection.setAutoCommit(false);
        ps = connection.prepareStatement("INSERT INTO product (prodid, title,cost) VALUES (?, ?, ?);");
        for (int i = 1; i <= 2000; i++) {
            ps.setInt(1, i);
            ps.setString(2, "товар" + i);
            ps.setInt(3, (i * 10) % 400 + 10);
            ps.addBatch();
        }
        ps.executeBatch();
        connection.commit();

    }

    public void fillinTablesNotOptimized() throws SQLException {

        for (int i = 1; i <= 200; i++) {
            stmt.execute("INSERT INTO product (prodid, title,cost) VALUES (" + i + ", 'товар" + i + "', " + ((i * 10) % 100 + 10) + ");");
        }
    }

    public int checkCost(String s) throws SQLException {
        int out = 0;
        ResultSet rs = stmt.executeQuery("SELECT * FROM product WHERE title = '" + s + "' ;");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getInt("prodid") + " " + rs.getString("title") + " " + rs.getInt("cost"));
            out = rs.getInt("cost");
        }
        rs.close();
        return out;
    }
}
