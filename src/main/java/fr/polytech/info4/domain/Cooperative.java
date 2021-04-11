package fr.polytech.info4.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cooperative.
 */
@Entity
@Table(name = "cooperative")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cooperative implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "cooperative")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Restaurant> possessions = new HashSet<>();

    @OneToMany(mappedBy = "cooperative")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Compte> members = new HashSet<>();

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

    public Cooperative name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Restaurant> getPossessions() {
        return possessions;
    }

    public Cooperative possessions(Set<Restaurant> restaurants) {
        this.possessions = restaurants;
        return this;
    }

    public Cooperative addPossessions(Restaurant restaurant) {
        this.possessions.add(restaurant);
        restaurant.setCooperative(this);
        return this;
    }

    public Cooperative removePossessions(Restaurant restaurant) {
        this.possessions.remove(restaurant);
        restaurant.setCooperative(null);
        return this;
    }

    public void setPossessions(Set<Restaurant> restaurants) {
        this.possessions = restaurants;
    }

    public Set<Compte> getMembers() {
        return members;
    }

    public Cooperative members(Set<Compte> comptes) {
        this.members = comptes;
        return this;
    }

    public Cooperative addMembers(Compte compte) {
        this.members.add(compte);
        compte.setCooperative(this);
        return this;
    }

    public Cooperative removeMembers(Compte compte) {
        this.members.remove(compte);
        compte.setCooperative(null);
        return this;
    }

    public void setMembers(Set<Compte> comptes) {
        this.members = comptes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cooperative)) {
            return false;
        }
        return id != null && id.equals(((Cooperative) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cooperative{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
