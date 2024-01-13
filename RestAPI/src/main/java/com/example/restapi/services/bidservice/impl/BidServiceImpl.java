package com.example.restapi.services.bidservice.impl;

import com.example.restapi.dtos.BidDTO;
import com.example.restapi.mappers.BidMapper;
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
    public List<BidDTO> getByItem(Long itemId) {
        return bidRepo.findByItem(itemId)
                .stream()
                .map(BidMapper::toModel)
                .toList();
    }

    @Override
    public List<BidDTO> getByUser(Long userId) {
        return bidRepo.findByUser(userId)
                .stream()
                .map(BidMapper::toModel)
                .toList();
    }
}
