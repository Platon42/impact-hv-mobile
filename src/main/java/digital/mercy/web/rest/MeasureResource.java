package digital.mercy.web.rest;
import digital.mercy.service.MeasureService;
import digital.mercy.web.rest.errors.BadRequestAlertException;
import digital.mercy.web.rest.util.HeaderUtil;
import digital.mercy.service.dto.MeasureDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Measure.
 */
@RestController
@RequestMapping("/api")
public class MeasureResource {

    private final Logger log = LoggerFactory.getLogger(MeasureResource.class);

    private static final String ENTITY_NAME = "impactHvMeasure";

    private final MeasureService measureService;

    public MeasureResource(MeasureService measureService) {
        this.measureService = measureService;
    }

    /**
     * POST  /measures : Create a new measure.
     *
     * @param measureDTO the measureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new measureDTO, or with status 400 (Bad Request) if the measure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/measures")
    public ResponseEntity<MeasureDTO> createMeasure(@RequestBody MeasureDTO measureDTO) throws URISyntaxException {
        log.debug("REST request to save Measure : {}", measureDTO);
        if (measureDTO.getId() != null) {
            throw new BadRequestAlertException("A new measure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeasureDTO result = measureService.save(measureDTO);
        return ResponseEntity.created(new URI("/api/measures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /measures : Updates an existing measure.
     *
     * @param measureDTO the measureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated measureDTO,
     * or with status 400 (Bad Request) if the measureDTO is not valid,
     * or with status 500 (Internal Server Error) if the measureDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/measures")
    public ResponseEntity<MeasureDTO> updateMeasure(@RequestBody MeasureDTO measureDTO) throws URISyntaxException {
        log.debug("REST request to update Measure : {}", measureDTO);
        if (measureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeasureDTO result = measureService.save(measureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, measureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /measures : get all the measures.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of measures in body
     */
    @GetMapping("/measures")
    public List<MeasureDTO> getAllMeasures(@RequestParam(required = false) String filter) {
        if ("challenge-is-null".equals(filter)) {
            log.debug("REST request to get all Measures where challenge is null");
            return measureService.findAllWhereChallengeIsNull();
        }
        log.debug("REST request to get all Measures");
        return measureService.findAll();
    }

    /**
     * GET  /measures/:id : get the "id" measure.
     *
     * @param id the id of the measureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the measureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/measures/{id}")
    public ResponseEntity<MeasureDTO> getMeasure(@PathVariable Long id) {
        log.debug("REST request to get Measure : {}", id);
        Optional<MeasureDTO> measureDTO = measureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(measureDTO);
    }

    /**
     * DELETE  /measures/:id : delete the "id" measure.
     *
     * @param id the id of the measureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/measures/{id}")
    public ResponseEntity<Void> deleteMeasure(@PathVariable Long id) {
        log.debug("REST request to delete Measure : {}", id);
        measureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
