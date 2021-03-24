package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.GiftItemApp;
import com.mycompany.myapp.domain.GiftOrder;
import com.mycompany.myapp.repository.GiftOrderRepository;

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
 * Integration tests for the {@link GiftOrderResource} REST controller.
 */
@SpringBootTest(classes = GiftItemApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GiftOrderResourceIT {

    private static final String DEFAULT_DESCRIPPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPPTION = "BBBBBBBBBB";

    @Autowired
    private GiftOrderRepository giftOrderRepository;

    @Mock
    private GiftOrderRepository giftOrderRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGiftOrderMockMvc;

    private GiftOrder giftOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GiftOrder createEntity(EntityManager em) {
        GiftOrder giftOrder = new GiftOrder()
            .descripption(DEFAULT_DESCRIPPTION);
        return giftOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GiftOrder createUpdatedEntity(EntityManager em) {
        GiftOrder giftOrder = new GiftOrder()
            .descripption(UPDATED_DESCRIPPTION);
        return giftOrder;
    }

    @BeforeEach
    public void initTest() {
        giftOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createGiftOrder() throws Exception {
        int databaseSizeBeforeCreate = giftOrderRepository.findAll().size();
        // Create the GiftOrder
        restGiftOrderMockMvc.perform(post("/api/gift-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(giftOrder)))
            .andExpect(status().isCreated());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeCreate + 1);
        GiftOrder testGiftOrder = giftOrderList.get(giftOrderList.size() - 1);
        assertThat(testGiftOrder.getDescripption()).isEqualTo(DEFAULT_DESCRIPPTION);
    }

    @Test
    @Transactional
    public void createGiftOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = giftOrderRepository.findAll().size();

        // Create the GiftOrder with an existing ID
        giftOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiftOrderMockMvc.perform(post("/api/gift-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(giftOrder)))
            .andExpect(status().isBadRequest());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGiftOrders() throws Exception {
        // Initialize the database
        giftOrderRepository.saveAndFlush(giftOrder);

        // Get all the giftOrderList
        restGiftOrderMockMvc.perform(get("/api/gift-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giftOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripption").value(hasItem(DEFAULT_DESCRIPPTION)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGiftOrdersWithEagerRelationshipsIsEnabled() throws Exception {
        when(giftOrderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGiftOrderMockMvc.perform(get("/api/gift-orders?eagerload=true"))
            .andExpect(status().isOk());

        verify(giftOrderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGiftOrdersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(giftOrderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGiftOrderMockMvc.perform(get("/api/gift-orders?eagerload=true"))
            .andExpect(status().isOk());

        verify(giftOrderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGiftOrder() throws Exception {
        // Initialize the database
        giftOrderRepository.saveAndFlush(giftOrder);

        // Get the giftOrder
        restGiftOrderMockMvc.perform(get("/api/gift-orders/{id}", giftOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(giftOrder.getId().intValue()))
            .andExpect(jsonPath("$.descripption").value(DEFAULT_DESCRIPPTION));
    }
    @Test
    @Transactional
    public void getNonExistingGiftOrder() throws Exception {
        // Get the giftOrder
        restGiftOrderMockMvc.perform(get("/api/gift-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGiftOrder() throws Exception {
        // Initialize the database
        giftOrderRepository.saveAndFlush(giftOrder);

        int databaseSizeBeforeUpdate = giftOrderRepository.findAll().size();

        // Update the giftOrder
        GiftOrder updatedGiftOrder = giftOrderRepository.findById(giftOrder.getId()).get();
        // Disconnect from session so that the updates on updatedGiftOrder are not directly saved in db
        em.detach(updatedGiftOrder);
        updatedGiftOrder
            .descripption(UPDATED_DESCRIPPTION);

        restGiftOrderMockMvc.perform(put("/api/gift-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGiftOrder)))
            .andExpect(status().isOk());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeUpdate);
        GiftOrder testGiftOrder = giftOrderList.get(giftOrderList.size() - 1);
        assertThat(testGiftOrder.getDescripption()).isEqualTo(UPDATED_DESCRIPPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingGiftOrder() throws Exception {
        int databaseSizeBeforeUpdate = giftOrderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftOrderMockMvc.perform(put("/api/gift-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(giftOrder)))
            .andExpect(status().isBadRequest());

        // Validate the GiftOrder in the database
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGiftOrder() throws Exception {
        // Initialize the database
        giftOrderRepository.saveAndFlush(giftOrder);

        int databaseSizeBeforeDelete = giftOrderRepository.findAll().size();

        // Delete the giftOrder
        restGiftOrderMockMvc.perform(delete("/api/gift-orders/{id}", giftOrder.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GiftOrder> giftOrderList = giftOrderRepository.findAll();
        assertThat(giftOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
