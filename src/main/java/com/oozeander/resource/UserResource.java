package com.oozeander.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.oozeander.exception.UserNotFoundException;
import com.oozeander.model.User;
import com.oozeander.service.UserService;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author b.ketrouci
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserResource {
    @Autowired
    private UserService userService;
    private final ObjectMapper mapper = new ObjectMapper();

    public UserResource() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userService.get(id);
        if (user != null)
            return ResponseEntity.ok(user);
        throw new UserNotFoundException(String.valueOf(id));
    }

    @Transactional
    @GetMapping(value = "/image/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> getImage(@PathVariable("id") Long id) throws SQLException {
        User user = userService.get(id);
        if (user != null)
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(user.getImage().getFileExtension()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.getImage().getFileName() + "\"")
                    .body(new ByteArrayResource(user.getImage().getFileData().getBytes(1L, (int) user.getImage().getFileData().length())));
        return ResponseEntity.notFound().build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<User> saveUser(@RequestParam("user") String userJson, @RequestParam("file") MultipartFile file) throws IOException {
        User user = mapper.readValue(userJson, User.class);
        Pair<Boolean, User> userSaved = userService.save(user, file);
        if (userSaved.getValue0())
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(userSaved.getValue1().getId()).toUri()).body(userSaved.getValue1());
        return ResponseEntity.badRequest().build();
    }
}
