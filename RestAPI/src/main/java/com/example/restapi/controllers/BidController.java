package com.example.restapi.controllers;

import com.example.restapi.dtos.BidDTO;
import com.example.restapi.dtos.ItemDTO;
import com.example.restapi.dtos.UpdateItemDTO;
import com.example.restapi.entites.Bid;
import com.example.restapi.services.bidservice.BidService;
import com.example.restapi.services.crud.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bids")
@RequiredArgsConstructor
public class BidController {
    private final CRUDService<BidDTO> crudService;

    private final BidService bidService;

    @GetMapping("")
    public ResponseEntity<List<BidDTO>> getAllBids() {
        return ResponseEntity.ok(crudService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BidDTO> getBidById(@PathVariable Long id) {
        return ResponseEntity.ok(crudService.getById(id));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<BidDTO>> getAllBidsByItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(bidService.getByItem(itemId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BidDTO>> getAllBidsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bidService.getByUser(userId));
    }

    @PostMapping("")
    public ResponseEntity<BidDTO> createBid(@RequestBody BidDTO bid) {
        return new ResponseEntity<>(crudService.create(bid), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBid(@RequestBody BidDTO bid, @PathVariable Long id) {
        crudService.update(id, bid);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBid(@PathVariable Long id) {
        crudService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
