package com.example.restapi.entites;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.Date;
import java.util.HashSet;
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

    @Column(name = "expired", nullable = false)
    private Boolean expired;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_type", referencedColumnName = "id", nullable = false)
    private ItemType itemType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private TelegramUser user;

    @JsonIgnore
    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Bid> bids = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Attachment> attachments = new HashSet<>();

    public void setBids(Set<Bid> bids) {
        this.bids.retainAll(bids);
        this.bids.addAll(bids);
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments.retainAll(attachments);
        this.attachments.addAll(attachments);
    }
}
