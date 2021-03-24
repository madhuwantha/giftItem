package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A GiftItem.
 */
@Entity
@Table(name = "gift_item")
public class GiftItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gift_name")
    private String giftName;

    @Column(name = "descripption")
    private String descripption;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "avalible_quantity")
    private Integer avalibleQuantity;

    @ManyToOne
    @JsonIgnoreProperties(value = "giftItems", allowSetters = true)
    private Category category;

    @ManyToMany(mappedBy = "giftItems")
    @JsonIgnore
    private Set<Cart> carts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGiftName() {
        return giftName;
    }

    public GiftItem giftName(String giftName) {
        this.giftName = giftName;
        return this;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getDescripption() {
        return descripption;
    }

    public GiftItem descripption(String descripption) {
        this.descripption = descripption;
        return this;
    }

    public void setDescripption(String descripption) {
        this.descripption = descripption;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public GiftItem unitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getAvalibleQuantity() {
        return avalibleQuantity;
    }

    public GiftItem avalibleQuantity(Integer avalibleQuantity) {
        this.avalibleQuantity = avalibleQuantity;
        return this;
    }

    public void setAvalibleQuantity(Integer avalibleQuantity) {
        this.avalibleQuantity = avalibleQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public GiftItem category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public GiftItem carts(Set<Cart> carts) {
        this.carts = carts;
        return this;
    }

    public GiftItem addCart(Cart cart) {
        this.carts.add(cart);
        cart.getGiftItems().add(this);
        return this;
    }

    public GiftItem removeCart(Cart cart) {
        this.carts.remove(cart);
        cart.getGiftItems().remove(this);
        return this;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GiftItem)) {
            return false;
        }
        return id != null && id.equals(((GiftItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GiftItem{" +
            "id=" + getId() +
            ", giftName='" + getGiftName() + "'" +
            ", descripption='" + getDescripption() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", avalibleQuantity=" + getAvalibleQuantity() +
            "}";
    }
}
