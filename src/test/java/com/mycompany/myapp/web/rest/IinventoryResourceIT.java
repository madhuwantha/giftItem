package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.GiftItemApp;
import com.mycompany.myapp.domain.Iinventory;
import com.mycompany.myapp.repository.IinventoryRepository;

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
 * Integration tests for the {@link IinventoryResource} REST controller.
 */
@SpringBootTest(classes = GiftItemApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IinventoryResourceIT {

    private static final Integer DEFAULT_AVALIBLE_QUANTITY = 1;
    private static final Integer UPDATED_AVALIBLE_QUANTITY = 2;

    @Autowired
    private IinventoryRepository iinventoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIinventoryMockMvc;

    private Iinventory iinventory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Iinventory createEntity(EntityManager em) {
        Iinventory iinventory = new Iinventory()
            .avalibleQuantity(DEFAULT_AVALIBLE_QUANTITY);
        return iinventory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Iinventory createUpdatedEntity(EntityManager em) {
        Iinventory iinventory = new Iinventory()
            .avalibleQuantity(UPDATED_AVALIBLE_QUANTITY);
        return iinventory;
    }

    @BeforeEach
    public void initTest() {
        iinventory = createEntity(em);
    }

    @Test
    @Transactional
    public void createIinventory() throws Exception {
        int databaseSizeBeforeCreate = iinventoryRepository.findAll().size();
        // Create the Iinventory
        restIinventoryMockMvc.perform(post("/api/iinventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(iinventory)))
            .andExpect(status().isCreated());

        // Validate the Iinventory in the database
        List<Iinventory> iinventoryList = iinventoryRepository.findAll();
        assertThat(iinventoryList).hasSize(databaseSizeBeforeCreate + 1);
        Iinventory testIinventory = iinventoryList.get(iinventoryList.size() - 1);
        assertThat(testIinventory.getAvalibleQuantity()).isEqualTo(DEFAULT_AVALIBLE_QUANTITY);
    }

    @Test
    @Transactional
    public void createIinventoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iinventoryRepository.findAll().size();

        // Create the Iinventory with an existing ID
        iinventory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIinventoryMockMvc.perform(post("/api/iinventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(iinventory)))
            .andExpect(status().isBadRequest());

        // Validate the Iinventory in the database
        List<Iinventory> iinventoryList = iinventoryRepository.findAll();
        assertThat(iinventoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIinventories() throws Exception {
        // Initialize the database
        iinventoryRepository.saveAndFlush(iinventory);

        // Get all the iinventoryList
        restIinventoryMockMvc.perform(get("/api/iinventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iinventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].avalibleQuantity").value(hasItem(DEFAULT_AVALIBLE_QUANTITY)));
    }
    
    @Test
    @Transactional
    public void getIinventory() throws Exception {
        // Initialize the database
        iinventoryRepository.saveAndFlush(iinventory);

        // Get the iinventory
        restIinventoryMockMvc.perform(get("/api/iinventories/{id}", iinventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(iinventory.getId().intValue()))
            .andExpect(jsonPath("$.avalibleQuantity").value(DEFAULT_AVALIBLE_QUANTITY));
    }
    @Test
    @Transactional
    public void getNonExistingIinventory() throws Exception {
        // Get the iinventory
        restIinventoryMockMvc.perform(get("/api/iinventories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIinventory() throws Exception {
        // Initialize the database
        iinventoryRepository.saveAndFlush(iinventory);

        int databaseSizeBeforeUpdate = iinventoryRepository.findAll().size();

        // Update the iinventory
        Iinventory updatedIinventory = iinventoryRepository.findById(iinventory.getId()).get();
        // Disconnect from session so that the updates on updatedIinventory are not directly saved in db
        em.detach(updatedIinventory);
        updatedIinventory
            .avalibleQuantity(UPDATED_AVALIBLE_QUANTITY);

        restIinventoryMockMvc.perform(put("/api/iinventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIinventory)))
            .andExpect(status().isOk());

        // Validate the Iinventory in the database
        List<Iinventory> iinventoryList = iinventoryRepository.findAll();
        assertThat(iinventoryList).hasSize(databaseSizeBeforeUpdate);
        Iinventory testIinventory = iinventoryList.get(iinventoryList.size() - 1);
        assertThat(testIinventory.getAvalibleQuantity()).isEqualTo(UPDATED_AVALIBLE_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingIinventory() throws Exception {
        int databaseSizeBeforeUpdate = iinventoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIinventoryMockMvc.perform(put("/api/iinventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(iinventory)))
            .andExpect(status().isBadRequest());

        // Validate the Iinventory in the database
        List<Iinventory> iinventoryList = iinventoryRepository.findAll();
        assertThat(iinventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIinventory() throws Exception {
        // Initialize the database
        iinventoryRepository.saveAndFlush(iinventory);

        int databaseSizeBeforeDelete = iinventoryRepository.findAll().size();

        // Delete the iinventory
        restIinventoryMockMvc.perform(delete("/api/iinventories/{id}", iinventory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Iinventory> iinventoryList = iinventoryRepository.findAll();
        assertThat(iinventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
