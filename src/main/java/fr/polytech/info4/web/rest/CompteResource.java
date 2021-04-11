package fr.polytech.info4.web.rest;

import fr.polytech.info4.domain.Compte;
import fr.polytech.info4.repository.CompteRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link fr.polytech.info4.domain.Compte}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CompteResource {

    private final Logger log = LoggerFactory.getLogger(CompteResource.class);

    private static final String ENTITY_NAME = "compte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompteRepository compteRepository;

    public CompteResource(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    /**
     * {@code POST  /comptes} : Create a new compte.
     *
     * @param compte the compte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compte, or with status {@code 400 (Bad Request)} if the compte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comptes")
    public ResponseEntity<Compte> createCompte(@Valid @RequestBody Compte compte) throws URISyntaxException {
        log.debug("REST request to save Compte : {}", compte);
        if (compte.getId() != null) {
            throw new BadRequestAlertException("A new compte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Compte result = compteRepository.save(compte);
        return ResponseEntity.created(new URI("/api/comptes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comptes} : Updates an existing compte.
     *
     * @param compte the compte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compte,
     * or with status {@code 400 (Bad Request)} if the compte is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comptes")
    public ResponseEntity<Compte> updateCompte(@Valid @RequestBody Compte compte) throws URISyntaxException {
        log.debug("REST request to update Compte : {}", compte);
        if (compte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Compte result = compteRepository.save(compte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compte.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comptes} : get all the comptes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comptes in body.
     */
    @GetMapping("/comptes")
    public List<Compte> getAllComptes(@RequestParam(required = false) String filter) {
        if ("owns-is-null".equals(filter)) {
            log.debug("REST request to get all Comptes where owns is null");
            return StreamSupport
                .stream(compteRepository.findAll().spliterator(), false)
                .filter(compte -> compte.getOwns() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Comptes");
        return compteRepository.findAll();
    }

    /**
     * {@code GET  /comptes/:id} : get the "id" compte.
     *
     * @param id the id of the compte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comptes/{id}")
    public ResponseEntity<Compte> getCompte(@PathVariable Long id) {
        log.debug("REST request to get Compte : {}", id);
        Optional<Compte> compte = compteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(compte);
    }

    /**
     * {@code DELETE  /comptes/:id} : delete the "id" compte.
     *
     * @param id the id of the compte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comptes/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        log.debug("REST request to delete Compte : {}", id);
        compteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
