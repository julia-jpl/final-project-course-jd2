package com.gmail.portnova.julia.web.controller.api;

import com.gmail.portnova.julia.service.UserAddService;
import com.gmail.portnova.julia.service.model.UserDTO;
import com.gmail.portnova.julia.web.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserValidator userValidator;
    private final UserAddService userAddService;

    @PostMapping
    public ResponseEntity<Void> addUser(@Valid @RequestBody UserDTO user, BindingResult result) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDTO savedUser = userAddService.addUserToDatabase(user);
        userAddService.sendEmail(savedUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
