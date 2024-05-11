package com.csys.appel.repository;

import com.csys.appel.domain.Fournisseur;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Fournisseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    @Query("select fournisseur from Fournisseur fournisseur where fournisseur.user.login = ?#{principal.username}")
    List<Fournisseur> findByUserIsCurrentUser();
}
