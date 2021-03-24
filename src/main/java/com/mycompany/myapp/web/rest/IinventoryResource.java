package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Iinventory;
import com.mycompany.myapp.repository.IinventoryRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Iinventory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IinventoryResource {

    private final Logger log = LoggerFactory.getLogger(IinventoryResource.class);

    private static final String ENTITY_NAME = "iinventory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IinventoryRepository iinventoryRepository;

    public IinventoryResource(IinventoryRepository iinventoryRepository) {
        this.iinventoryRepository = iinventoryRepository;
    }

    /**
     * {@code POST  /iinventories} : Create a new iinventory.
     *
     * @param iinventory the iinventory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new iinventory, or with status {@code 400 (Bad Request)} if the iinventory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/iinventories")
    public ResponseEntity<Iinventory> createIinventory(@RequestBody Iinventory iinventory) throws URISyntaxException {
        log.debug("REST request to save Iinventory : {}", iinventory);
        if (iinventory.getId() != null) {
            throw new BadRequestAlertException("A new iinventory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Iinventory result = iinventoryRepository.save(iinventory);
        return ResponseEntity.created(new URI("/api/iinventories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /iinventories} : Updates an existing iinventory.
     *
     * @param iinventory the iinventory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated iinventory,
     * or with status {@code 400 (Bad Request)} if the iinventory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the iinventory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/iinventories")
    public ResponseEntity<Iinventory> updateIinventory(@RequestBody Iinventory iinventory) throws URISyntaxException {
        log.debug("REST request to update Iinventory : {}", iinventory);
        if (iinventory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Iinventory result = iinventoryRepository.save(iinventory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, iinventory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /iinventories} : get all the iinventories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of iinventories in body.
     */
    @GetMapping("/iinventories")
    public List<Iinventory> getAllIinventories() {
        log.debug("REST request to get all Iinventories");
        return iinventoryRepository.findAll();
    }

    /**
     * {@code GET  /iinventories/:id} : get the "id" iinventory.
     *
     * @param id the id of the iinventory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the iinventory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/iinventories/{id}")
    public ResponseEntity<Iinventory> getIinventory(@PathVariable Long id) {
        log.debug("REST request to get Iinventory : {}", id);
        Optional<Iinventory> iinventory = iinventoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(iinventory);
    }

    /**
     * {@code DELETE  /iinventories/:id} : delete the "id" iinventory.
     *
     * @param id the id of the iinventory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/iinventories/{id}")
    public ResponseEntity<Void> deleteIinventory(@PathVariable Long id) {
        log.debug("REST request to delete Iinventory : {}", id);
        iinventoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
