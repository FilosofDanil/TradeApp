package com.example.restapi.services.bidservice;

import com.example.restapi.dtos.BidDTO;
import org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration;

import java.util.List;

public interface BidService {
    List<BidDTO> getByItem(Long itemId);

    List<BidDTO> getByUser(Long userId);
}
