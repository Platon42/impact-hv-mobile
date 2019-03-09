package digital.mercy.service;

import digital.mercy.domain.Challenge;
import digital.mercy.repository.ChallengeRepository;
import digital.mercy.service.dto.ChallengeDTO;
import digital.mercy.service.mapper.ChallengeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Challenge.
 */
@Service
@Transactional
public class ChallengeService {

    private final Logger log = LoggerFactory.getLogger(ChallengeService.class);

    private final ChallengeRepository challengeRepository;

    private final ChallengeMapper challengeMapper;

    public ChallengeService(ChallengeRepository challengeRepository, ChallengeMapper challengeMapper) {
        this.challengeRepository = challengeRepository;
        this.challengeMapper = challengeMapper;
    }

    /**
     * Save a challenge.
     *
     * @param challengeDTO the entity to save
     * @return the persisted entity
     */
    public ChallengeDTO save(ChallengeDTO challengeDTO) {
        log.debug("Request to save Challenge : {}", challengeDTO);
        Challenge challenge = challengeMapper.toEntity(challengeDTO);
        challenge = challengeRepository.save(challenge);
        return challengeMapper.toDto(challenge);
    }

    /**
     * Get all the challenges.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChallengeDTO> findAll() {
        log.debug("Request to get all Challenges");
        return challengeRepository.findAll().stream()
            .map(challengeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one challenge by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ChallengeDTO> findOne(Long id) {
        log.debug("Request to get Challenge : {}", id);
        return challengeRepository.findById(id)
            .map(challengeMapper::toDto);
    }

    /**
     * Delete the challenge by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Challenge : {}", id);
        challengeRepository.deleteById(id);
    }
}
