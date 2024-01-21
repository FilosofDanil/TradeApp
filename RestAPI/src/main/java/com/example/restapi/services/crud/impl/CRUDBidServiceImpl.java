package com.example.restapi.services.crud.impl;

import com.example.restapi.dtos.BidDTO;
import com.example.restapi.entites.Bid;
import com.example.restapi.entites.Item;
import com.example.restapi.repositories.BidRepository;
import com.example.restapi.repositories.ItemRepository;
import com.example.restapi.services.crud.CRUDBidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CRUDBidServiceImpl implements CRUDBidService {
    private final BidRepository bidRepo;

    private final ItemRepository itemRepository;

    @Override
    public List<Bid> getAll() {
        return bidRepo.findAll();
    }

    @Override
    public Bid getById(Long id) {
        return bidRepo.findById(id).get();
    }

    @Override
    public Bid create(Bid bid) {
        Item item = itemRepository.findById(bid.getItem().getId()).get();
        item.setBidPrice(bid.getBidPrice());
        itemRepository.save(item);
        return bidRepo.save(bid);
    }

    @Override
    public void update(Long id, Bid bid) {
        if (!bidRepo.existsById(id)) {
            bidRepo.save(bid);
        } else {
            bidRepo.updateBid(bid.getBidPrice(), bid.getComment());
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
