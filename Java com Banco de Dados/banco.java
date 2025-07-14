
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class banco {

    public Connection getCon() {
        Connection con = null;
        try {
            String database = "jdbc:ucanaccess://banco.accdb";
            con = DriverManager.getConnection(database);
        } catch (SQLException e) {
            Logger.getLogger(banco.class.getName()).log(Level.SEVERE,
                    null, e);
        }
        return con;
    }
}
