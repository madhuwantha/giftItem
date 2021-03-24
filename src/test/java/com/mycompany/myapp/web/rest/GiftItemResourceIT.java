package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.GiftItemApp;
import com.mycompany.myapp.domain.GiftItem;
import com.mycompany.myapp.repository.GiftItemRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GiftItemResource} REST controller.
 */
@SpringBootTest(classes = GiftItemApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GiftItemResourceIT {

    private static final String DEFAULT_GIFT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GIFT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_UNIT_PRICE = 1D;
    private static final Double UPDATED_UNIT_PRICE = 2D;

    @Autowired
    private GiftItemRepository giftItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGiftItemMockMvc;

    private GiftItem giftItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GiftItem createEntity(EntityManager em) {
        GiftItem giftItem = new GiftItem()
            .giftName(DEFAULT_GIFT_NAME)
            .descripption(DEFAULT_DESCRIPPTION)
            .unitPrice(DEFAULT_UNIT_PRICE);
        return giftItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GiftItem createUpdatedEntity(EntityManager em) {
        GiftItem giftItem = new GiftItem()
            .giftName(UPDATED_GIFT_NAME)
            .descripption(UPDATED_DESCRIPPTION)
            .unitPrice(UPDATED_UNIT_PRICE);
        return giftItem;
    }

    @BeforeEach
    public void initTest() {
        giftItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createGiftItem() throws Exception {
        int databaseSizeBeforeCreate = giftItemRepository.findAll().size();
        // Create the GiftItem
        restGiftItemMockMvc.perform(post("/api/gift-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(giftItem)))
            .andExpect(status().isCreated());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeCreate + 1);
        GiftItem testGiftItem = giftItemList.get(giftItemList.size() - 1);
        assertThat(testGiftItem.getGiftName()).isEqualTo(DEFAULT_GIFT_NAME);
        assertThat(testGiftItem.getDescripption()).isEqualTo(DEFAULT_DESCRIPPTION);
        assertThat(testGiftItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void createGiftItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = giftItemRepository.findAll().size();

        // Create the GiftItem with an existing ID
        giftItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiftItemMockMvc.perform(post("/api/gift-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(giftItem)))
            .andExpect(status().isBadRequest());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGiftItems() throws Exception {
        // Initialize the database
        giftItemRepository.saveAndFlush(giftItem);

        // Get all the giftItemList
        restGiftItemMockMvc.perform(get("/api/gift-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giftItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].giftName").value(hasItem(DEFAULT_GIFT_NAME)))
            .andExpect(jsonPath("$.[*].descripption").value(hasItem(DEFAULT_DESCRIPPTION)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getGiftItem() throws Exception {
        // Initialize the database
        giftItemRepository.saveAndFlush(giftItem);

        // Get the giftItem
        restGiftItemMockMvc.perform(get("/api/gift-items/{id}", giftItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(giftItem.getId().intValue()))
            .andExpect(jsonPath("$.giftName").value(DEFAULT_GIFT_NAME))
            .andExpect(jsonPath("$.descripption").value(DEFAULT_DESCRIPPTION))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGiftItem() throws Exception {
        // Get the giftItem
        restGiftItemMockMvc.perform(get("/api/gift-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGiftItem() throws Exception {
        // Initialize the database
        giftItemRepository.saveAndFlush(giftItem);

        int databaseSizeBeforeUpdate = giftItemRepository.findAll().size();

        // Update the giftItem
        GiftItem updatedGiftItem = giftItemRepository.findById(giftItem.getId()).get();
        // Disconnect from session so that the updates on updatedGiftItem are not directly saved in db
        em.detach(updatedGiftItem);
        updatedGiftItem
            .giftName(UPDATED_GIFT_NAME)
            .descripption(UPDATED_DESCRIPPTION)
            .unitPrice(UPDATED_UNIT_PRICE);

        restGiftItemMockMvc.perform(put("/api/gift-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGiftItem)))
            .andExpect(status().isOk());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeUpdate);
        GiftItem testGiftItem = giftItemList.get(giftItemList.size() - 1);
        assertThat(testGiftItem.getGiftName()).isEqualTo(UPDATED_GIFT_NAME);
        assertThat(testGiftItem.getDescripption()).isEqualTo(UPDATED_DESCRIPPTION);
        assertThat(testGiftItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingGiftItem() throws Exception {
        int databaseSizeBeforeUpdate = giftItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftItemMockMvc.perform(put("/api/gift-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(giftItem)))
            .andExpect(status().isBadRequest());

        // Validate the GiftItem in the database
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGiftItem() throws Exception {
        // Initialize the database
        giftItemRepository.saveAndFlush(giftItem);

        int databaseSizeBeforeDelete = giftItemRepository.findAll().size();

        // Delete the giftItem
        restGiftItemMockMvc.perform(delete("/api/gift-items/{id}", giftItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GiftItem> giftItemList = giftItemRepository.findAll();
        assertThat(giftItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
