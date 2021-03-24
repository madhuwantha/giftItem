package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.GiftOrder;
import com.mycompany.myapp.repository.GiftOrderRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.GiftOrder}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GiftOrderResource {

    private final Logger log = LoggerFactory.getLogger(GiftOrderResource.class);

    private static final String ENTITY_NAME = "giftOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GiftOrderRepository giftOrderRepository;

    public GiftOrderResource(GiftOrderRepository giftOrderRepository) {
        this.giftOrderRepository = giftOrderRepository;
    }

    /**
     * {@code POST  /gift-orders} : Create a new giftOrder.
     *
     * @param giftOrder the giftOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new giftOrder, or with status {@code 400 (Bad Request)} if the giftOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gift-orders")
    public ResponseEntity<GiftOrder> createGiftOrder(@RequestBody GiftOrder giftOrder) throws URISyntaxException {
        log.debug("REST request to save GiftOrder : {}", giftOrder);
        if (giftOrder.getId() != null) {
            throw new BadRequestAlertException("A new giftOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GiftOrder result = giftOrderRepository.save(giftOrder);
        return ResponseEntity.created(new URI("/api/gift-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gift-orders} : Updates an existing giftOrder.
     *
     * @param giftOrder the giftOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated giftOrder,
     * or with status {@code 400 (Bad Request)} if the giftOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the giftOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gift-orders")
    public ResponseEntity<GiftOrder> updateGiftOrder(@RequestBody GiftOrder giftOrder) throws URISyntaxException {
        log.debug("REST request to update GiftOrder : {}", giftOrder);
        if (giftOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GiftOrder result = giftOrderRepository.save(giftOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, giftOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gift-orders} : get all the giftOrders.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of giftOrders in body.
     */
    @GetMapping("/gift-orders")
    public List<GiftOrder> getAllGiftOrders(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all GiftOrders");
        return giftOrderRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /gift-orders/:id} : get the "id" giftOrder.
     *
     * @param id the id of the giftOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the giftOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gift-orders/{id}")
    public ResponseEntity<GiftOrder> getGiftOrder(@PathVariable Long id) {
        log.debug("REST request to get GiftOrder : {}", id);
        Optional<GiftOrder> giftOrder = giftOrderRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(giftOrder);
    }

    /**
     * {@code DELETE  /gift-orders/:id} : delete the "id" giftOrder.
     *
     * @param id the id of the giftOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gift-orders/{id}")
    public ResponseEntity<Void> deleteGiftOrder(@PathVariable Long id) {
        log.debug("REST request to delete GiftOrder : {}", id);
        giftOrderRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
