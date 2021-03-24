package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cart.
 */
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripption")
    private String descripption;

    @ManyToMany(mappedBy = "carts")
    @JsonIgnore
    private Set<GiftItem> giftItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripption() {
        return descripption;
    }

    public Cart descripption(String descripption) {
        this.descripption = descripption;
        return this;
    }

    public void setDescripption(String descripption) {
        this.descripption = descripption;
    }

    public Set<GiftItem> getGiftItems() {
        return giftItems;
    }

    public Cart giftItems(Set<GiftItem> giftItems) {
        this.giftItems = giftItems;
        return this;
    }

    public Cart addGiftItems(GiftItem giftItem) {
        this.giftItems.add(giftItem);
        giftItem.getCarts().add(this);
        return this;
    }

    public Cart removeGiftItems(GiftItem giftItem) {
        this.giftItems.remove(giftItem);
        giftItem.getCarts().remove(this);
        return this;
    }

    public void setGiftItems(Set<GiftItem> giftItems) {
        this.giftItems = giftItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cart)) {
            return false;
        }
        return id != null && id.equals(((Cart) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cart{" +
            "id=" + getId() +
            ", descripption='" + getDescripption() + "'" +
            "}";
    }
}
