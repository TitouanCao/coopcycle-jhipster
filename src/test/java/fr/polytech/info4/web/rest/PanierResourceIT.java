package fr.polytech.info4.web.rest;

import fr.polytech.info4.MyblogApp;
import fr.polytech.info4.domain.Panier;
import fr.polytech.info4.repository.PanierRepository;

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
 * Integration tests for the {@link PanierResource} REST controller.
 */
@SpringBootTest(classes = MyblogApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PanierResourceIT {

    private static final Integer DEFAULT_NB_ELEMENTS = 1;
    private static final Integer UPDATED_NB_ELEMENTS = 2;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    @Autowired
    private PanierRepository panierRepository;

    @Mock
    private PanierRepository panierRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPanierMockMvc;

    private Panier panier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Panier createEntity(EntityManager em) {
        Panier panier = new Panier()
            .nbElements(DEFAULT_NB_ELEMENTS)
            .price(DEFAULT_PRICE);
        return panier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Panier createUpdatedEntity(EntityManager em) {
        Panier panier = new Panier()
            .nbElements(UPDATED_NB_ELEMENTS)
            .price(UPDATED_PRICE);
        return panier;
    }

    @BeforeEach
    public void initTest() {
        panier = createEntity(em);
    }

    @Test
    @Transactional
    public void createPanier() throws Exception {
        int databaseSizeBeforeCreate = panierRepository.findAll().size();
        // Create the Panier
        restPanierMockMvc.perform(post("/api/paniers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panier)))
            .andExpect(status().isCreated());

        // Validate the Panier in the database
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeCreate + 1);
        Panier testPanier = panierList.get(panierList.size() - 1);
        assertThat(testPanier.getNbElements()).isEqualTo(DEFAULT_NB_ELEMENTS);
        assertThat(testPanier.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createPanierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = panierRepository.findAll().size();

        // Create the Panier with an existing ID
        panier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPanierMockMvc.perform(post("/api/paniers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panier)))
            .andExpect(status().isBadRequest());

        // Validate the Panier in the database
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNbElementsIsRequired() throws Exception {
        int databaseSizeBeforeTest = panierRepository.findAll().size();
        // set the field null
        panier.setNbElements(null);

        // Create the Panier, which fails.


        restPanierMockMvc.perform(post("/api/paniers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panier)))
            .andExpect(status().isBadRequest());

        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = panierRepository.findAll().size();
        // set the field null
        panier.setPrice(null);

        // Create the Panier, which fails.


        restPanierMockMvc.perform(post("/api/paniers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panier)))
            .andExpect(status().isBadRequest());

        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaniers() throws Exception {
        // Initialize the database
        panierRepository.saveAndFlush(panier);

        // Get all the panierList
        restPanierMockMvc.perform(get("/api/paniers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(panier.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbElements").value(hasItem(DEFAULT_NB_ELEMENTS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPaniersWithEagerRelationshipsIsEnabled() throws Exception {
        when(panierRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPanierMockMvc.perform(get("/api/paniers?eagerload=true"))
            .andExpect(status().isOk());

        verify(panierRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPaniersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(panierRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPanierMockMvc.perform(get("/api/paniers?eagerload=true"))
            .andExpect(status().isOk());

        verify(panierRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPanier() throws Exception {
        // Initialize the database
        panierRepository.saveAndFlush(panier);

        // Get the panier
        restPanierMockMvc.perform(get("/api/paniers/{id}", panier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(panier.getId().intValue()))
            .andExpect(jsonPath("$.nbElements").value(DEFAULT_NB_ELEMENTS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }
    @Test
    @Transactional
    public void getNonExistingPanier() throws Exception {
        // Get the panier
        restPanierMockMvc.perform(get("/api/paniers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePanier() throws Exception {
        // Initialize the database
        panierRepository.saveAndFlush(panier);

        int databaseSizeBeforeUpdate = panierRepository.findAll().size();

        // Update the panier
        Panier updatedPanier = panierRepository.findById(panier.getId()).get();
        // Disconnect from session so that the updates on updatedPanier are not directly saved in db
        em.detach(updatedPanier);
        updatedPanier
            .nbElements(UPDATED_NB_ELEMENTS)
            .price(UPDATED_PRICE);

        restPanierMockMvc.perform(put("/api/paniers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPanier)))
            .andExpect(status().isOk());

        // Validate the Panier in the database
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeUpdate);
        Panier testPanier = panierList.get(panierList.size() - 1);
        assertThat(testPanier.getNbElements()).isEqualTo(UPDATED_NB_ELEMENTS);
        assertThat(testPanier.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingPanier() throws Exception {
        int databaseSizeBeforeUpdate = panierRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPanierMockMvc.perform(put("/api/paniers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panier)))
            .andExpect(status().isBadRequest());

        // Validate the Panier in the database
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePanier() throws Exception {
        // Initialize the database
        panierRepository.saveAndFlush(panier);

        int databaseSizeBeforeDelete = panierRepository.findAll().size();

        // Delete the panier
        restPanierMockMvc.perform(delete("/api/paniers/{id}", panier.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
