package fr.polytech.info4.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SystemePaiement.
 */
@Entity
@Table(name = "systeme_paiement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemePaiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "method", nullable = false)
    private String method;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "systeme_paiement_agents",
               joinColumns = @JoinColumn(name = "systeme_paiement_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "agents_id", referencedColumnName = "id"))
    private Set<Compte> agents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public SystemePaiement method(String method) {
        this.method = method;
        return this;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Set<Compte> getAgents() {
        return agents;
    }

    public SystemePaiement agents(Set<Compte> comptes) {
        this.agents = comptes;
        return this;
    }

    public SystemePaiement addAgents(Compte compte) {
        this.agents.add(compte);
        compte.getOperations().add(this);
        return this;
    }

    public SystemePaiement removeAgents(Compte compte) {
        this.agents.remove(compte);
        compte.getOperations().remove(this);
        return this;
    }

    public void setAgents(Set<Compte> comptes) {
        this.agents = comptes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemePaiement)) {
            return false;
        }
        return id != null && id.equals(((SystemePaiement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemePaiement{" +
            "id=" + getId() +
            ", method='" + getMethod() + "'" +
            "}";
    }
}
