package com.example.tradeapp.components.impl;

import com.example.tradeapp.client.BidClient;
import com.example.tradeapp.client.ItemClient;
import com.example.tradeapp.client.UserClient;
import com.example.tradeapp.components.BidListFormer;
import com.example.tradeapp.entities.models.Bids;
import com.example.tradeapp.entities.models.Items;
import com.example.tradeapp.entities.models.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BidListFormerImpl implements BidListFormer {
    private final BidClient bidClient;

    private final UserClient userClient;

    private final ItemClient itemClient;

    @Override
    public String fromResponseBidList(Long itemId) {
        List<Bids> bids = bidClient.getAllBidsByItem(itemId);
        return formResultString(bids);
    }

    @Override
    public String fromResponseBidList(Long itemId, Long userId) {
        List<Bids> bids = bidClient.getAllBidsByUser(userId)
                .stream()
                .filter(bid -> bid.getItemId().equals(itemId))
                .toList();
        return formResultString(bids);
    }

    @Override
    public String formTopPriceBidString(Long itemId) {
        List<Bids> bids = bidClient.getAllBidsByItem(itemId);
        if (bids.isEmpty()){
            return "За даним товаром поки що немає ставок";
        }
        int price = 0;
        Bids biggestBid = bids.get(0);
        for (Bids bid: bids){
            if(bid.getBidPrice() > price){
                biggestBid = bid;
                price = bid.getBidPrice();
            }
        }
        return formStringFromBid(biggestBid);
    }

    @Override
    public List<Items> getAllRelatedItems(String username) {
        return itemClient.getAllItemsByUserHavingBids(username);
    }

    private String formResultString(List<Bids> bids){
        StringBuilder stringBuilder = new StringBuilder();
        for (Bids bid : bids) {
            stringBuilder.append(formStringFromBid(bid));
        }
        return stringBuilder.toString();
    }

    private String formStringFromBid(Bids bid){
        Users user = userClient.getUserById(bid.getUserId());
        String resultStr = user.getTgName() + ": " +
                "\n" +
                "Запропонована ціна: " + bid.getBidPrice() +
                "\nusername: @" + user.getUsername() +
                "\nКоментар: " + bid.getComment() +
                "\n\n";
        return resultStr;
    }
}
