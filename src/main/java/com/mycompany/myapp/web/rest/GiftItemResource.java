package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.GiftItem;
import com.mycompany.myapp.repository.GiftItemRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.GiftItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GiftItemResource {

    private final Logger log = LoggerFactory.getLogger(GiftItemResource.class);

    private static final String ENTITY_NAME = "giftItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GiftItemRepository giftItemRepository;

    public GiftItemResource(GiftItemRepository giftItemRepository) {
        this.giftItemRepository = giftItemRepository;
    }

    /**
     * {@code POST  /gift-items} : Create a new giftItem.
     *
     * @param giftItem the giftItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new giftItem, or with status {@code 400 (Bad Request)} if the giftItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gift-items")
    public ResponseEntity<GiftItem> createGiftItem(@RequestBody GiftItem giftItem) throws URISyntaxException {
        log.debug("REST request to save GiftItem : {}", giftItem);
        if (giftItem.getId() != null) {
            throw new BadRequestAlertException("A new giftItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GiftItem result = giftItemRepository.save(giftItem);
        return ResponseEntity.created(new URI("/api/gift-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gift-items} : Updates an existing giftItem.
     *
     * @param giftItem the giftItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated giftItem,
     * or with status {@code 400 (Bad Request)} if the giftItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the giftItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gift-items")
    public ResponseEntity<GiftItem> updateGiftItem(@RequestBody GiftItem giftItem) throws URISyntaxException {
        log.debug("REST request to update GiftItem : {}", giftItem);
        if (giftItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GiftItem result = giftItemRepository.save(giftItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, giftItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gift-items} : get all the giftItems.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of giftItems in body.
     */
    @GetMapping("/gift-items")
    public List<GiftItem> getAllGiftItems(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all GiftItems");
        return giftItemRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /gift-items/:id} : get the "id" giftItem.
     *
     * @param id the id of the giftItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the giftItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gift-items/{id}")
    public ResponseEntity<GiftItem> getGiftItem(@PathVariable Long id) {
        log.debug("REST request to get GiftItem : {}", id);
        Optional<GiftItem> giftItem = giftItemRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(giftItem);
    }

    /**
     * {@code DELETE  /gift-items/:id} : delete the "id" giftItem.
     *
     * @param id the id of the giftItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gift-items/{id}")
    public ResponseEntity<Void> deleteGiftItem(@PathVariable Long id) {
        log.debug("REST request to delete GiftItem : {}", id);
        giftItemRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
