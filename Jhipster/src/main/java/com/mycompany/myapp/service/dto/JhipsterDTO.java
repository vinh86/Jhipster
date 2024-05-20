package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Jhipster} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JhipsterDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean closed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JhipsterDTO)) {
            return false;
        }

        JhipsterDTO jhipsterDTO = (JhipsterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, jhipsterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JhipsterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", closed='" + getClosed() + "'" +
            "}";
    }
}
