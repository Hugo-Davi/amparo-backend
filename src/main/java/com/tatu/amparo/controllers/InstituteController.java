package com.tatu.amparo.controllers;

import com.tatu.amparo.dto.institute.CreateInstituteRequest;
import com.tatu.amparo.dto.institute.UpdateInstituteRequest;
import com.tatu.amparo.dto.location.LocationRequest;
import com.tatu.amparo.models.collections.Institute;
import com.tatu.amparo.services.support.InstituteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "institute")
@RestController
@RequestMapping(value = "/inst")
public class InstituteController {
    @Autowired
    private InstituteService service;

    @Operation(summary = "Create institute")
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Institute> create (@RequestBody CreateInstituteRequest instituteRequest,
                                             JwtAuthenticationToken token) {
        if (token.getName() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(this.service.save(instituteRequest, token.getName()));
    }

    @Operation(summary = "Get institute by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Institute> getInstitute (@PathVariable("id") String id) {
        Institute institute = this.service.get(id);
        if (institute == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(institute);
    }

    @Operation(summary = "Update institute if the user is Admin of the institute")
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

    @Operation(summary = "Delete institute if the user is Admin of the institute")
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

    @Operation(summary = "Get the institutes near the coordinates in the body")
    @RequestMapping(value = "/near", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Institute>> getInstitutesNearby (@RequestBody LocationRequest location) {
        return ResponseEntity.ok(this.service.getNear(location));
    }
}
