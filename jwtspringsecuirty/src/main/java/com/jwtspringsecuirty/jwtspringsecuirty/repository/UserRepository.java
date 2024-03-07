package com.jwtspringsecuirty.jwtspringsecuirty.repository;

import com.jwtspringsecuirty.jwtspringsecuirty.entinties.UserEntity;
import com.jwtspringsecuirty.jwtspringsecuirty.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity,String>{
    public Optional<UserEntity>findByEmail(String email);

}
