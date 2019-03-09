package digital.mercy.service;

import digital.mercy.domain.Investment;
import digital.mercy.repository.InvestmentRepository;
import digital.mercy.service.dto.InvestmentDTO;
import digital.mercy.service.mapper.InvestmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Investment.
 */
@Service
@Transactional
public class InvestmentService {

    private final Logger log = LoggerFactory.getLogger(InvestmentService.class);

    private final InvestmentRepository investmentRepository;

    private final InvestmentMapper investmentMapper;

    public InvestmentService(InvestmentRepository investmentRepository, InvestmentMapper investmentMapper) {
        this.investmentRepository = investmentRepository;
        this.investmentMapper = investmentMapper;
    }

    /**
     * Save a investment.
     *
     * @param investmentDTO the entity to save
     * @return the persisted entity
     */
    public InvestmentDTO save(InvestmentDTO investmentDTO) {
        log.debug("Request to save Investment : {}", investmentDTO);
        Investment investment = investmentMapper.toEntity(investmentDTO);
        investment = investmentRepository.save(investment);
        return investmentMapper.toDto(investment);
    }

    /**
     * Get all the investments.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<InvestmentDTO> findAll() {
        log.debug("Request to get all Investments");
        return investmentRepository.findAll().stream()
            .map(investmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one investment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<InvestmentDTO> findOne(Long id) {
        log.debug("Request to get Investment : {}", id);
        return investmentRepository.findById(id)
            .map(investmentMapper::toDto);
    }

    /**
     * Delete the investment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Investment : {}", id);
        investmentRepository.deleteById(id);
    }
}
