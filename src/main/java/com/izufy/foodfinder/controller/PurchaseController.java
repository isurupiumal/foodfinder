package com.izufy.foodfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final Neo4jClient neo4jClient;

    @Autowired
    public PurchaseController(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    @PostMapping
    public void recordPurchase(@RequestParam Long userId, @RequestParam Long itemId) {
        neo4jClient.query(
                "MATCH (u:User {id: $userId}), (i:Item {id: $itemId}) " +
                        "CREATE (u)-[:PURCHASED {purchaseDate: datetime()}]->(i)"
        ).bind(userId).to("userId").bind(itemId).to("itemId").run();
    }
}
