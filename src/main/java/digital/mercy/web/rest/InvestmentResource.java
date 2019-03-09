package digital.mercy.web.rest;
import digital.mercy.service.InvestmentService;
import digital.mercy.web.rest.errors.BadRequestAlertException;
import digital.mercy.web.rest.util.HeaderUtil;
import digital.mercy.service.dto.InvestmentDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Investment.
 */
@RestController
@RequestMapping("/api")
public class InvestmentResource {

    private final Logger log = LoggerFactory.getLogger(InvestmentResource.class);

    private static final String ENTITY_NAME = "impactHvInvestment";

    private final InvestmentService investmentService;

    public InvestmentResource(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    /**
     * POST  /investments : Create a new investment.
     *
     * @param investmentDTO the investmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new investmentDTO, or with status 400 (Bad Request) if the investment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/investments")
    public ResponseEntity<InvestmentDTO> createInvestment(@RequestBody InvestmentDTO investmentDTO) throws URISyntaxException {
        log.debug("REST request to save Investment : {}", investmentDTO);
        if (investmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new investment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvestmentDTO result = investmentService.save(investmentDTO);
        return ResponseEntity.created(new URI("/api/investments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /investments : Updates an existing investment.
     *
     * @param investmentDTO the investmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated investmentDTO,
     * or with status 400 (Bad Request) if the investmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the investmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/investments")
    public ResponseEntity<InvestmentDTO> updateInvestment(@RequestBody InvestmentDTO investmentDTO) throws URISyntaxException {
        log.debug("REST request to update Investment : {}", investmentDTO);
        if (investmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvestmentDTO result = investmentService.save(investmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, investmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /investments : get all the investments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of investments in body
     */
    @GetMapping("/investments")
    public List<InvestmentDTO> getAllInvestments() {
        log.debug("REST request to get all Investments");
        return investmentService.findAll();
    }

    /**
     * GET  /investments/:id : get the "id" investment.
     *
     * @param id the id of the investmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the investmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/investments/{id}")
    public ResponseEntity<InvestmentDTO> getInvestment(@PathVariable Long id) {
        log.debug("REST request to get Investment : {}", id);
        Optional<InvestmentDTO> investmentDTO = investmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(investmentDTO);
    }

    /**
     * DELETE  /investments/:id : delete the "id" investment.
     *
     * @param id the id of the investmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/investments/{id}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable Long id) {
        log.debug("REST request to delete Investment : {}", id);
        investmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
