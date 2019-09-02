package org.opendatadaykalro.is.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseService {

    private static volatile DatabaseService dbServiceInstance;
    //Eager Singleton Initialization
    private DatabaseService() {
        //Prevent form the reflection api.
        if (dbServiceInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }
    public static DatabaseService getInstance() {
        //Double check locking pattern
        if (dbServiceInstance == null) { //Check for the first time

            synchronized (DatabaseService.class) {   //Check for the second time.
                //if there is no instance available... create new one
                if (dbServiceInstance == null) dbServiceInstance = new DatabaseService();
            }
        }
        return dbServiceInstance;
    }

    private Connection connection = null;
    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {

        System.out.println("Test : get connection.");

        if ((connection == null) || (connection.isClosed())) {
            connection = openConnection();
        }
        return connection;
    }

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    private Connection openConnection() throws ClassNotFoundException, SQLException {


        if (connection == null) {
            try {
                // Driver driver;
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Unable to load Postgres Driver.");
                throw e;
            }

            try {
                System.out.println("Open new Connection");
                /*
      DB connection URL. Should be overridden in subclasses.
      Default value is here to test the class alone
     */
                String url = "jdbc:postgresql://########/test_db?currentSchema=public";
                String user = "#######";
                String password = "########";
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to the PostgreSQL server successfully.");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw e;
            }
        }
        return connection;
    }


    /**
     * Execute a request with 1 String parameter
     *
     * @param sql    SQL Statement with 1 string parameter."
     * @param param1 SQL Parameter 1 for the request
     *
     * @throws SQLException Sql error
     * @throws ClassNotFoundException if Driver not found
     */
    private boolean execute(String sql, String param1) throws SQLException, ClassNotFoundException {

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, param1);
            return preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /**
     * Execute a request with 1 String parameter
     *
     * @param sql    SQL Statement with 1 string parameter.
     * @param param1 String Parameter 1 for the request
     * @param param2 String  Parameter 2 for the request
     *
     * @throws SQLException Sql error
     * @throws ClassNotFoundException if Driver not found
     */
    private boolean execute(String sql, String param1, String param2) throws SQLException, ClassNotFoundException {

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, param1);
            preparedStatement.setString(2, param2);
            return preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw e;
        }
    }



    public void closeConnection() throws SQLException {

        if ((connection == null) || (connection.isClosed())) {
            System.out.println("Connection already closed");
            return;
        }

        System.out.println("Close Database Connection");
        connection.close();
    }

    @Override
    protected void finalize() throws Throwable {
        closeConnection();
        super.finalize();
    }
}
