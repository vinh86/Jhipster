package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JhipsterSetting.
 */
@Entity
@Table(name = "jhipster_setting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JhipsterSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "theme")
    private String theme;

    @Column(name = "page_size")
    private Integer pageSize;

    @Column(name = "others")
    private String others;

    @JsonIgnoreProperties(value = { "jhipsterSetting" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Jhipster jhipster;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JhipsterSetting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return this.theme;
    }

    public JhipsterSetting theme(String theme) {
        this.setTheme(theme);
        return this;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public JhipsterSetting pageSize(Integer pageSize) {
        this.setPageSize(pageSize);
        return this;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOthers() {
        return this.others;
    }

    public JhipsterSetting others(String others) {
        this.setOthers(others);
        return this;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public Jhipster getJhipster() {
        return this.jhipster;
    }

    public void setJhipster(Jhipster jhipster) {
        this.jhipster = jhipster;
    }

    public JhipsterSetting jhipster(Jhipster jhipster) {
        this.setJhipster(jhipster);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JhipsterSetting)) {
            return false;
        }
        return getId() != null && getId().equals(((JhipsterSetting) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JhipsterSetting{" +
            "id=" + getId() +
            ", theme='" + getTheme() + "'" +
            ", pageSize=" + getPageSize() +
            ", others='" + getOthers() + "'" +
            "}";
    }
}
