package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Connections {

    public Connection createSqlServerConnection(Properties props) {

        try {

            //DriverManager.setLogWriter(new PrintWriter((System.err)));
            return DriverManager.getConnection("jdbc:jtds:sqlserver://" + props.getProperty("host") + ":"
                    + Integer.parseInt(props.getProperty("port")),
                    props.getProperty("username"),
                    props.getProperty("password"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Connection createPostgreConnection(Properties props) {

        try {

            //DriverManager.setLogWriter(new PrintWriter((System.err)));

            return DriverManager.getConnection("jdbc:postgresql://" + props.getProperty("host") + ":"
                            + props.getProperty("port") + "/" + props.getProperty("db"),
                    props.getProperty("username"),
                    props.getProperty("password"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
