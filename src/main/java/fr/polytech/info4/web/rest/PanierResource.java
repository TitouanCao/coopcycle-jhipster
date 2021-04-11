package fr.polytech.info4.web.rest;

import fr.polytech.info4.domain.Panier;
import fr.polytech.info4.repository.PanierRepository;
import fr.polytech.info4.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.polytech.info4.domain.Panier}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PanierResource {

    private final Logger log = LoggerFactory.getLogger(PanierResource.class);

    private static final String ENTITY_NAME = "panier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PanierRepository panierRepository;

    public PanierResource(PanierRepository panierRepository) {
        this.panierRepository = panierRepository;
    }

    /**
     * {@code POST  /paniers} : Create a new panier.
     *
     * @param panier the panier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new panier, or with status {@code 400 (Bad Request)} if the panier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paniers")
    public ResponseEntity<Panier> createPanier(@Valid @RequestBody Panier panier) throws URISyntaxException {
        log.debug("REST request to save Panier : {}", panier);
        if (panier.getId() != null) {
            throw new BadRequestAlertException("A new panier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Panier result = panierRepository.save(panier);
        return ResponseEntity.created(new URI("/api/paniers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paniers} : Updates an existing panier.
     *
     * @param panier the panier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated panier,
     * or with status {@code 400 (Bad Request)} if the panier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the panier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paniers")
    public ResponseEntity<Panier> updatePanier(@Valid @RequestBody Panier panier) throws URISyntaxException {
        log.debug("REST request to update Panier : {}", panier);
        if (panier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Panier result = panierRepository.save(panier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, panier.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paniers} : get all the paniers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paniers in body.
     */
    @GetMapping("/paniers")
    public List<Panier> getAllPaniers(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Paniers");
        return panierRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /paniers/:id} : get the "id" panier.
     *
     * @param id the id of the panier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the panier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paniers/{id}")
    public ResponseEntity<Panier> getPanier(@PathVariable Long id) {
        log.debug("REST request to get Panier : {}", id);
        Optional<Panier> panier = panierRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(panier);
    }

    /**
     * {@code DELETE  /paniers/:id} : delete the "id" panier.
     *
     * @param id the id of the panier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paniers/{id}")
    public ResponseEntity<Void> deletePanier(@PathVariable Long id) {
        log.debug("REST request to delete Panier : {}", id);
        panierRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
