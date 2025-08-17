package com.tresorelila.springmvc_thymeleaf_springdata.web;

import com.tresorelila.springmvc_thymeleaf_springdata.entities.Patient;
import com.tresorelila.springmvc_thymeleaf_springdata.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Contrôleur Spring MVC pour la gestion des patients.
 *
 * <p>Ce contrôleur gère les opérations CRUD (Create, Read, Update, Delete) sur les entités
 * {@link Patient}, et fournit les données nécessaires aux vues Thymeleaf.</p>
 *
 * <p>Les URL exposées permettent :</p>
 * <ul>
 *   <li>Afficher une liste paginée et filtrée de patients</li>
 *   <li>Ajouter un patient via un formulaire (modal)</li>
 *   <li>Mettre à jour un patient existant</li>
 *   <li>Supprimer un patient</li>
 * </ul>
 */
@Controller
@AllArgsConstructor
public class PatientController {

    /** Repository permettant l’accès aux données Patient. */
    private final PatientRepository patientRepository;

    /**
     * Affiche la liste paginée des patients avec possibilité de recherche par mot-clé.
     *
     * @param model   Conteneur de données pour la vue Thymeleaf.
     * @param page    Numéro de la page (par défaut = 0).
     * @param size    Nombre d’éléments par page (par défaut = 5).
     * @param keyword Mot-clé pour filtrer les patients par nom.
     * @return Le template Thymeleaf "patients".
     */
    @GetMapping(path = "/index")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        // Recherche avec pagination
        Page<Patient> pagePatients =
                patientRepository.findByNomContains(keyword, PageRequest.of(page, size));

        // Données envoyées à la vue
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        // Objet vide utilisé par le formulaire modal (ajout)
        model.addAttribute("newPatient", new Patient());

        return "patients";
    }

    /**
     * Sauvegarde un nouveau patient (depuis un formulaire modal).
     *
     * @param patient Patient soumis via formulaire.
     * @param keyword Filtre de recherche courant (pour revenir au même état de la liste).
     * @param page    Page courante.
     * @param size    Taille de page courante.
     * @param ra      Permet d’envoyer des messages flash (succès/erreurs).
     * @return Redirection vers la liste des patients.
     */
    @PostMapping("/patients/save")
    public String savePatient(@ModelAttribute("newPatient") Patient patient,
                              @RequestParam(name="keyword", defaultValue="") String keyword,
                              @RequestParam(name="page", defaultValue="0") int page,
                              @RequestParam(name="size", defaultValue="5") int size,
                              RedirectAttributes ra) {
        patientRepository.save(patient);
        ra.addFlashAttribute("success", "Patient ajouté.");
        return "redirect:/index?page=" + page + "&size=" + size + "&keyword=" + keyword;
    }

    /**
     * Supprime un patient par son identifiant.
     *
     * @param id      Identifiant du patient à supprimer.
     * @param keyword Filtre courant (conserve la recherche après suppression).
     * @param page    Numéro de la page en cours.
     * @return Redirection vers la liste des patients.
     */
    @GetMapping("/delete")
    public String delete(Long id, String keyword, int page) {
        patientRepository.deleteById(id);
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    /**
     * Redirection de la racine vers la page principale.
     *
     * @return Redirection vers "/index".
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    /**
     * Met à jour les informations d’un patient existant.
     *
     * @param id      Identifiant du patient à mettre à jour.
     * @param form    Données envoyées par le formulaire (Patient).
     * @param keyword Mot-clé courant pour la recherche.
     * @param page    Numéro de la page en cours.
     * @param size    Taille de page.
     * @return Redirection vers la liste des patients.
     */
    @PostMapping("/patients/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Patient form,
                         @RequestParam(defaultValue="") String keyword,
                         @RequestParam(defaultValue="0") int page,
                         @RequestParam(defaultValue="5") int size) {
        form.setId(id);
        patientRepository.save(form);
        return "redirect:/index?page="+page+"&size="+size+"&keyword="+keyword;
    }
}
