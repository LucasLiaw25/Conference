package com.liaw.dev.Conference.controller;

import com.liaw.dev.Conference.dto.*;
import com.liaw.dev.Conference.entity.User;
import com.liaw.dev.Conference.enums.State;
import com.liaw.dev.Conference.mapper.UserMapper;
import com.liaw.dev.Conference.pix.PixService;
import com.liaw.dev.Conference.service.TokenService;
import com.liaw.dev.Conference.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;
    private final TokenService tokenService;
    private final PixService pixService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<TokenDTO> createUser(@RequestBody @Valid UserDTO dto){
        UserDTO userDTO = service.createUser(dto);
        User user = mapper.toEntity(userDTO);
        String token = tokenService.generateToken(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TokenDTO(token));
    }

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO dto){
        UsernamePasswordAuthenticationToken userPass = new UsernamePasswordAuthenticationToken(
                dto.email(), dto.password()
        );
        Authentication authenticate = authenticationManager.authenticate(userPass);
        User user = (User) authenticate.getPrincipal();
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(
                new TokenDTO(token)
        );
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listUser(){
        return ResponseEntity.ok(service.listUser());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<UserResponse>> findByState(@PathVariable State state){
        return ResponseEntity.ok(service.listByState(state));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserDTO dto){
        return ResponseEntity.ok(service.updateUser(id, dto));
    }

    @PostMapping("admin/{adminId}/{userId}")
    public ResponseEntity<Message> giveAdmin(@RequestBody GiveAdmDTO dto) {
        service.givAdmin(dto.adminId(), dto.userId());
        return ResponseEntity.ok(
                new Message(
                        HttpStatus.OK.value(),
                        "Admin concedido ao usuário"
                )
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Message> deleteUser(@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.ok(
                new Message(
                        HttpStatus.OK.value(),
                        "Usuário deletado com sucesso"
                )
        );
    }

}
