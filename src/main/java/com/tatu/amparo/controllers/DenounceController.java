package com.tatu.amparo.controllers;

import com.google.gson.Gson;
import com.tatu.amparo.models.Denounce;
import com.tatu.amparo.services.DenounceService;
import com.tatu.amparo.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/denounce")
public class DenounceController {
    @Autowired
    private DenounceService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> getAll(){
        String response = new Gson().toJson(this.service.getAll());
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable("id") String id) {
        String response = new Gson().toJson(this.service.get(id));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create (@RequestBody Denounce denounce){
        this.service.save(denounce);
        Response response = new Response("Denúncia salva");
        return ResponseEntity.ok(new Gson().toJson(response));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update (@PathVariable("id") String id, @RequestBody Denounce denounce){

        if (!this.service.existById(id)){
            return ResponseEntity.notFound().build();
        }
        denounce.setId(id);
        this.service.save(denounce);

        Response response = new Response("Denúncia atualizada");
        return ResponseEntity
                .ok(new Gson().toJson(response));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable String id) {
        Denounce denounce = this.service.get(id);
        if (denounce == null) {
            return ResponseEntity.notFound().build();
        }
        this.service.delete(id);
        String response = new Gson().toJson(denounce);

        return ResponseEntity
                .ok(response);
    }

}
