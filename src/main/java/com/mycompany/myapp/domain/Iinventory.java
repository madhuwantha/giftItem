package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Iinventory.
 */
@Entity
@Table(name = "iinventory")
public class Iinventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avalible_quantity")
    private Integer avalibleQuantity;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAvalibleQuantity() {
        return avalibleQuantity;
    }

    public Iinventory avalibleQuantity(Integer avalibleQuantity) {
        this.avalibleQuantity = avalibleQuantity;
        return this;
    }

    public void setAvalibleQuantity(Integer avalibleQuantity) {
        this.avalibleQuantity = avalibleQuantity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Iinventory)) {
            return false;
        }
        return id != null && id.equals(((Iinventory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Iinventory{" +
            "id=" + getId() +
            ", avalibleQuantity=" + getAvalibleQuantity() +
            "}";
    }
}
