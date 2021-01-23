package com.farazpardazan.cardmanagementsystem.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Hossein Baghshahi
 */
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{card.name.empty}")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "{card.number.empty}")
    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @NotNull(message = "{card.owner.empty}")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_user_id", unique = true)
    private User owner;

    @Column(name = "active")
    private boolean active = true;

    public Card() {
    }

    public Card(String name, String cardNumber, User owner, boolean active) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.owner = owner;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
