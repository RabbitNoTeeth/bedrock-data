package com.github.rabbitnoteeth.bedrock.data.sql.exception;

public class SqlClientException extends Exception {

    public SqlClientException() {
        super();
    }

    public SqlClientException(String message) {
        super(message);
    }

    public SqlClientException(Throwable e) {
        super(e);
    }

    public SqlClientException(String message, Throwable e) {
        super(message, e);
    }
}
