package com.example.restapi.entites;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.Set;

//@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "telegram_users")
public class TelegramUser  {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "tg_name", nullable = false)
    private String tgName;

    @Column(name = "tg_surname", nullable = false)
    private String tgSurname;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Item> items;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Bid> bids;

    public void setBids(Set<Bid> bids) {
        this.bids.retainAll(bids);
        this.bids.addAll(bids);
    }

    public void setItems(Set<Item> items) {
        this.items.retainAll(items);
        this.items.addAll(items);
    }
}
