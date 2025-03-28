package com.tomateunmate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomateunmate.entitie.Usuario;
import com.tomateunmate.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(Usuario usuario) {
        userRepository.save(usuario);
    }

    public Usuario findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
}
