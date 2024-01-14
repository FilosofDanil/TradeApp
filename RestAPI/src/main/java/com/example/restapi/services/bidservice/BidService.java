package com.example.restapi.services.bidservice;

import com.example.restapi.dtos.BidDTO;
import com.example.restapi.entites.Bid;
import org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration;

import java.util.List;

public interface BidService {
    List<Bid> getByItem(Long itemId);

    List<Bid> getByUser(Long userId);
}
