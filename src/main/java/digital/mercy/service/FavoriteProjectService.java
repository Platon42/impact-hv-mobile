package digital.mercy.service;

import digital.mercy.domain.FavoriteProject;
import digital.mercy.repository.FavoriteProjectRepository;
import digital.mercy.service.dto.FavoriteProjectDTO;
import digital.mercy.service.mapper.FavoriteProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing FavoriteProject.
 */
@Service
@Transactional
public class FavoriteProjectService {

    private final Logger log = LoggerFactory.getLogger(FavoriteProjectService.class);

    private final FavoriteProjectRepository favoriteProjectRepository;

    private final FavoriteProjectMapper favoriteProjectMapper;

    public FavoriteProjectService(FavoriteProjectRepository favoriteProjectRepository, FavoriteProjectMapper favoriteProjectMapper) {
        this.favoriteProjectRepository = favoriteProjectRepository;
        this.favoriteProjectMapper = favoriteProjectMapper;
    }

    /**
     * Save a favoriteProject.
     *
     * @param favoriteProjectDTO the entity to save
     * @return the persisted entity
     */
    public FavoriteProjectDTO save(FavoriteProjectDTO favoriteProjectDTO) {
        log.debug("Request to save FavoriteProject : {}", favoriteProjectDTO);
        FavoriteProject favoriteProject = favoriteProjectMapper.toEntity(favoriteProjectDTO);
        favoriteProject = favoriteProjectRepository.save(favoriteProject);
        return favoriteProjectMapper.toDto(favoriteProject);
    }

    /**
     * Get all the favoriteProjects.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<FavoriteProjectDTO> findAll() {
        log.debug("Request to get all FavoriteProjects");
        return favoriteProjectRepository.findAll().stream()
            .map(favoriteProjectMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one favoriteProject by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FavoriteProjectDTO> findOne(Long id) {
        log.debug("Request to get FavoriteProject : {}", id);
        return favoriteProjectRepository.findById(id)
            .map(favoriteProjectMapper::toDto);
    }

    /**
     * Delete the favoriteProject by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FavoriteProject : {}", id);
        favoriteProjectRepository.deleteById(id);
    }
}
