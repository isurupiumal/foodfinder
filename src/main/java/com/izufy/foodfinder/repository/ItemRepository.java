package com.izufy.foodfinder.repository;

import com.izufy.foodfinder.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends Neo4jRepository<Item, Long> {
    @Query("CALL db.index.fulltext.queryNodes('itemSearch', $query) YIELD node " +
            "WHERE ($minPrice IS NULL OR node.price >= $minPrice) " +
            "AND ($maxPrice IS NULL OR node.price <= $maxPrice) " +
            "AND ($dietaryOptions IS NULL OR ALL(label IN $dietaryOptions WHERE label IN node.dietaryLabels)) " +
            "RETURN node " +
            "ORDER BY node.id " +
            "SKIP $skip " +
            "LIMIT $limit")
    List<Item> searchItemsContent(@Param("query") String query,
                                  @Param("minPrice") Double minPrice,
                                  @Param("maxPrice") Double maxPrice,
                                  @Param("dietaryOptions") List<String> dietaryOptions,
                                  @Param("skip") long skip,
                                  @Param("limit") int limit);

    @Query("CALL db.index.fulltext.queryNodes('itemSearch', $query) YIELD node " +
            "WHERE ($minPrice IS NULL OR node.price >= $minPrice) " +
            "AND ($maxPrice IS NULL OR node.price <= $maxPrice) " +
            "AND ($dietaryOptions IS NULL OR ALL(label IN $dietaryOptions WHERE label IN node.dietaryLabels)) " +
            "RETURN COUNT(node)")
    long searchItemsCount(@Param("query") String query,
                          @Param("minPrice") Double minPrice,
                          @Param("maxPrice") Double maxPrice,
                          @Param("dietaryOptions") List<String> dietaryOptions);

    default Page<Item> searchItems(String query, Double minPrice, Double maxPrice, List<String> dietaryOptions, Pageable pageable) {
        List<Item> content = searchItemsContent(query, minPrice, maxPrice, dietaryOptions, pageable.getOffset(), pageable.getPageSize());
        long total = searchItemsCount(query, minPrice, maxPrice, dietaryOptions);
        return new PageImpl<>(content, pageable, total);
    }
}