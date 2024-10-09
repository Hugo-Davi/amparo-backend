package com.tatu.mulher.services;

import com.tatu.mulher.models.Denounce;
import com.tatu.mulher.repositories.DenounceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DenounceService {
    @Autowired
    private DenounceRepository repository;

    public Denounce save(Denounce denounce) {
        return repository.save(denounce);
    }
    public Denounce get(String id) {
        return repository.findById(id).orElse(null);
    }
    public List<Denounce> getAll(){
        return repository.findAll();
    }
    public void delete(String id) {
        repository.deleteById(id);
    }
    public Denounce update(Denounce denounce) {
        return repository.save(denounce);
    }
    public boolean existById(String id) { return repository.existsById(id); }
}
