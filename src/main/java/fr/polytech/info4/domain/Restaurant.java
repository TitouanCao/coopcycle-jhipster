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
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "adress", nullable = false)
    private String adress;

    @OneToOne
    @JoinColumn(unique = true)
    private Compte owned;

    @OneToMany(mappedBy = "restaurant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Produit> products = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Course> orders = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Panier> carts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "possessions", allowSetters = true)
    private Cooperative cooperative;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Restaurant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public Restaurant adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Compte getOwned() {
        return owned;
    }

    public Restaurant owned(Compte compte) {
        this.owned = compte;
        return this;
    }

    public void setOwned(Compte compte) {
        this.owned = compte;
    }

    public Set<Produit> getProducts() {
        return products;
    }

    public Restaurant products(Set<Produit> produits) {
        this.products = produits;
        return this;
    }

    public Restaurant addProducts(Produit produit) {
        this.products.add(produit);
        produit.setRestaurant(this);
        return this;
    }

    public Restaurant removeProducts(Produit produit) {
        this.products.remove(produit);
        produit.setRestaurant(null);
        return this;
    }

    public void setProducts(Set<Produit> produits) {
        this.products = produits;
    }

    public Set<Course> getOrders() {
        return orders;
    }

    public Restaurant orders(Set<Course> courses) {
        this.orders = courses;
        return this;
    }

    public Restaurant addOrders(Course course) {
        this.orders.add(course);
        course.setRestaurant(this);
        return this;
    }

    public Restaurant removeOrders(Course course) {
        this.orders.remove(course);
        course.setRestaurant(null);
        return this;
    }

    public void setOrders(Set<Course> courses) {
        this.orders = courses;
    }

    public Set<Panier> getCarts() {
        return carts;
    }

    public Restaurant carts(Set<Panier> paniers) {
        this.carts = paniers;
        return this;
    }

    public Restaurant addCarts(Panier panier) {
        this.carts.add(panier);
        panier.setRestaurant(this);
        return this;
    }

    public Restaurant removeCarts(Panier panier) {
        this.carts.remove(panier);
        panier.setRestaurant(null);
        return this;
    }

    public void setCarts(Set<Panier> paniers) {
        this.carts = paniers;
    }

    public Cooperative getCooperative() {
        return cooperative;
    }

    public Restaurant cooperative(Cooperative cooperative) {
        this.cooperative = cooperative;
        return this;
    }

    public void setCooperative(Cooperative cooperative) {
        this.cooperative = cooperative;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        return id != null && id.equals(((Restaurant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", adress='" + getAdress() + "'" +
            "}";
    }
}
