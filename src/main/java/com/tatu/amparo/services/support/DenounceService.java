package com.tatu.amparo.services.support;

import com.tatu.amparo.models.collections.Denounce;
import com.tatu.amparo.repositories.DenounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public List<Denounce> getDenouncesByUser(String id) {
        return repository.findByUser(id);
    }
}
