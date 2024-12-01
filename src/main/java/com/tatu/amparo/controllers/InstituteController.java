package com.tatu.amparo.controllers;

import com.tatu.amparo.dto.institute.CreateInstituteRequest;
import com.tatu.amparo.dto.institute.UpdateInstituteRequest;
import com.tatu.amparo.models.collections.Institute;
import com.tatu.amparo.models.collections.Post;
import com.tatu.amparo.models.collections.User;
import com.tatu.amparo.models.fields.Location;
import com.tatu.amparo.services.support.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/inst")
public class InstituteController {
    @Autowired
    private InstituteService service;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Institute> create (@RequestBody CreateInstituteRequest instituteRequest,
                                             JwtAuthenticationToken token) {
        if (token.getName() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(this.service.save(instituteRequest, token.getName()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Institute> getInstitute (@PathVariable("id") String id) {
        Institute institute = this.service.get(id);
        if (institute == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(institute);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateInstitute (@PathVariable("id") String id,
                                                @RequestBody UpdateInstituteRequest instituteRequest,
                                                JwtAuthenticationToken token) {
        if (token.getName() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Institute institute = this.service.get(id);
        if (institute == null) {
            return ResponseEntity.notFound().build();
        }
        if (!institute.hasAdmin(token.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        this.service.updateInstitute(id, instituteRequest);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete (@PathVariable("id") String id,
                                             JwtAuthenticationToken token) {
        if (token.getName() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Institute institute = this.service.get(id);
        if (institute == null) {
            return ResponseEntity.notFound().build();
        }
        if (!institute.hasAdmin(token.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }

//    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Institute>> getInstitutesNearby (@RequestBody Location location) {
//        return ResponseEntity.ok(this.service.getNearby(location));
//    }
}
