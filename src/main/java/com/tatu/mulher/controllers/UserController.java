package com.tatu.mulher.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "/", headers = "key=teste", method = RequestMethod.GET)
    public String getUsers(){
        System.out.println("users get");
        return "funcionando";
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getUserById (@PathVariable("id") long id){
        System.out.println(id);
        return "O id desse usuário é: " + id;
    }
}
