package com.oozeander.service.impl;

import com.oozeander.model.User;
import com.oozeander.model.embeddable.Image;
import com.oozeander.repository.UserRepository;
import com.oozeander.service.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User get(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public Pair<Boolean, User> save(User user, MultipartFile file) throws IOException {
        Boolean isSaved = Boolean.TRUE;
        User userToSave = null;
        if (user != null && !file.isEmpty()) try {
            userToSave = new User(
                    user.getFirstname(),
                    user.getLastname(),
                    new Image(
                            file.getOriginalFilename(),
                            file.getContentType(),
                            file.getSize() / 1024 + " Ko",
                            BlobProxy.generateProxy(file.getBytes())
                    )
            );
            userRepository.saveAndFlush(userToSave);
        } catch (Exception exception) {
            isSaved = Boolean.FALSE;
            System.out.println("Error in saving User, cause : " + exception.getLocalizedMessage());
        }
        else isSaved = Boolean.FALSE;
        return Pair.with(isSaved, userToSave);
    }
}