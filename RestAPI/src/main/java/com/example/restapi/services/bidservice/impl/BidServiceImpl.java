package com.example.restapi.services.bidservice.impl;

import com.example.restapi.entites.Bid;
import com.example.restapi.repositories.BidRepository;
import com.example.restapi.services.bidservice.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {
    private final BidRepository bidRepo;

    @Override
    public List<Bid> getByItem(Long itemId) {
        return bidRepo.findByItem(itemId);
    }

    @Override
    public List<Bid> getByUser(Long userId) {
        return bidRepo.findByUser(userId);
    }
}
