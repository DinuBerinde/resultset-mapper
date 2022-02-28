package com.dinuberinde;

public class ResultSetMapperException extends RuntimeException {

    public ResultSetMapperException(Exception e) {
        super(e);
    }

    public ResultSetMapperException(String message) {
        super(message);
    }
}
