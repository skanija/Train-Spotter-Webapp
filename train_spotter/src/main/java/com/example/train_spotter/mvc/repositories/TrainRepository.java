package com.example.train_spotter.mvc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.train_spotter.mvc.models.Train;
import com.example.train_spotter.mvc.models.User;

@Repository
public interface TrainRepository extends CrudRepository<Train, Long> {

    List<Train> findAll();

    List<Train> findById(long id);

    void deleteById(long id);

    List<Train> findByUser(User user);

}
