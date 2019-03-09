package digital.mercy.web.rest;

import digital.mercy.ImpactHvApp;

import digital.mercy.domain.Measure;
import digital.mercy.repository.MeasureRepository;
import digital.mercy.service.MeasureService;
import digital.mercy.service.dto.MeasureDTO;
import digital.mercy.service.mapper.MeasureMapper;
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
 * Test class for the MeasureResource REST controller.
 *
 * @see MeasureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImpactHvApp.class)
public class MeasureResourceIntTest {

    private static final String DEFAULT_CHALLENGE = "AAAAAAAAAA";
    private static final String UPDATED_CHALLENGE = "BBBBBBBBBB";

    private static final Long DEFAULT_PEOPLE_SUPPORTED_POVERTY = 1L;
    private static final Long UPDATED_PEOPLE_SUPPORTED_POVERTY = 2L;

    private static final Long DEFAULT_PEOPLE_RECEIVED_MEDICAL_TREATMENT = 1L;
    private static final Long UPDATED_PEOPLE_RECEIVED_MEDICAL_TREATMENT = 2L;

    private static final Long DEFAULT_KWH_CLEAN_ENERGY_PER_YEAR = 1L;
    private static final Long UPDATED_KWH_CLEAN_ENERGY_PER_YEAR = 2L;

    private static final Long DEFAULT_TONS_CO_2_EMISSIONS_OFFSET = 1L;
    private static final Long UPDATED_TONS_CO_2_EMISSIONS_OFFSET = 2L;

    private static final Long DEFAULT_TONS_PLASTIC_RECYCLED = 1L;
    private static final Long UPDATED_TONS_PLASTIC_RECYCLED = 2L;

    private static final Long DEFAULT_TONS_WASTE_RECYCLED = 1L;
    private static final Long UPDATED_TONS_WASTE_RECYCLED = 2L;

    private static final Long DEFAULT_PEOPLE_RECEIVED_ACCESS_EDUCATION = 1L;
    private static final Long UPDATED_PEOPLE_RECEIVED_ACCESS_EDUCATION = 2L;

    private static final Long DEFAULT_JOBS_CREATED = 1L;
    private static final Long UPDATED_JOBS_CREATED = 2L;

    @Autowired
    private MeasureRepository measureRepository;

    @Autowired
    private MeasureMapper measureMapper;

    @Autowired
    private MeasureService measureService;

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

    private MockMvc restMeasureMockMvc;

    private Measure measure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeasureResource measureResource = new MeasureResource(measureService);
        this.restMeasureMockMvc = MockMvcBuilders.standaloneSetup(measureResource)
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
    public static Measure createEntity(EntityManager em) {
        Measure measure = new Measure()
            .challenge(DEFAULT_CHALLENGE)
            .peopleSupportedPoverty(DEFAULT_PEOPLE_SUPPORTED_POVERTY)
            .peopleReceivedMedicalTreatment(DEFAULT_PEOPLE_RECEIVED_MEDICAL_TREATMENT)
            .kwhCleanEnergyPerYear(DEFAULT_KWH_CLEAN_ENERGY_PER_YEAR)
            .tonsCo2EmissionsOffset(DEFAULT_TONS_CO_2_EMISSIONS_OFFSET)
            .tonsPlasticRecycled(DEFAULT_TONS_PLASTIC_RECYCLED)
            .tonsWasteRecycled(DEFAULT_TONS_WASTE_RECYCLED)
            .peopleReceivedAccessEducation(DEFAULT_PEOPLE_RECEIVED_ACCESS_EDUCATION)
            .jobsCreated(DEFAULT_JOBS_CREATED);
        return measure;
    }

