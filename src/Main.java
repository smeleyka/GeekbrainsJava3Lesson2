import java.sql.*;

/**
 * Created by smele on 29.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        DBClass db = new DBClass();
        try {
            db.connect();
            //db.createTable();
            db.clearTable();
           db.fillinTables();
           db.checkCost("товар490");
            //db.fillinTablesNotOptimized();
            db.disconnect();

//            Connection connection;
//            Statement stmt;
//            PreparedStatement ps;
//            Class.forName("org.sqlite.JDBC");
//            connection = DriverManager.getConnection("jdbc:sqlite:stuff.db");
//            stmt = connection.createStatement();
//
//
//            long t = System.currentTimeMillis();
//            connection.setAutoCommit(false);
//            ps = connection.prepareStatement("INSERT INTO product (prodid, title,cost) VALUES (?, ?, ?);");
//            for (int i = 1; i <= 2000; i++) {
//                ps.setInt(1, i);
//                ps.setString(2, "товар" + i);
//                ps.setInt(3, (i * 10) % 100 + 10);
//                ps.addBatch();
//            }
//            ps.executeBatch();
//            connection.commit();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

