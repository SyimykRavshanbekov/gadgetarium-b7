package com.example.gadgetariumb7.db.repository;

import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.dto.response.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select case when count(u)>0 then true else false end from User u where u.email like :email")
    boolean existsByEmail(@Param(value = "email") String email);

    @Query("select new com.example.gadgetariumb7.dto.response.UserResponse(" +
            "r.user.id," +
            "r.user.firstName," +
            "r.user.lastName," +
            "r.user.email," +
            "r.user.image) from Review  r join User u on r.user.id=u.id")
    UserResponse getUser();
}