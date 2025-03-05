package com.izufy.foodfinder.controller;

import com.izufy.foodfinder.model.Item;
import com.izufy.foodfinder.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public Page<Item> searchItems(@RequestParam String query,
                                  @RequestParam(required = false) Double minPrice,
                                  @RequestParam(required = false) Double maxPrice,
                                  @RequestParam(required = false) List<String> dietaryOptions,
                                  Pageable pageable) {
        return searchService.searchItems(query, minPrice, maxPrice, dietaryOptions, pageable);
    }
}
