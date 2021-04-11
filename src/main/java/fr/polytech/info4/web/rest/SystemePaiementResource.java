package fr.polytech.info4.web.rest;

import fr.polytech.info4.domain.SystemePaiement;
import fr.polytech.info4.repository.SystemePaiementRepository;
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
 * REST controller for managing {@link fr.polytech.info4.domain.SystemePaiement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SystemePaiementResource {

    private final Logger log = LoggerFactory.getLogger(SystemePaiementResource.class);

    private static final String ENTITY_NAME = "systemePaiement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemePaiementRepository systemePaiementRepository;

    public SystemePaiementResource(SystemePaiementRepository systemePaiementRepository) {
        this.systemePaiementRepository = systemePaiementRepository;
    }

    /**
     * {@code POST  /systeme-paiements} : Create a new systemePaiement.
     *
     * @param systemePaiement the systemePaiement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemePaiement, or with status {@code 400 (Bad Request)} if the systemePaiement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/systeme-paiements")
    public ResponseEntity<SystemePaiement> createSystemePaiement(@Valid @RequestBody SystemePaiement systemePaiement) throws URISyntaxException {
        log.debug("REST request to save SystemePaiement : {}", systemePaiement);
        if (systemePaiement.getId() != null) {
            throw new BadRequestAlertException("A new systemePaiement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemePaiement result = systemePaiementRepository.save(systemePaiement);
        return ResponseEntity.created(new URI("/api/systeme-paiements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /systeme-paiements} : Updates an existing systemePaiement.
     *
     * @param systemePaiement the systemePaiement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemePaiement,
     * or with status {@code 400 (Bad Request)} if the systemePaiement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemePaiement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/systeme-paiements")
    public ResponseEntity<SystemePaiement> updateSystemePaiement(@Valid @RequestBody SystemePaiement systemePaiement) throws URISyntaxException {
        log.debug("REST request to update SystemePaiement : {}", systemePaiement);
        if (systemePaiement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemePaiement result = systemePaiementRepository.save(systemePaiement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemePaiement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /systeme-paiements} : get all the systemePaiements.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemePaiements in body.
     */
    @GetMapping("/systeme-paiements")
    public List<SystemePaiement> getAllSystemePaiements(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all SystemePaiements");
        return systemePaiementRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /systeme-paiements/:id} : get the "id" systemePaiement.
     *
     * @param id the id of the systemePaiement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemePaiement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/systeme-paiements/{id}")
    public ResponseEntity<SystemePaiement> getSystemePaiement(@PathVariable Long id) {
        log.debug("REST request to get SystemePaiement : {}", id);
        Optional<SystemePaiement> systemePaiement = systemePaiementRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(systemePaiement);
    }

    /**
     * {@code DELETE  /systeme-paiements/:id} : delete the "id" systemePaiement.
     *
     * @param id the id of the systemePaiement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/systeme-paiements/{id}")
    public ResponseEntity<Void> deleteSystemePaiement(@PathVariable Long id) {
        log.debug("REST request to delete SystemePaiement : {}", id);
        systemePaiementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
