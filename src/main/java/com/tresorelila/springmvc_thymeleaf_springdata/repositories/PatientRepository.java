package com.tresorelila.springmvc_thymeleaf_springdata.repositories;

import com.tresorelila.springmvc_thymeleaf_springdata.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Spring Data JPA pour la gestion des entités {@link Patient}.
 *
 * <p>Cette interface hérite de {@link JpaRepository}, ce qui fournit
 * automatiquement toutes les opérations CRUD de base (create, read, update, delete),
 * ainsi que la pagination et le tri.</p>
 *
 * <p>Exemples de méthodes héritées :</p>
 * <ul>
 *     <li>{@code findAll()} - Récupérer tous les patients</li>
 *     <li>{@code findById(Long id)} - Rechercher un patient par identifiant</li>
 *     <li>{@code save(Patient p)} - Enregistrer ou mettre à jour un patient</li>
 *     <li>{@code deleteById(Long id)} - Supprimer un patient</li>
 * </ul>
 *
 * <p>On peut également définir des méthodes personnalisées en suivant
 * la convention de nommage de Spring Data JPA.</p>
 *
 * Exemple d’utilisation dans un service :
 * <pre>
 *     @Service
 *     public class PatientService {
 *
 *         @Autowired
 *         private PatientRepository patientRepository;
 *
 *         public Page&lt;Patient&gt; searchPatients(String keyword, Pageable pageable) {
 *             return patientRepository.findByNomContains(keyword, pageable);
 *         }
 *     }
 * </pre>
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Recherche les patients dont le nom contient une sous-chaîne donnée.
     *
     * @param kw       Mot-clé recherché (partie du nom du patient).
     * @param pageable Paramètres de pagination et de tri (ex: taille de page, numéro de page).
     * @return Une page de patients correspondant au critère de recherche.
     *
     * Exemple d’appel :
     * <pre>
     *     PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("nom").ascending());
     *     Page&lt;Patient&gt; patients = patientRepository.findByNomContains("dup", pageRequest);
     * </pre>
     */
    Page<Patient> findByNomContains(String kw, Pageable pageable);
}
