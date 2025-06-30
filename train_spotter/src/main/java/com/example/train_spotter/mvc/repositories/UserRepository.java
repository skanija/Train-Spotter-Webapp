package com.example.train_spotter.mvc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.train_spotter.mvc.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    List<User> findById(long id);

    void deleteById(long id);

    Optional<User> findByEmail(String email);

    User getByEmail(String email);

}
