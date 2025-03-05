package com.izufy.foodfinder.service;

import com.izufy.foodfinder.model.Item;
import com.izufy.foodfinder.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private final ItemRepository itemRepository;

    @Autowired
    public SearchService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Page<Item> searchItems(String query, Double minPrice, Double maxPrice, List<String> dietaryOptions, Pageable pageable) {
        return itemRepository.searchItems(query, minPrice, maxPrice, dietaryOptions, pageable);
    }
}
