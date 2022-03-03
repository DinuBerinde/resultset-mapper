import com.dinuberinde.ResultSetMapper;
import com.dinuberinde.ResultSetMapperException;
import dto.Address;
import dto.User;
import helper.DBHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultSetMapperTest {
    private static DBHelper dbHelper;

    @BeforeAll
    static void initDB() throws SQLException, ClassNotFoundException {
        dbHelper = new DBHelper()
                        .connectToDB()
                        .createTables()
                        .insertData();
    }

    @AfterAll
    static void closeDB() throws Exception {
        dbHelper.close();
    }

    @Test
    void itShouldMapTheSingleUser() throws SQLException {
        User user;

        try (Statement stmt = dbHelper.getConnection().createStatement()) {
            System.out.println("Query for user...");

            String sql = "SELECT * FROM USERS WHERE ID = 1";
            ResultSet resultSet = stmt.executeQuery(sql);

            user = ResultSetMapper.toObject(resultSet, User.class);
        }

        assertNotNull(user);
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals(1L, user.getId());
        assertEquals(true, user.isMale());
        assertEquals("1.92", user.getHeight());
        assertEquals("17/09/2000", user.getBirthDateString());
        assertEquals(LocalDate.of(2000, 9, 17).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), user.getBirthDate().toString());
    }

    @Test
    void isShouldMapAListOfUsers() throws SQLException {
        List<User> users;

        try (Statement stmt = dbHelper.getConnection().createStatement()) {
            System.out.println("Query for users...");

            String sql = "SELECT * FROM USERS";
            ResultSet resultSet = stmt.executeQuery(sql);

            users = ResultSetMapper.toList(resultSet, User.class);
        }

        assertNotNull(users);
        assertEquals(5, users.size());

        users.forEach(user -> {
            assertNotNull(user.getBirthDate());
            assertNotNull(user.getName());
            assertNotNull(user.getSurname());
            assertNotNull(user.getId());
            assertNotNull(user.getBirthDateString());
            assertNotNull(user.getHeight());
        });

        User mike = users.get(1);
        assertEquals("Mike", mike.getName());
        assertEquals("Donald", mike.getSurname());
        assertEquals(2L, mike.getId());
        assertEquals(true, mike.isMale());
        assertEquals("1.80", mike.getHeight());
        assertEquals("27/09/1993", mike.getBirthDateString());
        assertEquals(LocalDate.of(1993, 9, 27).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), mike.getBirthDate().toString());

        User doris = users.get(4);
        assertEquals("Doris", doris.getName());
        assertEquals("Smith", doris.getSurname());
        assertEquals(5L, doris.getId());
        assertEquals(false, doris.isMale());
        assertEquals("1.77", doris.getHeight());
        assertEquals("09/09/1999", doris.getBirthDateString());
        assertEquals(LocalDate.of(1999, 9, 9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), doris.getBirthDate().toString());
    }


    @Test
    void isShouldMapAListOfUsersByUsingApply() throws SQLException {
        List<User> users = new ArrayList<>();

        try (Statement stmt = dbHelper.getConnection().createStatement()) {
            System.out.println("Query for users...");

            String sql = "SELECT * FROM USERS";
            ResultSet resultSet = stmt.executeQuery(sql);

            while(resultSet.next()) {
                users.add(ResultSetMapper.apply(resultSet, User.class));
            }
        }

        assertNotNull(users);
        assertEquals(5, users.size());

        users.forEach(user -> {
            assertNotNull(user.getBirthDate());
            assertNotNull(user.getName());
            assertNotNull(user.getSurname());
            assertNotNull(user.getId());
            assertNotNull(user.getBirthDateString());
            assertNotNull(user.getHeight());
        });

        User mike = users.get(1);
        assertEquals("Mike", mike.getName());
        assertEquals("Donald", mike.getSurname());
        assertEquals(2L, mike.getId());
        assertEquals(true, mike.isMale());
        assertEquals("1.80", mike.getHeight());
        assertEquals("27/09/1993", mike.getBirthDateString());
        assertEquals(LocalDate.of(1993, 9, 27).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), mike.getBirthDate().toString());

        User doris = users.get(4);
        assertEquals("Doris", doris.getName());
        assertEquals("Smith", doris.getSurname());
        assertEquals(5L, doris.getId());
        assertEquals(false, doris.isMale());
        assertEquals("1.77", doris.getHeight());
        assertEquals("09/09/1999", doris.getBirthDateString());
        assertEquals(LocalDate.of(1999, 9, 9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), doris.getBirthDate().toString());
    }

    @Test
    void itShouldMapTheSingleAddress() throws SQLException {
        Address address;

        try (Statement stmt = dbHelper.getConnection().createStatement()) {
            System.out.println("Query for address...");

            String sql = "SELECT * FROM ADDRESS WHERE ID = 4";
            ResultSet resultSet = stmt.executeQuery(sql);

            address = ResultSetMapper.toObject(resultSet, Address.class);
        }

        assertNotNull(address);
        assertEquals("IRELAND", address.getNation());
        assertEquals("Dublin", address.getCity());
        assertEquals(4L, address.getID());
        assertEquals("New Bridge", address.getStreet());
        assertEquals(21, address.getNumber());
        assertEquals(4L, address.getUserID());
    }

    @Test
    void isShouldMapAListOfAddresses() throws SQLException {
        List<Address> addresses;

        try (Statement stmt = dbHelper.getConnection().createStatement()) {
            System.out.println("Query for addresses...");

            String sql = "SELECT * FROM ADDRESS";
            ResultSet resultSet = stmt.executeQuery(sql);

            addresses = ResultSetMapper.toList(resultSet, Address.class);
        }

        assertNotNull(addresses);
        assertEquals(5, addresses.size());

        addresses.forEach(address -> {
            assertNotNull(address.getUserID());
            assertNotNull(address.getID());
            assertNotNull(address.getNation());
            assertNotNull(address.getNation());
            assertNotNull(address.getCity());
            assertNotNull(address.getStreet());
            assertTrue(address.getNumber() > 0);
        });

        Address address = addresses.get(1);
        assertEquals("SUA", address.getNation());
        assertEquals("New York", address.getCity());
        assertEquals(2L, address.getID());
        assertEquals("W 14th", address.getStreet());
        assertEquals(10, address.getNumber());
        assertEquals(2L, address.getUserID());
    }

    @Test
    void itShouldFailOnNullResultSet() {

        try {
            ResultSetMapper.toObject(null, Address.class);
        } catch (Exception e) {
            assertTrue(e instanceof ResultSetMapperException);
            return;
        }
        fail();
    }

    @Test
    void itShouldFailOnNullType() throws SQLException {

        try (Statement stmt = dbHelper.getConnection().createStatement()) {
            System.out.println("Query for addresses...");

            String sql = "SELECT * FROM ADDRESS";
            ResultSet resultSet = stmt.executeQuery(sql);

            ResultSetMapper.toList(resultSet, null);
        } catch (Exception e) {
            assertTrue(e instanceof ResultSetMapperException);
            return;
        }

        fail();
    }
}
