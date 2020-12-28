package com.oozeander.service;

import com.oozeander.model.User;
import org.javatuples.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    User get(Long id);

    Pair<Boolean, User> save(User user, MultipartFile file) throws IOException;
}