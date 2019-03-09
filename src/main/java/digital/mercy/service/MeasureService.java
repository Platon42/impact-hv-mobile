package digital.mercy.service;

import digital.mercy.domain.Measure;
import digital.mercy.repository.MeasureRepository;
import digital.mercy.service.dto.MeasureDTO;
import digital.mercy.service.mapper.MeasureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Measure.
 */
@Service
@Transactional
public class MeasureService {

    private final Logger log = LoggerFactory.getLogger(MeasureService.class);

    private final MeasureRepository measureRepository;

    private final MeasureMapper measureMapper;

    public MeasureService(MeasureRepository measureRepository, MeasureMapper measureMapper) {
        this.measureRepository = measureRepository;
        this.measureMapper = measureMapper;
    }

    /**
     * Save a measure.
     *
     * @param measureDTO the entity to save
     * @return the persisted entity
     */
    public MeasureDTO save(MeasureDTO measureDTO) {
        log.debug("Request to save Measure : {}", measureDTO);
        Measure measure = measureMapper.toEntity(measureDTO);
        measure = measureRepository.save(measure);
        return measureMapper.toDto(measure);
    }

    /**
     * Get all the measures.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MeasureDTO> findAll() {
        log.debug("Request to get all Measures");
        return measureRepository.findAll().stream()
            .map(measureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the measures where Challenge is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<MeasureDTO> findAllWhereChallengeIsNull() {
        log.debug("Request to get all measures where Challenge is null");
        return StreamSupport
            .stream(measureRepository.findAll().spliterator(), false)
            .filter(measure -> measure.getChallenge() == null)
            .map(measureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one measure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MeasureDTO> findOne(Long id) {
        log.debug("Request to get Measure : {}", id);
        return measureRepository.findById(id)
            .map(measureMapper::toDto);
    }

    /**
     * Delete the measure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Measure : {}", id);
        measureRepository.deleteById(id);
    }
}
