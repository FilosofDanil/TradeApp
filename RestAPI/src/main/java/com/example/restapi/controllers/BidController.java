package com.example.restapi.controllers;

import com.example.restapi.dtos.BidDTO;
import com.example.restapi.entites.Bid;
import com.example.restapi.services.bidservice.BidService;
import com.example.restapi.services.crud.CRUDService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bids")
@RequiredArgsConstructor
public class BidController {
    private final CRUDService<Bid> crudService;

    private final BidService bidService;

    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<BidDTO>> getAllBids() {
        return ResponseEntity.ok(crudService.getAll()
                .stream()
                .map(this::toDto)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BidDTO> getBidById(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(crudService.getById(id)));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<BidDTO>> getAllBidsByItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(bidService.getByItem(itemId).stream()
                .map(this::toDto)
                .toList());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BidDTO>> getAllBidsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bidService.getByUser(userId)
                .stream()
                .map(this::toDto)
                .toList());
    }

    @PostMapping("")
    public ResponseEntity<BidDTO> createBid(@RequestBody BidDTO bid) {
        return new ResponseEntity<>(toDto(crudService.create(toEntity(bid))), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBid(@RequestBody BidDTO bid, @PathVariable Long id) {
        crudService.update(id, toEntity(bid));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBid(@PathVariable Long id) {
        crudService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private BidDTO toDto(Bid bid) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(bid, BidDTO.class);
    }

    private Bid toEntity(BidDTO bidDTO) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(bidDTO, Bid.class);
    }
}
