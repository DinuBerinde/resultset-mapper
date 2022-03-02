package com.dinuberinde;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * The goal of this library is to map a Java {@link java.sql.ResultSet} to a POJO class.
 * <p>Example:</p>
 * <pre class="code">
 *public class User {
 *
 *  &#064;MapperLabel(name = "ID")
 *  private Long id;
 *
 *  &#064;MapperLabel(name = "NAME")
 *  private String name;
 *
 *  &#064;MapperLabel(name = "BIRTHDATE")
 *  private Date birthDate;
 *
 *  // getters and setters or Lombok
 * }
 *</pre>
 *
 * <p>Usage:</p>
 * <pre class="code">
 *
 *  // map result set to a user
 *  ResultSetMapper.toObject(resultSet, User.class);
 *
 *  // or map result set to a list of users
 *  List<User> users = ResultSetMapper.toList(resultSet, User.class);
 *
 *  // or apply the current row of the result set to an object
 *  List<User> users = new ArrayList<>();
 *  while(resultSet.next()) {
 *      users.add(ResultSetMapper.apply(resultSet, User.class));
 *  }
 * </pre>
 */
public class ResultSetMapper {

    private ResultSetMapper() {}

    /**
     * It maps the current row of the result set to an object.
     * @param resultSet the result set
     * @param type the type of the resulting object
     * @return the object mapped
     */
    public static <T> T apply(ResultSet resultSet, Class<T> type) {

        if (resultSet == null) {
            throw new ResultSetMapperException("ResultSet cannot be null");
        }

        if (type == null) {
            throw new ResultSetMapperException("Type cannot be null");
        }

        try {
            return buildDTO(type, resultSet);
        } catch (Exception e) {
            throw new ResultSetMapperException(e);
        }
    }

    /**
     * It maps a result set to a single object by iterating over the result set.
     * @param resultSet the result set
     * @param type the type of the resulting object
     * @return the object mapped
     */
    public static <T> T toObject(ResultSet resultSet, Class<T> type) {
        List<T> dtoList = toList(resultSet, type);
        return !dtoList.isEmpty() ? dtoList.get(0) : null;
    }

    /**
     * It maps a result set to a list of objects by iterating over the result set.
     * @param resultSet the result set
     * @param type the type of the objects
     * @return a list of mapped objects
     */
    public static <T> List<T> toList(ResultSet resultSet, Class<T> type) {

        if (resultSet == null) {
            throw new ResultSetMapperException("ResultSet cannot be null");
        }

        if (type == null) {
            throw new ResultSetMapperException("Type cannot be null");
        }

        try {
            List<T> dtoList = new ArrayList<>();

            while (resultSet.next()) {
                T dto = buildDTO(type, resultSet);
                dtoList.add(dto);
            }

            return dtoList;
        } catch (Exception e) {
            throw new ResultSetMapperException(e);
        }
    }

    private static <T> T buildDTO(Class<T> type, ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        T dto = type.getDeclaredConstructor().newInstance();
        List<Field> fields = getAllFields(type);

        for (Field field: fields) {
            MapperLabel annotation = field.getAnnotation(MapperLabel.class);
            if (annotation != null) {
                String columnName = annotation.name();
                Class<?> fieldType = field.getType();
                Object value = annotation.optional() ? safelyGetValue(fieldType, resultSet, columnName) : getValue(fieldType, resultSet, columnName);

                field.setAccessible(true);
                field.set(dto, value);
            }
        }

        return dto;
    }

    private static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> clazz = type; clazz != null; clazz = clazz.getSuperclass()) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        return fields;
    }

    private static Object safelyGetValue(Class<?> fieldType, ResultSet resultSet, String columnName) {
        try {
            return getValue(fieldType, resultSet, columnName);
        } catch (SQLException e) {
            return null;
        }
    }

    private static Object getValue(Class<?> fieldType, ResultSet resultSet, String columnName) throws SQLException {

        if (fieldType == String.class) {
            return resultSet.getString(columnName);
        } else if (fieldType == Long.class || fieldType == long.class) {
            return resultSet.getLong(columnName);
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return resultSet.getInt(columnName);
        } else if (fieldType == Float.class || fieldType == float.class) {
            return resultSet.getFloat(columnName);
        } else if (fieldType == Double.class || fieldType == double.class) {
            return resultSet.getDouble(columnName);
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return resultSet.getBoolean(columnName);
        } else if (fieldType == Date.class || fieldType == java.sql.Date.class) {
            return resultSet.getDate(columnName);
        } else if (fieldType == Byte.class || fieldType == byte.class) {
            return resultSet.getByte(columnName);
        } else if (fieldType == BigDecimal.class) {
            return resultSet.getBigDecimal(columnName);
        } else if (fieldType == byte[].class) {
            return resultSet.getBytes(columnName);
        } else if (fieldType == Blob.class) {
            return resultSet.getBlob(columnName);
        } else if (fieldType == InputStream.class) {
            return resultSet.getBinaryStream(columnName);
        } else if (fieldType == Timestamp.class) {
            return resultSet.getTimestamp(columnName);
        } else if (fieldType == Object.class) {
            return resultSet.getObject(columnName);
        } else if (fieldType == Clob.class) {
            return resultSet.getClob(columnName);
        } else if (fieldType == Array.class) {
            return resultSet.getArray(columnName);
        } else {
            throw new IllegalStateException("No suitable type was found for " + columnName);
        }
    }
}
