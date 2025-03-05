package com.izufy.foodfinder.service;

import com.izufy.foodfinder.model.Item;
import com.izufy.foodfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {
    private final UserRepository userRepository;

    @Autowired
    public RecommendationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Item> getRecommendations(Long userId) {
        return userRepository.recommendItems(userId);
    }
}
