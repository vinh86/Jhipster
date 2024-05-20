package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.JhipsterSetting} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JhipsterSettingDTO implements Serializable {

    private Long id;

    private String theme;

    private Integer pageSize;

    private String others;

    @NotNull
    private JhipsterDTO jhipster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public JhipsterDTO getJhipster() {
        return jhipster;
    }

    public void setJhipster(JhipsterDTO jhipster) {
        this.jhipster = jhipster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JhipsterSettingDTO)) {
            return false;
        }

        JhipsterSettingDTO jhipsterSettingDTO = (JhipsterSettingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, jhipsterSettingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JhipsterSettingDTO{" +
            "id=" + getId() +
            ", theme='" + getTheme() + "'" +
            ", pageSize=" + getPageSize() +
            ", others='" + getOthers() + "'" +
            ", jhipster=" + getJhipster() +
            "}";
    }
}
