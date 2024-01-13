package com.example.restapi.entites;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "description")
    private String description;

    @Column(name = "start_price", nullable = false)
    private Integer startPrice;

    @Column(name = "bid_price")
    private Integer bidPrice;

    @Column(name = "placement_date", nullable = false)
    private Date placementDate;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private TelegramUser user;

    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Bid> bids;

    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Attachment> attachments;
}
