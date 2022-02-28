package com.dinuberinde;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        Field[] fields = type.getDeclaredFields();

        for (Field field: fields) {
            MapperLabel annotation = field.getAnnotation(MapperLabel.class);
            if (annotation != null) {
                String columnName = annotation.name();
                Class<?> fieldType = field.getType();
                field.setAccessible(true);
                field.set(dto, getValue(fieldType, resultSet, columnName));
            }
        }

        return dto;
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
