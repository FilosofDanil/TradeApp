package com.example.restapi.repositories;

import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.entites.Item;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
    List<Item> findAll();

    @Modifying
    @Query(value = "insert into items " +
            "(item_name, description, start_price, " +
            "placement_date, expiration_date, user_id)" +
            " values (:itemName, :description, :startPrice," +
            " :placementDate, :expirationDate, :userId) returning " +
            "id, bid_price, item_name, description, start_price," +
            " placement_date, expiration_date, user_id",
            nativeQuery = true)
    Item create(@Param("itemName") String itemName,
                @Param("description") String description,
                @Param("startPrice") Integer startPrice,
                @Param("placementDate") Date placementDate,
                @Param("expirationDate") Date expirationDate,
                @Param("userId") Long userId);

    @Modifying
    @Query("update Item set itemName = :itemName," +
            " description = :description, expirationDate = :expirationDate," +
            " placementDate = :placementDate" +
            " where id = :id")
    void updateItem(@Param("itemName") String itemName,
                    @Param("description") String description,
                    @Param("expirationDate") Date expirationDate,
                    @Param("placementDate") Date placementDate,
                    @Param("id") Long id);

    List<Item> findByUserUsername(String username);

    List<Item> findByUserTgName(String tgName);
}
