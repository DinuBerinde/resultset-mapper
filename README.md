# resultset-mapper

The goal of this library is to map a Java ***java.sql.ResultSet*** to a ***POJO*** class.  
Each row of the result set will be mapped to a POJO instance and each column of the row will be
mapped to the field annotated with ```@MappedLabel()``` of the POJO. 

## Usage

```java

// map result set to object
User user = ResultSetMapper.toObject(resultSet, User.class);

// map result set to a list of objects
List<User> users = ResultSetMapper.toList(resultSet, User.class);

// apply the current row of the result set to an object
while(resultSet.next()) {
    users.add(ResultSetMapper.apply(resultSet, User.class));
}

```

## Full example

```java
import com.dinuberinde.MapperLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

// POJO
public class User {

    @MapperLabel(name = "ID")
    private Long id;

    @MapperLabel(name = "ID")
    private String name;

    @MapperLabel(name = "ID")
    private Date birthDate;

    // getters and setter or Lombok
}

// Usage
public class DBHelper {
    ...

    public User getUser(Long id) {
        try (Statement stmt = getDBConnection().createStatement()) {

            // query
            String sql = "SELECT * FROM USERS WHERE ID = " + id;
            ResultSet resultSet = stmt.executeQuery(sql);

            // map result set to a user
            User user = ResultSetMapper.toObject(resultSet, User.class);

            return user;
        }
    }

    public List<User> getUsers() {
        try (Statement stmt = getDBConnection().createStatement()) {

            // query
            String sql = "SELECT * FROM USERS";
            ResultSet resultSet = stmt.executeQuery(sql);

            // map result set to a list of users
            List<User> users = ResultSetMapper.toList(resultSet, User.class);

            return users;
        }
    }

    public List<User> getUsersWithApply() {
        try (Statement stmt = getDBConnection().createStatement()) {

            // query
            String sql = "SELECT * FROM USERS";
            ResultSet resultSet = stmt.executeQuery(sql);

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                // apply the current row of the result set to an object
                users.add(ResultSetMapper.apply(resultSet, User.class));
            }

            return users;
        }
    }

}
```

## Maven

```xml
<dependency>
    <groupId>com.dinuberinde</groupId>
    <artifactId>resultset-mapper</artifactId>
    <version>1.1</version>
</dependency>
```

## License

[Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.html)

## Author
Dinu Berinde <dinu2193@gmail.com>