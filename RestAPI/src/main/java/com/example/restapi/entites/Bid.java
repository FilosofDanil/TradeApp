package com.example.restapi.entites;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "bids")
public class Bid  {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bid_price", nullable = false)
    private Integer bidPrice;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private TelegramUser user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private Item item;
}
