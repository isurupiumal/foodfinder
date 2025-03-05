package com.izufy.foodfinder.repository;

import com.izufy.foodfinder.model.Item;
import com.izufy.foodfinder.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User, Long> {
    @Query("MATCH (u1:User {id: $userId})-[r1:PURCHASED]->(i:Item)<-[r2:PURCHASED]-(u2:User) " +
            "WHERE r1.purchaseDate > datetime() - duration('P1Y') AND r2.purchaseDate > datetime() - duration('P1Y') " +
            "WITH u1, u2, COLLECT(DISTINCT i) as commonItems " +
            "MATCH (u1)-[r3:PURCHASED]->(i1:Item) " +
            "WHERE r3.purchaseDate > datetime() - duration('P1Y') " +
            "WITH u1, u2, commonItems, COLLECT(DISTINCT i1) as u1Items " +
            "MATCH (u2)-[r4:PURCHASED]->(i2:Item) " +
            "WHERE r4.purchaseDate > datetime() - duration('P1Y') " +
            "WITH u1, u2, commonItems, u1Items, COLLECT(DISTINCT i2) as u2Items " +
            "WITH u1, u2, SIZE(commonItems) as intersection, SIZE(u1Items) + SIZE(u2Items) - SIZE(commonItems) as union " +
            "WITH u1, u2, toFloat(intersection) / union as similarity " +
            "WHERE similarity > 0.1 " +
            "MATCH (u2)-[r5:PURCHASED]->(recItem:Item) " +
            "WHERE r5.purchaseDate > datetime() - duration('P1Y') " +
            "AND NOT (u1)-[:PURCHASED]->(recItem) " +
            "AND NONE(label IN recItem.dietaryLabels WHERE label IN u1.restrictions) " +
            "WITH u1, recItem, similarity " +
            "WITH u1, recItem, SUM(similarity) as baseScore " +
            "WITH u1, recItem, baseScore, " +
            "CASE WHEN recItem.category IN u1.preferredCategories THEN 1.5 ELSE 1.0 END as preferenceFactor " +
            "WITH recItem, baseScore * preferenceFactor as score " +
            "ORDER BY score DESC " +
            "LIMIT 10 " +
            "RETURN recItem")
    List<Item> recommendItems(@Param("userId") Long userId);
}
