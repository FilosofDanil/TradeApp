package com.example.tradeapp.client;

import com.example.tradeapp.entities.models.Bids;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bidClient", url = "${application.config.bids}")
public interface BidClient {
    @GetMapping("")
    List<Bids> getAllBids();

    @GetMapping("/{id}")
    Bids getBidById(@PathVariable Long id);

    @GetMapping("/item/{itemId}")
    List<Bids> getAllBidsByItem(@PathVariable Long itemId);

    @GetMapping("/user/{userId}")
    List<Bids> getAllBidsByUser(@PathVariable Long userId);

    @PostMapping("")
    Bids createBid(@RequestBody Bids bid);

    @PutMapping("/{id}")
    void updateBid(@RequestBody Bids bid, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void deleteBid(@PathVariable Long id);
}
