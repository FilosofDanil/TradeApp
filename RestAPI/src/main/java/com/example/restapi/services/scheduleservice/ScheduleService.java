package com.example.restapi.services.scheduleservice;

import com.example.restapi.entites.Item;
import com.example.restapi.services.crud.CRUDService;
import com.example.restapi.services.itemservice.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final CRUDService<Item> itemService;

//    @Scheduled(cron = "@hourly")
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    @Transactional
    public void checkData() {
        Date today = new Date();
        List<Item> items = itemService.getAll().stream()
                .filter(item -> !item.getExpired() && item.getExpirationDate().before(today))
                .toList();
        items.forEach(item -> {
            item.setExpired(true);
            itemService.update(item.getId(), item);
        });
    }
}
