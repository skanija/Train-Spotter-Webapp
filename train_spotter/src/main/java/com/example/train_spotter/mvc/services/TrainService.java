package com.example.train_spotter.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.train_spotter.mvc.models.Train;
import com.example.train_spotter.mvc.repositories.TrainRepository;

@Service
public class TrainService {

    private final TrainRepository trainRepository;
    
    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public List<Train> allTrains() {
        return trainRepository.findAll();
    }

    public Train createTrain(Train b) {
        return trainRepository.save(b);
    }

    public Train findTrain(Long id) {
        Optional<Train> optionalUser = trainRepository.findById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            return null;
        }
    }

    public Train updateTrain(Train b) {
        return trainRepository.save(b);
    }

    public void deleteTrain(Long id) {
        trainRepository.deleteById(id);
    }

}
