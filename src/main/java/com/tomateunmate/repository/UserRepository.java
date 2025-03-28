package com.tomateunmate.repository;



import org.springframework.data.jpa.repository.JpaRepository;


import com.tomateunmate.entitie.Usuario;


public interface UserRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);  
}
