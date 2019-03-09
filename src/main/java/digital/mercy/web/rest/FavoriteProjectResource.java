package digital.mercy.web.rest;
import digital.mercy.service.FavoriteProjectService;
import digital.mercy.web.rest.errors.BadRequestAlertException;
import digital.mercy.web.rest.util.HeaderUtil;
import digital.mercy.service.dto.FavoriteProjectDTO;
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
 * REST controller for managing FavoriteProject.
 */
@RestController
@RequestMapping("/api")
public class FavoriteProjectResource {

    private final Logger log = LoggerFactory.getLogger(FavoriteProjectResource.class);

    private static final String ENTITY_NAME = "impactHvFavoriteProject";

    private final FavoriteProjectService favoriteProjectService;

    public FavoriteProjectResource(FavoriteProjectService favoriteProjectService) {
        this.favoriteProjectService = favoriteProjectService;
    }

    /**
     * POST  /favorite-projects : Create a new favoriteProject.
     *
     * @param favoriteProjectDTO the favoriteProjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new favoriteProjectDTO, or with status 400 (Bad Request) if the favoriteProject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/favorite-projects")
    public ResponseEntity<FavoriteProjectDTO> createFavoriteProject(@RequestBody FavoriteProjectDTO favoriteProjectDTO) throws URISyntaxException {
        log.debug("REST request to save FavoriteProject : {}", favoriteProjectDTO);
        if (favoriteProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new favoriteProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FavoriteProjectDTO result = favoriteProjectService.save(favoriteProjectDTO);
        return ResponseEntity.created(new URI("/api/favorite-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /favorite-projects : Updates an existing favoriteProject.
     *
     * @param favoriteProjectDTO the favoriteProjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated favoriteProjectDTO,
     * or with status 400 (Bad Request) if the favoriteProjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the favoriteProjectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/favorite-projects")
    public ResponseEntity<FavoriteProjectDTO> updateFavoriteProject(@RequestBody FavoriteProjectDTO favoriteProjectDTO) throws URISyntaxException {
        log.debug("REST request to update FavoriteProject : {}", favoriteProjectDTO);
        if (favoriteProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FavoriteProjectDTO result = favoriteProjectService.save(favoriteProjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, favoriteProjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /favorite-projects : get all the favoriteProjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of favoriteProjects in body
     */
    @GetMapping("/favorite-projects")
    public List<FavoriteProjectDTO> getAllFavoriteProjects() {
        log.debug("REST request to get all FavoriteProjects");
        return favoriteProjectService.findAll();
    }

    /**
     * GET  /favorite-projects/:id : get the "id" favoriteProject.
     *
     * @param id the id of the favoriteProjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the favoriteProjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/favorite-projects/{id}")
    public ResponseEntity<FavoriteProjectDTO> getFavoriteProject(@PathVariable Long id) {
        log.debug("REST request to get FavoriteProject : {}", id);
        Optional<FavoriteProjectDTO> favoriteProjectDTO = favoriteProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(favoriteProjectDTO);
    }

    /**
     * DELETE  /favorite-projects/:id : delete the "id" favoriteProject.
     *
     * @param id the id of the favoriteProjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/favorite-projects/{id}")
    public ResponseEntity<Void> deleteFavoriteProject(@PathVariable Long id) {
        log.debug("REST request to delete FavoriteProject : {}", id);
        favoriteProjectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
