package com.tatu.mulher.controllers;

import com.tatu.mulher.models.User;
import com.tatu.mulher.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private long idArmazenado;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", headers = "key=teste", method = RequestMethod.GET)
    public String getUsers(){
        System.out.println("users get");
        return "funcionando";
    }

    @RequestMapping(value = "/valor", method = RequestMethod.GET)
    public String getIdArmazenado(){
        return "Id Armazenado no server é: " + this.idArmazenado;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getUserById (@PathVariable("id") long id){
        System.out.println(id);
        this.idArmazenado = id;
        return "O id desse usuário é: " + id;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public String getUserById (@PathVariable("name") String name){
        User user = new User(name, "20");
        this.userService.saveUser(user);
        return "O nome do usuario é: " + name;
    }
}