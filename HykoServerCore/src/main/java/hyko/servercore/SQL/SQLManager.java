package hyko.servercore.SQL;

import hyko.servercore.ServerCore;
import sun.tools.jconsole.Tab;

import java.sql.*;
import java.util.UUID;

public class SQLManager {

    private String databaseHost;
    private String databaseName;
    private String databaseUserName;
    private String databasePassword;
    private Connection c;
    private final ServerCore HykoPluginCore;


    /**
     * This constructor manages SQL databases.
     *
     * @param databaseHost
     * @param databaseName
     * @param databaseUserName
     * @param databasePassword
     */
    public SQLManager(ServerCore plugin, String databaseHost, String databaseName, String databaseUserName, String databasePassword) {
        this.databaseHost = databaseHost;
        this.databaseName = databaseName;
        this.databaseUserName = databaseUserName;
        this.databasePassword = databasePassword;
        HykoPluginCore = plugin;
    }


    public String getDatabaseHost() {
        return databaseHost;
    }

    public void setDatabaseHost(String databaseHost) {
        this.databaseHost = databaseHost;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseUserName() {
        return databaseUserName;
    }

    public void setDatabaseUserName(String databaseUserName) {
        this.databaseUserName = databaseUserName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public Connection getConnection() {
        HykoPluginCore.getLogger().info("[HykoCore] Attempting to connect to MySQL.");
        try {
            c = DriverManager.getConnection(
                    "jdbc:mysql://" + databaseHost + ":3306/" +
                            databaseName +
                            "?autoReconnect=true",
                    databaseUserName,
                    databasePassword);
        } catch (SQLException throwables) {
            HykoPluginCore.getLogger().info("[HykoCore] Failed to connect to MySQL!");
            throwables.printStackTrace();
        }
        if(c!=null) {
            HykoPluginCore.getLogger().info("[HykoCore] Connected to MySQL!");
        }
        return c;

    }

    public void createDatabase(String name, TableType type) {
        if(c==null) {
            return;
        }
        if (type == TableType.PLAYER_DATABASE) {
            try {
                PreparedStatement create = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + name + "(PLAYER_NAME VARCHAR(100), UUID VARCHAR(100))");
                create.executeUpdate();
            } catch (SQLException throwables) {
                getConnection();
                throwables.printStackTrace();
                HykoPluginCore.getLogger().info("Could not create database. Error in SQL Syntax?");
                return;
            }
            HykoPluginCore.getLogger().info("Database found and/or created.?");
        }
        HykoPluginCore.getLogger().info("Could not create table of type " + type.toString());
    }

    public ResultSet getResult(String qry) {
        if(c==null) {
            return null;
        }
            ResultSet rs = null;
            try {
                Statement st = c.createStatement();
                rs = st.executeQuery(qry);
            } catch (SQLException e) {
                getConnection();
                e.printStackTrace();
            }
            return rs;
    }



    /**
     * Updates and gets the latest data from the database.
     * @param qry
     */
    public void update(String qry) {
        if(c==null) {
            return;
        }
        try {
            Statement st = c.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException e) {
            getConnection();
            e.printStackTrace();
        }

    }

    public void close() throws SQLException {
        c.close();
        HykoPluginCore.getLogger().info("[HykoCore] Disconnected from MySQL.");
    }

}
