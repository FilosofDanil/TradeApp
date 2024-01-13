package com.example.restapi.repositories;

import com.example.restapi.entites.Bid;
import com.example.restapi.entites.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BidRepository extends CrudRepository<Bid, Long> {
    List<Bid> findAll();

    @Modifying
    @Query(value = "insert into bids (user_id, item_id, " +
            "comment, bid_price)" +
            " values (:userId, :itemId, :comment, :bidPrice)" +
            "returning id, user_id, item_id,comment, bid_price",
            nativeQuery = true)
    Bid create(@Param("userId") Long userId,
               @Param("itemId") Long itemId,
               @Param("comment") String comment,
               @Param("bidPrice") Integer bidPrice);

    @Modifying
    @Query("update Bid set bidPrice = :bidPrice," +
            "comment = :comment" +
            " where id = :id")
    void updateBid(@Param("bidPrice") Integer bidPrice,
                   @Param("comment") String comment);

    @Query(value = "select * from bids where item_id = :itemId",
            nativeQuery = true)
    List<Bid> findByItem(@Param("itemId") Long itemId);

    @Query(value = "select * from bids where user_id = :userId",
            nativeQuery = true)
    List<Bid> findByUser(@Param("userId") Long userId);
}
