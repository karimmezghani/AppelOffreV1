package com.csys.appel.repository;

import com.csys.appel.domain.Compteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Compteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteurRepository extends JpaRepository<Compteur, Long> {}
