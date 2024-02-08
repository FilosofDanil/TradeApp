package com.example.restapi;

import com.example.restapi.entites.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class TestData {
    public final static List<TelegramUser> users =
            List.of(TelegramUser.builder()
                    .id(1L)
                    .username("few")
                    .chatId(12312L)
                    .tgName("name")
                    .tgSurname("surname")
                    .build(), TelegramUser.builder()
                    .id(2L)
                    .username("few")
                    .chatId(12312L)
                    .tgName("name")
                    .tgSurname("surname")
                    .build());

    public final static List<Settings> settings =
            List.of(Settings.builder()
                            .id(1L)
                            .user(users.get(0))
                            .city("Київ")
                            .build(),
                    Settings
                            .builder()
                            .id(2L)
                            .user(users.get(1))
                            .city("Вся Україна")
                            .build());

    public final static List<ItemType> itemTypes =
            List.of(ItemType.builder()
                    .id(1L)
                    .name("Авто")
                    .build());

    public final static List<Item> items =
            List.of(Item.builder()
                    .id(1L)
                    .user(users.get(0))
                    .bidPrice(100)
                    .startPrice(70)
                    .itemName("name1")
                    .description("description1")
                    .itemType(itemTypes.get(0))
                    .expired(false)
                    .build(), Item.builder()
                    .id(2L)
                    .user(users.get(1))
                    .bidPrice(250)
                    .startPrice(150)
                    .itemName("name2")
                    .description("description2")
                    .itemType(itemTypes.get(0))
                    .expired(true)
                    .build());

    public final static List<Bid> bids =
            List.of(Bid.builder()
                    .id(1L)
                    .user(users.get(1))
                    .item(items.get(0))
                    .comment("comment1")
                    .bidPrice(100)
                    .build(), Bid.builder()
                    .id(2L)
                    .user(users.get(0))
                    .item(items.get(1))
                    .comment("comment2")
                    .bidPrice(250)
                    .build());

    public final static List<Attachment> attachments =
            List.of(Attachment.builder()
                    .id(1L)
                    .item(items.get(0))
                    .itemData("data1")
                    .itemType("img")
                    .build(), Attachment.builder()
                    .item(items.get(1))
                    .itemData("data2")
                    .itemType("img")
                    .build());
}
