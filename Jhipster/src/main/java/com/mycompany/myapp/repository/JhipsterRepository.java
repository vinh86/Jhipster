package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Jhipster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Jhipster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JhipsterRepository extends JpaRepository<Jhipster, Long> {}
