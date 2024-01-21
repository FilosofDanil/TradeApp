package com.example.restapi.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "settings")
public class Settings {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", nullable = false)
    private String city;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private TelegramUser user;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "categories",
            joinColumns = {@JoinColumn(name = "settings_id")},
            inverseJoinColumns = {@JoinColumn(name = "itemtype_id")}
    )
    Set<ItemType> categories = new HashSet<>();

    public void setItemTypes(Set<ItemType> itemTypes) {
        this.categories.retainAll(itemTypes);
        this.categories.addAll(itemTypes);
    }
}
