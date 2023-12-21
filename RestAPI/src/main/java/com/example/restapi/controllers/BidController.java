package com.example.restapi.controllers;

import com.example.restapi.dtos.BidDTO;
import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.dtos.UpdateItemDTO;
import com.example.restapi.entites.Bid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bids")
@RequiredArgsConstructor
public class BidController {
    @GetMapping("")
    public ResponseEntity<List<Bid>> getAllBids() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bid> getBidById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<Bid>> getAllBidsByItem(@PathVariable Long itemId) {
        return null;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bid>> getAllBidsByUser(@PathVariable Long userId) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBid(@RequestBody BidDTO bid, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBid(@PathVariable Long id) {
        return null;
    }

}
