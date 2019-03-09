package digital.mercy.web.rest;
import digital.mercy.service.ChallengeService;
import digital.mercy.web.rest.errors.BadRequestAlertException;
import digital.mercy.web.rest.util.HeaderUtil;
import digital.mercy.service.dto.ChallengeDTO;
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
 * REST controller for managing Challenge.
 */
@RestController
@RequestMapping("/api")
public class ChallengeResource {

    private final Logger log = LoggerFactory.getLogger(ChallengeResource.class);

    private static final String ENTITY_NAME = "impactHvChallenge";

    private final ChallengeService challengeService;

    public ChallengeResource(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    /**
     * POST  /challenges : Create a new challenge.
     *
     * @param challengeDTO the challengeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new challengeDTO, or with status 400 (Bad Request) if the challenge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/challenges")
    public ResponseEntity<ChallengeDTO> createChallenge(@RequestBody ChallengeDTO challengeDTO) throws URISyntaxException {
        log.debug("REST request to save Challenge : {}", challengeDTO);
        if (challengeDTO.getId() != null) {
            throw new BadRequestAlertException("A new challenge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChallengeDTO result = challengeService.save(challengeDTO);
        return ResponseEntity.created(new URI("/api/challenges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /challenges : Updates an existing challenge.
     *
     * @param challengeDTO the challengeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated challengeDTO,
     * or with status 400 (Bad Request) if the challengeDTO is not valid,
     * or with status 500 (Internal Server Error) if the challengeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/challenges")
    public ResponseEntity<ChallengeDTO> updateChallenge(@RequestBody ChallengeDTO challengeDTO) throws URISyntaxException {
        log.debug("REST request to update Challenge : {}", challengeDTO);
        if (challengeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChallengeDTO result = challengeService.save(challengeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, challengeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /challenges : get all the challenges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of challenges in body
     */
    @GetMapping("/challenges")
    public List<ChallengeDTO> getAllChallenges() {
        log.debug("REST request to get all Challenges");
        return challengeService.findAll();
    }

    /**
     * GET  /challenges/:id : get the "id" challenge.
     *
     * @param id the id of the challengeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the challengeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/challenges/{id}")
    public ResponseEntity<ChallengeDTO> getChallenge(@PathVariable Long id) {
        log.debug("REST request to get Challenge : {}", id);
        Optional<ChallengeDTO> challengeDTO = challengeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(challengeDTO);
    }

    /**
     * DELETE  /challenges/:id : delete the "id" challenge.
     *
     * @param id the id of the challengeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/challenges/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long id) {
        log.debug("REST request to delete Challenge : {}", id);
        challengeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
