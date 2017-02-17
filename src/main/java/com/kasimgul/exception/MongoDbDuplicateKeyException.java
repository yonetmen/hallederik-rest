package com.kasimgul.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.CONFLICT)
public class MongoDbDuplicateKeyException extends DuplicateKeyException implements Serializable {
    private static final long SerialVersionUID = 1L;

    public MongoDbDuplicateKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MongoDbDuplicateKeyException(String msg) {
        super(msg);
    }
}
