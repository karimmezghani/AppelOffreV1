package com.csys.appel.repository;

import com.csys.appel.domain.AppelOffre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AppelOffre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppelOffreRepository extends JpaRepository<AppelOffre, Long> {}
