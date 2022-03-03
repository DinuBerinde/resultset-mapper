package helper;

import java.sql.*;

public class DBHelper implements AutoCloseable {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem:testdb";
    private static final String USER = "sa";
    private static final String PASS = "password";

    private Connection conn;


    public DBHelper connectToDB() throws ClassNotFoundException, SQLException {
        System.out.println("Connecting to database...");
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        return this;
    }

    public DBHelper createTables() throws SQLException {
        System.out.println("Creating tables...");

        try (Statement stmt = conn.createStatement()) {
            String userTable = "CREATE TABLE USERS" +
                    "(ID INTEGER not NULL, " +
                    " NAME VARCHAR(255), " +
                    " SURNAME VARCHAR(255), " +
                    " BIRTHDATE DATE, " +
                    " BIRTHDATE2 DATE, " +
                    " MALE BOOLEAN default false, " +
                    " PRIMARY KEY (ID));";

            String addressTable = "CREATE TABLE ADDRESS" +
                    "(ID INTEGER not NULL, " +
                    " NATION VARCHAR(255), " +
                    " CITY VARCHAR(255), " +
                    " STREET VARCHAR(255), " +
                    " STREET_NUMBER INTEGER, " +
                    " USER_ID INTEGER, " +
                    " PRIMARY KEY (ID));";

            stmt.execute(userTable);
            stmt.execute(addressTable);

            System.out.println("Tables created...");
        }

        return this;
    }


    public DBHelper insertData() throws SQLException  {
        System.out.println("Inserting data...");

        try (Statement stmt = conn.createStatement()) {
            String user1 = "INSERT INTO USERS VALUES(1, 'John', 'Doe', {ts '2000-09-17 18:47:52.69'}, {ts '2000-09-17 18:47:52.69'}, true);";
            String user2 = "INSERT INTO USERS VALUES(2, 'Mike', 'Donald', {ts '1993-09-27 18:47:52.69'}, {ts '1993-09-27 18:47:52.69'}, true);";
            String user3 = "INSERT INTO USERS VALUES(3, 'Jake', 'Johnson', {ts '1990-02-07 18:47:52.69'}, {ts '1990-02-07 18:47:52.69'}, true);";
            String user4 = "INSERT INTO USERS VALUES(4, 'Alice', 'Williams', {ts '2000-10-17 18:47:52.69'}, {ts '2000-10-17 18:47:52.69'}, false);";
            String user5 = "INSERT INTO USERS VALUES(5, 'Doris', 'Smith', {ts '1999-09-09 18:47:52.69'}, {ts '1999-09-09 18:47:52.69'}, false);";

            String address1 = "INSERT INTO ADDRESS VALUES(1, 'SUA', 'Chicago', 'Bakery', 1, 1);";
            String address2 = "INSERT INTO ADDRESS VALUES(2, 'SUA', 'New York', 'W 14th', 10, 2);";
            String address3 = "INSERT INTO ADDRESS VALUES(3, 'SUA', 'Los Angeles', 'Wilshire', 12, 3);";
            String address4 = "INSERT INTO ADDRESS VALUES(4, 'IRELAND', 'Dublin', 'New Bridge', 21, 4);";
            String address5 = "INSERT INTO ADDRESS VALUES(5, 'ENGLAND', 'London', 'Rodney', 11, 5);";

            stmt.execute(user1);
            stmt.execute(user2);
            stmt.execute(user3);
            stmt.execute(user4);
            stmt.execute(user5);

            stmt.execute(address1);
            stmt.execute(address2);
            stmt.execute(address3);
            stmt.execute(address4);
            stmt.execute(address5);

            System.out.println("Data inserted...");
        }

        return this;
    }

    public Connection getConnection() {
      return conn;
    }

    @Override
    public void close() throws Exception {
        if (this.conn != null) {
            this.conn.close();
        }
    }
}
