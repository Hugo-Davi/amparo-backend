package com.tatu.amparo.services.support;

import com.tatu.amparo.dto.institute.CreateInstituteRequest;
import com.tatu.amparo.dto.institute.UpdateInstituteRequest;
import com.tatu.amparo.dto.location.LocationRequest;
import com.tatu.amparo.models.collections.Institute;
import com.tatu.amparo.models.collections.Post;
import com.tatu.amparo.models.collections.User;
import com.tatu.amparo.models.fields.Location;
import com.tatu.amparo.repositories.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstituteService {
    @Autowired
    private InstituteRepository repository;

    public Institute save(CreateInstituteRequest instituteRequest, String creatorId) {
        Institute institute = new Institute();
        institute.setName(instituteRequest.name());
        institute.setDescription(instituteRequest.description());
        institute.setLocation(instituteRequest.location());
        // create array of admins with creator
        List<User> admins = new ArrayList<>();
        admins.add(new User(creatorId));
        institute.setAdmins(admins);
        return repository.save(institute);
    }
    public Institute get(String id) {
        return repository.findById(id).orElse(null);
    }
    public void delete(String id){
        repository.deleteById(id);
    }

    public void updateInstitute(String id,
                                UpdateInstituteRequest instituteRequest) {
        repository.updateInstitute(
                id,
                instituteRequest.name(),
                instituteRequest.description(),
                instituteRequest.location()
        );
    }

    public List<Institute> getNear(LocationRequest location) {
        return repository.getNear(location.longitude(),location.latitude());
    }
}
