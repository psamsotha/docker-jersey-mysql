package org.example.resource.db;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String msg) {
        super(msg);
    }

    public DatabaseException(Exception ex) {
        super(ex);
    }

    public DatabaseException(String msg, Exception ex) {
        super(msg, ex);
    }
}
