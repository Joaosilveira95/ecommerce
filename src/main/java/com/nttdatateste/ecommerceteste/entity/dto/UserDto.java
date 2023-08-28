package com.nttdatateste.ecommerceteste.entity.dto;

import com.nttdatateste.ecommerceteste.entity.Role;
import com.nttdatateste.ecommerceteste.entity.User;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String document;
    private String email;
    private String password;
    private String postalCode;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;


    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setDocument(user.getDocument());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setPostalCode(user.getPostalCode());
        userDto.setState(user.getState());
        userDto.setCity(user.getCity());
        userDto.setNeighborhood(user.getNeighborhood());
        userDto.setStreet(user.getStreet());


        userDto.setCreatedDate(user.getCreatedDate());
        userDto.setUpdatedDate(user.getUpdatedDate());

        return userDto;
    }
}