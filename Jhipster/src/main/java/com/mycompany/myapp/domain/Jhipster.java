package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Jhipster.
 */
@Entity
@Table(name = "jhipster")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Jhipster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "closed")
    private Boolean closed;

    @JsonIgnoreProperties(value = { "jhipster" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "jhipster")
    private JhipsterSetting jhipsterSetting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Jhipster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Jhipster name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getClosed() {
        return this.closed;
    }

    public Jhipster closed(Boolean closed) {
        this.setClosed(closed);
        return this;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public JhipsterSetting getJhipsterSetting() {
        return this.jhipsterSetting;
    }

    public void setJhipsterSetting(JhipsterSetting jhipsterSetting) {
        if (this.jhipsterSetting != null) {
            this.jhipsterSetting.setJhipster(null);
        }
        if (jhipsterSetting != null) {
            jhipsterSetting.setJhipster(this);
        }
        this.jhipsterSetting = jhipsterSetting;
    }

    public Jhipster jhipsterSetting(JhipsterSetting jhipsterSetting) {
        this.setJhipsterSetting(jhipsterSetting);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jhipster)) {
            return false;
        }
        return getId() != null && getId().equals(((Jhipster) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jhipster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", closed='" + getClosed() + "'" +
            "}";
    }
}
