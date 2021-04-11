package fr.polytech.info4.web.rest;

import fr.polytech.info4.MyblogApp;
import fr.polytech.info4.domain.Cooperative;
import fr.polytech.info4.repository.CooperativeRepository;

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
 * Integration tests for the {@link CooperativeResource} REST controller.
 */
@SpringBootTest(classes = MyblogApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CooperativeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CooperativeRepository cooperativeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCooperativeMockMvc;

    private Cooperative cooperative;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cooperative createEntity(EntityManager em) {
        Cooperative cooperative = new Cooperative()
            .name(DEFAULT_NAME);
        return cooperative;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cooperative createUpdatedEntity(EntityManager em) {
        Cooperative cooperative = new Cooperative()
            .name(UPDATED_NAME);
        return cooperative;
    }

    @BeforeEach
    public void initTest() {
        cooperative = createEntity(em);
    }

    @Test
    @Transactional
    public void createCooperative() throws Exception {
        int databaseSizeBeforeCreate = cooperativeRepository.findAll().size();
        // Create the Cooperative
        restCooperativeMockMvc.perform(post("/api/cooperatives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cooperative)))
            .andExpect(status().isCreated());

        // Validate the Cooperative in the database
        List<Cooperative> cooperativeList = cooperativeRepository.findAll();
        assertThat(cooperativeList).hasSize(databaseSizeBeforeCreate + 1);
        Cooperative testCooperative = cooperativeList.get(cooperativeList.size() - 1);
        assertThat(testCooperative.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCooperativeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cooperativeRepository.findAll().size();

        // Create the Cooperative with an existing ID
        cooperative.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCooperativeMockMvc.perform(post("/api/cooperatives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cooperative)))
            .andExpect(status().isBadRequest());

        // Validate the Cooperative in the database
        List<Cooperative> cooperativeList = cooperativeRepository.findAll();
        assertThat(cooperativeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cooperativeRepository.findAll().size();
        // set the field null
        cooperative.setName(null);

        // Create the Cooperative, which fails.


        restCooperativeMockMvc.perform(post("/api/cooperatives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cooperative)))
            .andExpect(status().isBadRequest());

        List<Cooperative> cooperativeList = cooperativeRepository.findAll();
        assertThat(cooperativeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCooperatives() throws Exception {
        // Initialize the database
        cooperativeRepository.saveAndFlush(cooperative);

        // Get all the cooperativeList
        restCooperativeMockMvc.perform(get("/api/cooperatives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cooperative.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCooperative() throws Exception {
        // Initialize the database
        cooperativeRepository.saveAndFlush(cooperative);

        // Get the cooperative
        restCooperativeMockMvc.perform(get("/api/cooperatives/{id}", cooperative.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cooperative.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingCooperative() throws Exception {
        // Get the cooperative
        restCooperativeMockMvc.perform(get("/api/cooperatives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCooperative() throws Exception {
        // Initialize the database
        cooperativeRepository.saveAndFlush(cooperative);

        int databaseSizeBeforeUpdate = cooperativeRepository.findAll().size();

        // Update the cooperative
        Cooperative updatedCooperative = cooperativeRepository.findById(cooperative.getId()).get();
        // Disconnect from session so that the updates on updatedCooperative are not directly saved in db
        em.detach(updatedCooperative);
        updatedCooperative
            .name(UPDATED_NAME);

        restCooperativeMockMvc.perform(put("/api/cooperatives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCooperative)))
            .andExpect(status().isOk());

        // Validate the Cooperative in the database
        List<Cooperative> cooperativeList = cooperativeRepository.findAll();
        assertThat(cooperativeList).hasSize(databaseSizeBeforeUpdate);
        Cooperative testCooperative = cooperativeList.get(cooperativeList.size() - 1);
        assertThat(testCooperative.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCooperative() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCooperativeMockMvc.perform(put("/api/cooperatives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cooperative)))
            .andExpect(status().isBadRequest());

        // Validate the Cooperative in the database
        List<Cooperative> cooperativeList = cooperativeRepository.findAll();
        assertThat(cooperativeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCooperative() throws Exception {
        // Initialize the database
        cooperativeRepository.saveAndFlush(cooperative);

        int databaseSizeBeforeDelete = cooperativeRepository.findAll().size();

        // Delete the cooperative
        restCooperativeMockMvc.perform(delete("/api/cooperatives/{id}", cooperative.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cooperative> cooperativeList = cooperativeRepository.findAll();
        assertThat(cooperativeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
