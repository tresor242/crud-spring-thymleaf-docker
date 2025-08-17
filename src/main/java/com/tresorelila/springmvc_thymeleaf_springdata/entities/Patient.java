package com.tresorelila.springmvc_thymeleaf_springdata.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Représente un patient dans le système médical.
 *
 * <p>Cette entité est persistée dans une base de données via JPA/Hibernate et
 * utilisée par l'application Spring MVC + Thymeleaf + Spring Data.</p>
 *
 * <p>Annotations principales :</p>
 * <ul>
 *   <li>{@link Entity} : Indique que cette classe est une entité JPA persistable.</li>
 *   <li>{@link Data} : Génère automatiquement les getters, setters, toString(),
 *       equals() et hashCode() via Lombok.</li>
 *   <li>{@link AllArgsConstructor} et {@link NoArgsConstructor} : Génèrent les
 *       constructeurs nécessaires.</li>
 * </ul>
 *
 * Exemple d’utilisation :
 * <pre>
 *     Patient patient = new Patient();
 *     patient.setNom("Dupont");
 *     patient.setDateNaissance(new Date());
 *     patient.setMalade(true);
 *     patient.setScore(85);
 * </pre>
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    /**
     * Identifiant unique du patient.
     * <p>
     * Généré automatiquement par la base de données grâce à la stratégie
     * {@link GenerationType#IDENTITY}.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom du patient.
     */
    private String nom;

    /**
     * Date de naissance du patient.
     * <p>
     * {@link TemporalType#DATE} permet de ne stocker que la date (sans l'heure).
     * {@link DateTimeFormat} permet de définir le format attendu lors de la
     * saisie ou de l'affichage dans les formulaires Thymeleaf.
     * </p>
     * Exemple : "1990-05-21"
     */
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;

    /**
     * Statut de santé du patient.
     * <p>true = malade, false = en bonne santé</p>
     */
    private boolean malade;

    /**
     * Score du patient (exemple : indicateur de santé, résultat médical, etc.).
     */
    private int score;
}
