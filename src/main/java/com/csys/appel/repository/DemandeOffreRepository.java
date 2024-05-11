package com.csys.appel.repository;

import com.csys.appel.domain.DemandeOffre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DemandeOffre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeOffreRepository extends JpaRepository<DemandeOffre, Long> {}
