package com.tatu.amparo.services;

import com.tatu.amparo.dto.user.SupportNetworkGet;
import com.tatu.amparo.models.Comment;
import com.tatu.amparo.models.SupportNetwork;
import com.tatu.amparo.models.User;
import com.tatu.amparo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupportNetworkService {
    @Autowired
    private UserRepository userRepository;

    public List<SupportNetwork> getSupportNetwork (String id) {
        SupportNetworkGet supportNetworkGet = userRepository.getSupportNetwork(id);
        return supportNetworkGet.supportNetwork();
    }

    public List<SupportNetwork> addSupportNetwork (String id, List<SupportNetwork> newSupportNetwork){
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return null;
        }
        user.setSupportNetwork(newSupportNetwork);
        userRepository.save(user);

        return newSupportNetwork;
    }
}
