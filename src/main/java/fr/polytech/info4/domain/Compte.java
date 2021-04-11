package fr.polytech.info4.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Compte.
 */
@Entity
@Table(name = "compte")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @Min(value = 0)
    @Max(value = 120)
    @Column(name = "age")
    private Integer age;

    @NotNull
    @Column(name = "adress", nullable = false)
    private String adress;

    @OneToMany(mappedBy = "compte")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Panier> carts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "comptes", allowSetters = true)
    private Roles roles;

    @OneToOne(mappedBy = "owned")
    @JsonIgnore
    private Restaurant owns;

    @ManyToOne
    @JsonIgnoreProperties(value = "members", allowSetters = true)
    private Cooperative cooperative;

    @ManyToMany(mappedBy = "agents")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<SystemePaiement> operations = new HashSet<>();

    @ManyToMany(mappedBy = "agents")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

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

    public Compte name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Compte surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public Compte age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAdress() {
        return adress;
    }

    public Compte adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Set<Panier> getCarts() {
        return carts;
    }

    public Compte carts(Set<Panier> paniers) {
        this.carts = paniers;
        return this;
    }

    public Compte addCarts(Panier panier) {
        this.carts.add(panier);
        panier.setCompte(this);
        return this;
    }

    public Compte removeCarts(Panier panier) {
        this.carts.remove(panier);
        panier.setCompte(null);
        return this;
    }

    public void setCarts(Set<Panier> paniers) {
        this.carts = paniers;
    }

    public Roles getRoles() {
        return roles;
    }

    public Compte roles(Roles roles) {
        this.roles = roles;
        return this;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Restaurant getOwns() {
        return owns;
    }

    public Compte owns(Restaurant restaurant) {
        this.owns = restaurant;
        return this;
    }

    public void setOwns(Restaurant restaurant) {
        this.owns = restaurant;
    }

    public Cooperative getCooperative() {
        return cooperative;
    }

    public Compte cooperative(Cooperative cooperative) {
        this.cooperative = cooperative;
        return this;
    }

    public void setCooperative(Cooperative cooperative) {
        this.cooperative = cooperative;
    }

    public Set<SystemePaiement> getOperations() {
        return operations;
    }

    public Compte operations(Set<SystemePaiement> systemePaiements) {
        this.operations = systemePaiements;
        return this;
    }

    public Compte addOperations(SystemePaiement systemePaiement) {
        this.operations.add(systemePaiement);
        systemePaiement.getAgents().add(this);
        return this;
    }

    public Compte removeOperations(SystemePaiement systemePaiement) {
        this.operations.remove(systemePaiement);
        systemePaiement.getAgents().remove(this);
        return this;
    }

    public void setOperations(Set<SystemePaiement> systemePaiements) {
        this.operations = systemePaiements;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public Compte courses(Set<Course> courses) {
        this.courses = courses;
        return this;
    }

    public Compte addCourses(Course course) {
        this.courses.add(course);
        course.getAgents().add(this);
        return this;
    }

    public Compte removeCourses(Course course) {
        this.courses.remove(course);
        course.getAgents().remove(this);
        return this;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compte)) {
            return false;
        }
        return id != null && id.equals(((Compte) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Compte{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", age=" + getAge() +
            ", adress='" + getAdress() + "'" +
            "}";
    }
}
