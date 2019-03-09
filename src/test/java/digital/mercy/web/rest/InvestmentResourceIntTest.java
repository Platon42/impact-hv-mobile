package digital.mercy.web.rest;

import digital.mercy.ImpactHvApp;

import digital.mercy.domain.Investment;
import digital.mercy.repository.InvestmentRepository;
import digital.mercy.service.InvestmentService;
import digital.mercy.service.dto.InvestmentDTO;
import digital.mercy.service.mapper.InvestmentMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static digital.mercy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InvestmentResource REST controller.
 *
 * @see InvestmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImpactHvApp.class)
public class InvestmentResourceIntTest {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private InvestmentMapper investmentMapper;

    @Autowired
    private InvestmentService investmentService;

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

    private MockMvc restInvestmentMockMvc;

    private Investment investment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvestmentResource investmentResource = new InvestmentResource(investmentService);
        this.restInvestmentMockMvc = MockMvcBuilders.standaloneSetup(investmentResource)
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
    public static Investment createEntity(EntityManager em) {
        Investment investment = new Investment()
            .user(DEFAULT_USER)
            .project(DEFAULT_PROJECT)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .amount(DEFAULT_AMOUNT);
        return investment;
    }

    @Before
    public void initTest() {
        investment = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvestment() throws Exception {
        int databaseSizeBeforeCreate = investmentRepository.findAll().size();

        // Create the Investment
        InvestmentDTO investmentDTO = investmentMapper.toDto(investment);
        restInvestmentMockMvc.perform(post("/api/investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Investment in the database
        List<Investment> investmentList = investmentRepository.findAll();
        assertThat(investmentList).hasSize(databaseSizeBeforeCreate + 1);
        Investment testInvestment = investmentList.get(investmentList.size() - 1);
        assertThat(testInvestment.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testInvestment.getProject()).isEqualTo(DEFAULT_PROJECT);
        assertThat(testInvestment.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testInvestment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createInvestmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = investmentRepository.findAll().size();

        // Create the Investment with an existing ID
        investment.setId(1L);
        InvestmentDTO investmentDTO = investmentMapper.toDto(investment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvestmentMockMvc.perform(post("/api/investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Investment in the database
        List<Investment> investmentList = investmentRepository.findAll();
        assertThat(investmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInvestments() throws Exception {
        // Initialize the database
        investmentRepository.saveAndFlush(investment);

        // Get all the investmentList
        restInvestmentMockMvc.perform(get("/api/investments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(investment.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getInvestment() throws Exception {
        // Initialize the database
        investmentRepository.saveAndFlush(investment);

        // Get the investment
        restInvestmentMockMvc.perform(get("/api/investments/{id}", investment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(investment.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT.toString()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInvestment() throws Exception {
        // Get the investment
        restInvestmentMockMvc.perform(get("/api/investments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvestment() throws Exception {
        // Initialize the database
        investmentRepository.saveAndFlush(investment);

        int databaseSizeBeforeUpdate = investmentRepository.findAll().size();

        // Update the investment
        Investment updatedInvestment = investmentRepository.findById(investment.getId()).get();
        // Disconnect from session so that the updates on updatedInvestment are not directly saved in db
        em.detach(updatedInvestment);
        updatedInvestment
            .user(UPDATED_USER)
            .project(UPDATED_PROJECT)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .amount(UPDATED_AMOUNT);
        InvestmentDTO investmentDTO = investmentMapper.toDto(updatedInvestment);

        restInvestmentMockMvc.perform(put("/api/investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentDTO)))
            .andExpect(status().isOk());

        // Validate the Investment in the database
        List<Investment> investmentList = investmentRepository.findAll();
        assertThat(investmentList).hasSize(databaseSizeBeforeUpdate);
        Investment testInvestment = investmentList.get(investmentList.size() - 1);
        assertThat(testInvestment.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testInvestment.getProject()).isEqualTo(UPDATED_PROJECT);
        assertThat(testInvestment.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testInvestment.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingInvestment() throws Exception {
        int databaseSizeBeforeUpdate = investmentRepository.findAll().size();

        // Create the Investment
        InvestmentDTO investmentDTO = investmentMapper.toDto(investment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvestmentMockMvc.perform(put("/api/investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Investment in the database
        List<Investment> investmentList = investmentRepository.findAll();
        assertThat(investmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvestment() throws Exception {
        // Initialize the database
        investmentRepository.saveAndFlush(investment);

        int databaseSizeBeforeDelete = investmentRepository.findAll().size();

        // Delete the investment
        restInvestmentMockMvc.perform(delete("/api/investments/{id}", investment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Investment> investmentList = investmentRepository.findAll();
        assertThat(investmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Investment.class);
        Investment investment1 = new Investment();
        investment1.setId(1L);
        Investment investment2 = new Investment();
        investment2.setId(investment1.getId());
        assertThat(investment1).isEqualTo(investment2);
        investment2.setId(2L);
        assertThat(investment1).isNotEqualTo(investment2);
        investment1.setId(null);
        assertThat(investment1).isNotEqualTo(investment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvestmentDTO.class);
        InvestmentDTO investmentDTO1 = new InvestmentDTO();
        investmentDTO1.setId(1L);
        InvestmentDTO investmentDTO2 = new InvestmentDTO();
        assertThat(investmentDTO1).isNotEqualTo(investmentDTO2);
        investmentDTO2.setId(investmentDTO1.getId());
        assertThat(investmentDTO1).isEqualTo(investmentDTO2);
        investmentDTO2.setId(2L);
        assertThat(investmentDTO1).isNotEqualTo(investmentDTO2);
        investmentDTO1.setId(null);
        assertThat(investmentDTO1).isNotEqualTo(investmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(investmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(investmentMapper.fromId(null)).isNull();
    }
}
