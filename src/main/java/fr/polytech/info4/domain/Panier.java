package fr.polytech.info4.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Panier.
 */
@Entity
@Table(name = "panier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Panier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nb_elements", nullable = false)
    private Integer nbElements;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "panier_content",
               joinColumns = @JoinColumn(name = "panier_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "content_id", referencedColumnName = "id"))
    private Set<Produit> contents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "carts", allowSetters = true)
    private Compte compte;

    @ManyToOne
    @JsonIgnoreProperties(value = "carts", allowSetters = true)
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNbElements() {
        return nbElements;
    }

    public Panier nbElements(Integer nbElements) {
        this.nbElements = nbElements;
        return this;
    }

    public void setNbElements(Integer nbElements) {
        this.nbElements = nbElements;
    }

    public Integer getPrice() {
        return price;
    }

    public Panier price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Set<Produit> getContents() {
        return contents;
    }

    public Panier contents(Set<Produit> produits) {
        this.contents = produits;
        return this;
    }

    public Panier addContent(Produit produit) {
        this.contents.add(produit);
        produit.getCarts().add(this);
        return this;
    }

    public Panier removeContent(Produit produit) {
        this.contents.remove(produit);
        produit.getCarts().remove(this);
        return this;
    }

    public void setContents(Set<Produit> produits) {
        this.contents = produits;
    }

    public Compte getCompte() {
        return compte;
    }

    public Panier compte(Compte compte) {
        this.compte = compte;
        return this;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Panier restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Panier)) {
            return false;
        }
        return id != null && id.equals(((Panier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Panier{" +
            "id=" + getId() +
            ", nbElements=" + getNbElements() +
            ", price=" + getPrice() +
            "}";
    }
}
