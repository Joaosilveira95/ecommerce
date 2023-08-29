package com.nttdatateste.ecommerceteste.service;

import com.google.gson.*;
import com.nttdatateste.ecommerceteste.entity.Cart;
import com.nttdatateste.ecommerceteste.entity.User;
import com.nttdatateste.ecommerceteste.entity.exception.UserNotFoundException;
import com.nttdatateste.ecommerceteste.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartService cartService;

    @Autowired
    public UserService(UserRepository userRepository, CartService cartService) {
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    public User addUser(User user) throws Exception {
       User userWithAddress = getAddress(user);

        Cart cart = new Cart();
        cartService.addCart(cart, userWithAddress); // This might be responsible for cart-user association
        userWithAddress.setCart(cart);
        cart.setUserId(userWithAddress.getId());

        userRepository.save(userWithAddress);

        return userWithAddress;
    }

    public  User getAddress(User user) throws Exception {
        String postalCode = user.getPostalCode();

        if (postalCode.length() == 8) {
            // API CEP
            URL url = new URL("https://brasilapi.com.br/api/cep/v1/" + user.getPostalCode());
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String postCode = "";
            StringBuilder jsonPostalCode = new StringBuilder();

            while ((postCode = br.readLine()) != null) {
                jsonPostalCode.append(postCode);
            }
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                @Override
                public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
                }
            }).create();

            User userAux = gson.fromJson(jsonPostalCode.toString(), User.class);


            user.setPostalCode(postalCode);
            user.setState(userAux.getState());
            user.setCity(userAux.getCity());
            user.setNeighborhood(userAux.getNeighborhood());
            user.setStreet(userAux.getStreet());

            br.close();
            is.close();

        } else {
            throw new IllegalArgumentException("Invalid postal code");
        }
        return user;
    }



    public List<User> getUsers() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User deleteUser(Long id) {
        User user = getUser(id);
        cartService.deleteCart(user.getCart().getId());
        userRepository.delete(user);
        return user;
    }

    @Transactional
    public User editUser(Long id, User user) throws Exception {
        User userToEdit = getUser(id);

        if (user.getName() != null) {
            userToEdit.setName(user.getName());
        }
        if (user.getSurname() != null) {
            userToEdit.setSurname(user.getSurname());
        }
        if (user.getDocument() != null) {
           userToEdit.setDocument(user.getDocument());
        }
        if (user.getEmail() != null) {
            userToEdit.setEmail(user.getEmail());
        }

        if (user.getPassword() != null) {
            userToEdit.setPassword(user.getPassword());
        }

        if (user.getRole() != null) {
            userToEdit.setRole(user.getRole());
        }

        if (user.getPostalCode() != null) {
            userToEdit.setPostalCode(user.getPostalCode());
            getAddress(user);
            userToEdit.setState(user.getState());
            userToEdit.setCity(user.getCity());
            userToEdit.setNeighborhood(user.getNeighborhood());
            userToEdit.setStreet(user.getStreet());
        }


        LocalDateTime currentUpdateDate = userToEdit.getUpdatedDate();
        if (currentUpdateDate == null) {
            userToEdit.setUpdatedDate(userToEdit.getCreatedDate());
        } else {
            userToEdit.setUpdatedDate(user.getUpdatedDate());
        }


        return userToEdit;
    }



}
