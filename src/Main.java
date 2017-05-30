import java.sql.SQLException;

/**
 * Created by smele on 29.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        DBClass db = new DBClass();
        try {
            db.connect();
            db.createTable();
            db.clearTable();
            db.disconnect();





        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

