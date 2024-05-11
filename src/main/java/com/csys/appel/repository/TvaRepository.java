package com.csys.appel.repository;

import com.csys.appel.domain.Tva;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TvaRepository extends JpaRepository<Tva, Long> {}
