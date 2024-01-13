package com.example.restapi.services.crud.impl;

import com.example.restapi.dtos.BidDTO;
import com.example.restapi.entites.Bid;
import com.example.restapi.mappers.BidMapper;
import com.example.restapi.repositories.BidRepository;
import com.example.restapi.services.crud.CRUDBidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CRUDBidServiceImpl implements CRUDBidService {
    private final BidRepository bidRepo;

    @Override
    public List<BidDTO> getAll() {
        return bidRepo.findAll()
                .stream()
                .map(BidMapper::toModel)
                .toList();
    }

    @Override
    public BidDTO getById(Long id) {
        return bidRepo.findById(id)
                .map(BidMapper::toModel).get();
    }

    @Override
    public BidDTO create(BidDTO bidDTO) {
        return BidMapper.toModel(newBid(bidDTO));
    }

    @Override
    public void update(Long id, BidDTO bidDTO) {
        if(bidRepo.findById(id).isEmpty()){
            newBid(bidDTO);
        }
    }

    @Override
    public void delete(Long id) {
        bidRepo.deleteById(id);
    }

    private Bid newBid(BidDTO bidDTO) {
        return bidRepo.create(bidDTO.getUserId(),
                bidDTO.getUserId(),
                bidDTO.getComment(),
                bidDTO.getBidPrice());
    }
}