    @Before
    public void initTest() {
        measure = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeasure() throws Exception {
        int databaseSizeBeforeCreate = measureRepository.findAll().size();

        // Create the Measure
        MeasureDTO measureDTO = measureMapper.toDto(measure);
        restMeasureMockMvc.perform(post("/api/measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measureDTO)))
            .andExpect(status().isCreated());

        // Validate the Measure in the database
        List<Measure> measureList = measureRepository.findAll();
        assertThat(measureList).hasSize(databaseSizeBeforeCreate + 1);
        Measure testMeasure = measureList.get(measureList.size() - 1);
        assertThat(testMeasure.getChallenge()).isEqualTo(DEFAULT_CHALLENGE);
        assertThat(testMeasure.getPeopleSupportedPoverty()).isEqualTo(DEFAULT_PEOPLE_SUPPORTED_POVERTY);
        assertThat(testMeasure.getPeopleReceivedMedicalTreatment()).isEqualTo(DEFAULT_PEOPLE_RECEIVED_MEDICAL_TREATMENT);
        assertThat(testMeasure.getKwhCleanEnergyPerYear()).isEqualTo(DEFAULT_KWH_CLEAN_ENERGY_PER_YEAR);
        assertThat(testMeasure.getTonsCo2EmissionsOffset()).isEqualTo(DEFAULT_TONS_CO_2_EMISSIONS_OFFSET);
        assertThat(testMeasure.getTonsPlasticRecycled()).isEqualTo(DEFAULT_TONS_PLASTIC_RECYCLED);
        assertThat(testMeasure.getTonsWasteRecycled()).isEqualTo(DEFAULT_TONS_WASTE_RECYCLED);
        assertThat(testMeasure.getPeopleReceivedAccessEducation()).isEqualTo(DEFAULT_PEOPLE_RECEIVED_ACCESS_EDUCATION);
        assertThat(testMeasure.getJobsCreated()).isEqualTo(DEFAULT_JOBS_CREATED);
    }

    @Test
    @Transactional
    public void createMeasureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = measureRepository.findAll().size();

        // Create the Measure with an existing ID
        measure.setId(1L);
        MeasureDTO measureDTO = measureMapper.toDto(measure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeasureMockMvc.perform(post("/api/measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Measure in the database
        List<Measure> measureList = measureRepository.findAll();
        assertThat(measureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMeasures() throws Exception {
        // Initialize the database
        measureRepository.saveAndFlush(measure);

        // Get all the measureList
        restMeasureMockMvc.perform(get("/api/measures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(measure.getId().intValue())))
            .andExpect(jsonPath("$.[*].challenge").value(hasItem(DEFAULT_CHALLENGE.toString())))
            .andExpect(jsonPath("$.[*].peopleSupportedPoverty").value(hasItem(DEFAULT_PEOPLE_SUPPORTED_POVERTY.intValue())))
            .andExpect(jsonPath("$.[*].peopleReceivedMedicalTreatment").value(hasItem(DEFAULT_PEOPLE_RECEIVED_MEDICAL_TREATMENT.intValue())))
            .andExpect(jsonPath("$.[*].kwhCleanEnergyPerYear").value(hasItem(DEFAULT_KWH_CLEAN_ENERGY_PER_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].tonsCo2EmissionsOffset").value(hasItem(DEFAULT_TONS_CO_2_EMISSIONS_OFFSET.intValue())))
            .andExpect(jsonPath("$.[*].tonsPlasticRecycled").value(hasItem(DEFAULT_TONS_PLASTIC_RECYCLED.intValue())))
            .andExpect(jsonPath("$.[*].tonsWasteRecycled").value(hasItem(DEFAULT_TONS_WASTE_RECYCLED.intValue())))
            .andExpect(jsonPath("$.[*].peopleReceivedAccessEducation").value(hasItem(DEFAULT_PEOPLE_RECEIVED_ACCESS_EDUCATION.intValue())))
            .andExpect(jsonPath("$.[*].jobsCreated").value(hasItem(DEFAULT_JOBS_CREATED.intValue())));
    }
    
    @Test
    @Transactional
    public void getMeasure() throws Exception {
        // Initialize the database
        measureRepository.saveAndFlush(measure);

        // Get the measure
        restMeasureMockMvc.perform(get("/api/measures/{id}", measure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(measure.getId().intValue()))
            .andExpect(jsonPath("$.challenge").value(DEFAULT_CHALLENGE.toString()))
            .andExpect(jsonPath("$.peopleSupportedPoverty").value(DEFAULT_PEOPLE_SUPPORTED_POVERTY.intValue()))
            .andExpect(jsonPath("$.peopleReceivedMedicalTreatment").value(DEFAULT_PEOPLE_RECEIVED_MEDICAL_TREATMENT.intValue()))
            .andExpect(jsonPath("$.kwhCleanEnergyPerYear").value(DEFAULT_KWH_CLEAN_ENERGY_PER_YEAR.intValue()))
            .andExpect(jsonPath("$.tonsCo2EmissionsOffset").value(DEFAULT_TONS_CO_2_EMISSIONS_OFFSET.intValue()))
            .andExpect(jsonPath("$.tonsPlasticRecycled").value(DEFAULT_TONS_PLASTIC_RECYCLED.intValue()))
            .andExpect(jsonPath("$.tonsWasteRecycled").value(DEFAULT_TONS_WASTE_RECYCLED.intValue()))
            .andExpect(jsonPath("$.peopleReceivedAccessEducation").value(DEFAULT_PEOPLE_RECEIVED_ACCESS_EDUCATION.intValue()))
            .andExpect(jsonPath("$.jobsCreated").value(DEFAULT_JOBS_CREATED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMeasure() throws Exception {
        // Get the measure
        restMeasureMockMvc.perform(get("/api/measures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeasure() throws Exception {
        // Initialize the database
        measureRepository.saveAndFlush(measure);

        int databaseSizeBeforeUpdate = measureRepository.findAll().size();

        // Update the measure
        Measure updatedMeasure = measureRepository.findById(measure.getId()).get();
        // Disconnect from session so that the updates on updatedMeasure are not directly saved in db
        em.detach(updatedMeasure);
        updatedMeasure
            .challenge(UPDATED_CHALLENGE)
            .peopleSupportedPoverty(UPDATED_PEOPLE_SUPPORTED_POVERTY)
            .peopleReceivedMedicalTreatment(UPDATED_PEOPLE_RECEIVED_MEDICAL_TREATMENT)
            .kwhCleanEnergyPerYear(UPDATED_KWH_CLEAN_ENERGY_PER_YEAR)
            .tonsCo2EmissionsOffset(UPDATED_TONS_CO_2_EMISSIONS_OFFSET)
            .tonsPlasticRecycled(UPDATED_TONS_PLASTIC_RECYCLED)
            .tonsWasteRecycled(UPDATED_TONS_WASTE_RECYCLED)
            .peopleReceivedAccessEducation(UPDATED_PEOPLE_RECEIVED_ACCESS_EDUCATION)
            .jobsCreated(UPDATED_JOBS_CREATED);
        MeasureDTO measureDTO = measureMapper.toDto(updatedMeasure);

        restMeasureMockMvc.perform(put("/api/measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measureDTO)))
            .andExpect(status().isOk());

        // Validate the Measure in the database
        List<Measure> measureList = measureRepository.findAll();
        assertThat(measureList).hasSize(databaseSizeBeforeUpdate);
        Measure testMeasure = measureList.get(measureList.size() - 1);
        assertThat(testMeasure.getChallenge()).isEqualTo(UPDATED_CHALLENGE);
        assertThat(testMeasure.getPeopleSupportedPoverty()).isEqualTo(UPDATED_PEOPLE_SUPPORTED_POVERTY);
        assertThat(testMeasure.getPeopleReceivedMedicalTreatment()).isEqualTo(UPDATED_PEOPLE_RECEIVED_MEDICAL_TREATMENT);
        assertThat(testMeasure.getKwhCleanEnergyPerYear()).isEqualTo(UPDATED_KWH_CLEAN_ENERGY_PER_YEAR);
        assertThat(testMeasure.getTonsCo2EmissionsOffset()).isEqualTo(UPDATED_TONS_CO_2_EMISSIONS_OFFSET);
        assertThat(testMeasure.getTonsPlasticRecycled()).isEqualTo(UPDATED_TONS_PLASTIC_RECYCLED);
        assertThat(testMeasure.getTonsWasteRecycled()).isEqualTo(UPDATED_TONS_WASTE_RECYCLED);
        assertThat(testMeasure.getPeopleReceivedAccessEducation()).isEqualTo(UPDATED_PEOPLE_RECEIVED_ACCESS_EDUCATION);
        assertThat(testMeasure.getJobsCreated()).isEqualTo(UPDATED_JOBS_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingMeasure() throws Exception {
        int databaseSizeBeforeUpdate = measureRepository.findAll().size();

        // Create the Measure
        MeasureDTO measureDTO = measureMapper.toDto(measure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeasureMockMvc.perform(put("/api/measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Measure in the database
        List<Measure> measureList = measureRepository.findAll();
        assertThat(measureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeasure() throws Exception {
        // Initialize the database
        measureRepository.saveAndFlush(measure);

        int databaseSizeBeforeDelete = measureRepository.findAll().size();

        // Delete the measure
        restMeasureMockMvc.perform(delete("/api/measures/{id}", measure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Measure> measureList = measureRepository.findAll();
        assertThat(measureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Measure.class);
        Measure measure1 = new Measure();
        measure1.setId(1L);
        Measure measure2 = new Measure();
        measure2.setId(measure1.getId());
        assertThat(measure1).isEqualTo(measure2);
        measure2.setId(2L);
        assertThat(measure1).isNotEqualTo(measure2);
        measure1.setId(null);
        assertThat(measure1).isNotEqualTo(measure2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeasureDTO.class);
        MeasureDTO measureDTO1 = new MeasureDTO();
        measureDTO1.setId(1L);
        MeasureDTO measureDTO2 = new MeasureDTO();
        assertThat(measureDTO1).isNotEqualTo(measureDTO2);
        measureDTO2.setId(measureDTO1.getId());
        assertThat(measureDTO1).isEqualTo(measureDTO2);
        measureDTO2.setId(2L);
        assertThat(measureDTO1).isNotEqualTo(measureDTO2);
        measureDTO1.setId(null);
        assertThat(measureDTO1).isNotEqualTo(measureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(measureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(measureMapper.fromId(null)).isNull();
    }
}
