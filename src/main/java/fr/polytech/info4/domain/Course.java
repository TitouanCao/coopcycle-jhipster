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
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "time_required", nullable = false)
    private Integer timeRequired;

    @OneToOne
    @JoinColumn(unique = true)
    private Panier order;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "course_agents",
               joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "agents_id", referencedColumnName = "id"))
    private Set<Compte> agents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTimeRequired() {
        return timeRequired;
    }

    public Course timeRequired(Integer timeRequired) {
        this.timeRequired = timeRequired;
        return this;
    }

    public void setTimeRequired(Integer timeRequired) {
        this.timeRequired = timeRequired;
    }

    public Panier getOrder() {
        return order;
    }

    public Course order(Panier panier) {
        this.order = panier;
        return this;
    }

    public void setOrder(Panier panier) {
        this.order = panier;
    }

    public Set<Compte> getAgents() {
        return agents;
    }

    public Course agents(Set<Compte> comptes) {
        this.agents = comptes;
        return this;
    }

    public Course addAgents(Compte compte) {
        this.agents.add(compte);
        compte.getCourses().add(this);
        return this;
    }

    public Course removeAgents(Compte compte) {
        this.agents.remove(compte);
        compte.getCourses().remove(this);
        return this;
    }

    public void setAgents(Set<Compte> comptes) {
        this.agents = comptes;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Course restaurant(Restaurant restaurant) {
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
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", timeRequired=" + getTimeRequired() +
            "}";
    }
}
