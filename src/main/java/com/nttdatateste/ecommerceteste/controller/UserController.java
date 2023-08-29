package com.nttdatateste.ecommerceteste.controller;


import com.nttdatateste.ecommerceteste.entity.User;
import com.nttdatateste.ecommerceteste.entity.dto.UserDto;
import com.nttdatateste.ecommerceteste.repository.UserRepository;
import com.nttdatateste.ecommerceteste.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @Operation(summary = "Add New User", description = "Add a new User to DB")
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody final UserDto userDto) throws Exception {
        User user = userService.addUser(User.from(userDto));
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Users", description = "Get All Users in DB")
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userService.getUsers();
        List<UserDto> usersDto = users.stream().map(UserDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @Operation(summary = "Get User By ID", description = "Get Brand User ID in DB")
    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable final Long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }

    @Operation(summary = "Get Users By Name", description = "Get Users By Name or Partial Name")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserDto>> searchUsersByName(@PathVariable String name) {
        List<User> users = (List<User>) userRepository.searchByNameLike(name);
        List<UserDto> userDtos = users.stream().map(UserDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @Operation(summary = "Delete User by ID", description = "Delete the User from ID in DB")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable final Long id) {
        User user = userService.deleteUser(id);
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }

    @Operation(summary = "Update User by ID", description = "Update the User in DB")
    @PutMapping(value = "{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable final Long id, @RequestBody final UserDto userDto) throws Exception {
        User editedUser = userService.editUser(id, User.from(userDto));
        return new ResponseEntity<>(UserDto.from(editedUser), HttpStatus.OK);

    }
    
    @Operation(summary = "Get User By Email", description = "Get Users By Email")
    @GetMapping("/email/{email}")
    public ResponseEntity<List<UserDto>> searchUsersByEmail(@PathVariable String email) {
        List<User> users = (List<User>) userRepository.searchByEmailLike(email);
        List<UserDto> userDtos = users.stream().map(UserDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }



}