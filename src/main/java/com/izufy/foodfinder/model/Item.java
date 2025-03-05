package com.izufy.foodfinder.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@Data
@Node
public class Item {
    @Id
    private Long id;
    private String name;
    private String description;
    private String category;
    private List<String> dietaryLabels;
    private Double price;
}
