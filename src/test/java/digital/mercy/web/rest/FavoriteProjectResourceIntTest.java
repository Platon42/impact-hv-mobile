package digital.mercy.web.rest;

import digital.mercy.ImpactHvApp;

import digital.mercy.domain.FavoriteProject;
import digital.mercy.repository.FavoriteProjectRepository;
import digital.mercy.service.FavoriteProjectService;
import digital.mercy.service.dto.FavoriteProjectDTO;
import digital.mercy.service.mapper.FavoriteProjectMapper;
import digital.mercy.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static digital.mercy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FavoriteProjectResource REST controller.
 *
 * @see FavoriteProjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImpactHvApp.class)
public class FavoriteProjectResourceIntTest {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT = "BBBBBBBBBB";

    @Autowired
    private FavoriteProjectRepository favoriteProjectRepository;

    @Autowired
    private FavoriteProjectMapper favoriteProjectMapper;

    @Autowired
    private FavoriteProjectService favoriteProjectService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFavoriteProjectMockMvc;

    private FavoriteProject favoriteProject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FavoriteProjectResource favoriteProjectResource = new FavoriteProjectResource(favoriteProjectService);
        this.restFavoriteProjectMockMvc = MockMvcBuilders.standaloneSetup(favoriteProjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavoriteProject createEntity(EntityManager em) {
        FavoriteProject favoriteProject = new FavoriteProject()
            .user(DEFAULT_USER)
            .project(DEFAULT_PROJECT);
        return favoriteProject;
    }

    @Before
    public void initTest() {
        favoriteProject = createEntity(em);
    }

    @Test
    @Transactional
    public void createFavoriteProject() throws Exception {
        int databaseSizeBeforeCreate = favoriteProjectRepository.findAll().size();

        // Create the FavoriteProject
        FavoriteProjectDTO favoriteProjectDTO = favoriteProjectMapper.toDto(favoriteProject);
        restFavoriteProjectMockMvc.perform(post("/api/favorite-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoriteProjectDTO)))
            .andExpect(status().isCreated());

        // Validate the FavoriteProject in the database
        List<FavoriteProject> favoriteProjectList = favoriteProjectRepository.findAll();
        assertThat(favoriteProjectList).hasSize(databaseSizeBeforeCreate + 1);
        FavoriteProject testFavoriteProject = favoriteProjectList.get(favoriteProjectList.size() - 1);
        assertThat(testFavoriteProject.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testFavoriteProject.getProject()).isEqualTo(DEFAULT_PROJECT);
    }

    @Test
    @Transactional
    public void createFavoriteProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = favoriteProjectRepository.findAll().size();

        // Create the FavoriteProject with an existing ID
        favoriteProject.setId(1L);
        FavoriteProjectDTO favoriteProjectDTO = favoriteProjectMapper.toDto(favoriteProject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavoriteProjectMockMvc.perform(post("/api/favorite-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoriteProjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FavoriteProject in the database
        List<FavoriteProject> favoriteProjectList = favoriteProjectRepository.findAll();
        assertThat(favoriteProjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFavoriteProjects() throws Exception {
        // Initialize the database
        favoriteProjectRepository.saveAndFlush(favoriteProject);

        // Get all the favoriteProjectList
        restFavoriteProjectMockMvc.perform(get("/api/favorite-projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favoriteProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.toString())));
    }
    
    @Test
    @Transactional
    public void getFavoriteProject() throws Exception {
        // Initialize the database
        favoriteProjectRepository.saveAndFlush(favoriteProject);

        // Get the favoriteProject
        restFavoriteProjectMockMvc.perform(get("/api/favorite-projects/{id}", favoriteProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(favoriteProject.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFavoriteProject() throws Exception {
        // Get the favoriteProject
        restFavoriteProjectMockMvc.perform(get("/api/favorite-projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFavoriteProject() throws Exception {
        // Initialize the database
        favoriteProjectRepository.saveAndFlush(favoriteProject);

        int databaseSizeBeforeUpdate = favoriteProjectRepository.findAll().size();

        // Update the favoriteProject
        FavoriteProject updatedFavoriteProject = favoriteProjectRepository.findById(favoriteProject.getId()).get();
        // Disconnect from session so that the updates on updatedFavoriteProject are not directly saved in db
        em.detach(updatedFavoriteProject);
        updatedFavoriteProject
            .user(UPDATED_USER)
            .project(UPDATED_PROJECT);
        FavoriteProjectDTO favoriteProjectDTO = favoriteProjectMapper.toDto(updatedFavoriteProject);

        restFavoriteProjectMockMvc.perform(put("/api/favorite-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoriteProjectDTO)))
            .andExpect(status().isOk());

        // Validate the FavoriteProject in the database
        List<FavoriteProject> favoriteProjectList = favoriteProjectRepository.findAll();
        assertThat(favoriteProjectList).hasSize(databaseSizeBeforeUpdate);
        FavoriteProject testFavoriteProject = favoriteProjectList.get(favoriteProjectList.size() - 1);
        assertThat(testFavoriteProject.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testFavoriteProject.getProject()).isEqualTo(UPDATED_PROJECT);
    }

    @Test
    @Transactional
    public void updateNonExistingFavoriteProject() throws Exception {
        int databaseSizeBeforeUpdate = favoriteProjectRepository.findAll().size();

        // Create the FavoriteProject
        FavoriteProjectDTO favoriteProjectDTO = favoriteProjectMapper.toDto(favoriteProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavoriteProjectMockMvc.perform(put("/api/favorite-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoriteProjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FavoriteProject in the database
        List<FavoriteProject> favoriteProjectList = favoriteProjectRepository.findAll();
        assertThat(favoriteProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFavoriteProject() throws Exception {
        // Initialize the database
        favoriteProjectRepository.saveAndFlush(favoriteProject);

        int databaseSizeBeforeDelete = favoriteProjectRepository.findAll().size();

        // Delete the favoriteProject
        restFavoriteProjectMockMvc.perform(delete("/api/favorite-projects/{id}", favoriteProject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FavoriteProject> favoriteProjectList = favoriteProjectRepository.findAll();
        assertThat(favoriteProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavoriteProject.class);
        FavoriteProject favoriteProject1 = new FavoriteProject();
        favoriteProject1.setId(1L);
        FavoriteProject favoriteProject2 = new FavoriteProject();
        favoriteProject2.setId(favoriteProject1.getId());
        assertThat(favoriteProject1).isEqualTo(favoriteProject2);
        favoriteProject2.setId(2L);
        assertThat(favoriteProject1).isNotEqualTo(favoriteProject2);
        favoriteProject1.setId(null);
        assertThat(favoriteProject1).isNotEqualTo(favoriteProject2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavoriteProjectDTO.class);
        FavoriteProjectDTO favoriteProjectDTO1 = new FavoriteProjectDTO();
        favoriteProjectDTO1.setId(1L);
        FavoriteProjectDTO favoriteProjectDTO2 = new FavoriteProjectDTO();
        assertThat(favoriteProjectDTO1).isNotEqualTo(favoriteProjectDTO2);
        favoriteProjectDTO2.setId(favoriteProjectDTO1.getId());
        assertThat(favoriteProjectDTO1).isEqualTo(favoriteProjectDTO2);
        favoriteProjectDTO2.setId(2L);
        assertThat(favoriteProjectDTO1).isNotEqualTo(favoriteProjectDTO2);
        favoriteProjectDTO1.setId(null);
        assertThat(favoriteProjectDTO1).isNotEqualTo(favoriteProjectDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(favoriteProjectMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(favoriteProjectMapper.fromId(null)).isNull();
    }
}
