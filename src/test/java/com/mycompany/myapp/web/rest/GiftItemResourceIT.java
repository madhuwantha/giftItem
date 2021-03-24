package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.GiftItemApp;
import com.mycompany.myapp.domain.GiftItem;
import com.mycompany.myapp.repository.GiftItemRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GiftItemResource} REST controller.
 */
@SpringBootTest(classes = GiftItemApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GiftItemResourceIT {

    private static final String DEFAULT_GIFT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GIFT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_UNIT_PRICE = 1D;
    private static final Double UPDATED_UNIT_PRICE = 2D;

    private static final Integer DEFAULT_AVALIBLE_QUANTITY = 1;
    private static final Integer UPDATED_AVALIBLE_QUANTITY = 2;

    @Autowired
    private GiftItemRepository giftItemRepository;

    @Mock
    private GiftItemRepository giftItemRepositoryMock;

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
            .unitPrice(DEFAULT_UNIT_PRICE)
            .avalibleQuantity(DEFAULT_AVALIBLE_QUANTITY);
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
            .unitPrice(UPDATED_UNIT_PRICE)
            .avalibleQuantity(UPDATED_AVALIBLE_QUANTITY);
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
        assertThat(testGiftItem.getAvalibleQuantity()).isEqualTo(DEFAULT_AVALIBLE_QUANTITY);
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
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].avalibleQuantity").value(hasItem(DEFAULT_AVALIBLE_QUANTITY)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGiftItemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(giftItemRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGiftItemMockMvc.perform(get("/api/gift-items?eagerload=true"))
            .andExpect(status().isOk());

        verify(giftItemRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGiftItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(giftItemRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGiftItemMockMvc.perform(get("/api/gift-items?eagerload=true"))
            .andExpect(status().isOk());

        verify(giftItemRepositoryMock, times(1)).findAllWithEagerRelationships(any());
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
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.avalibleQuantity").value(DEFAULT_AVALIBLE_QUANTITY));
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
            .unitPrice(UPDATED_UNIT_PRICE)
            .avalibleQuantity(UPDATED_AVALIBLE_QUANTITY);

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
        assertThat(testGiftItem.getAvalibleQuantity()).isEqualTo(UPDATED_AVALIBLE_QUANTITY);
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
