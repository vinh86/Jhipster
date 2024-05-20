package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.JhipsterSetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JhipsterSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JhipsterSettingRepository extends JpaRepository<JhipsterSetting, Long> {}
