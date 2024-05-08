package com.suhoi.in.controller;

import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;
import com.suhoi.facade.UserFacade;
import com.suhoi.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @PostMapping("/register")
    public ResponseEntity<User> create(@RequestBody CreateUserDto createUserDto,
                                    UriComponentsBuilder uriBuilder) {

        User user = userFacade.signUp(createUserDto);
        return ResponseEntity
                .created(uriBuilder.path("/api/users/{id}")
                        .buildAndExpand(user.getId()).toUri())
                .body(user);

    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody AuthDto authDto) {
        userFacade.auth(authDto);
        return ResponseEntity.ok().body("auth success");
    }
}
