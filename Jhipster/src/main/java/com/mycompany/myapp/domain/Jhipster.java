package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(name = "setting", columnDefinition = "jsonb")
    @JdbcTypeCode(value = SqlTypes.JSON)
    private JhipsterSetting setting;

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

    public JhipsterSetting getSetting() {
        return this.setting;
    }

    public Jhipster setting(JhipsterSetting setting) {
        this.setSetting(setting);
        return this;
    }

    public Jhipster setting(String setting) {
        this.setSetting(new JhipsterSetting(setting));
        return this;
    }

    public void setSetting(JhipsterSetting setting) {
        this.setting = setting;
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
            ", setting='" + getSetting() + "'" +
            "}";
    }
}
