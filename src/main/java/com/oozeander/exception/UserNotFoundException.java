package com.oozeander.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -6603997201257665462L;

    public UserNotFoundException(String id) {
        super("User n°" + Long.valueOf(id) + " does not exist");
    }
}