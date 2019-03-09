package digital.mercy.service.mapper;

import digital.mercy.domain.*;
import digital.mercy.service.dto.ChallengeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Challenge and its DTO ChallengeDTO.
 */
@Mapper(componentModel = "spring", uses = {MeasureMapper.class, ProjectMapper.class})
public interface ChallengeMapper extends EntityMapper<ChallengeDTO, Challenge> {

    @Mapping(source = "measure.id", target = "measureId")
    @Mapping(source = "project.id", target = "projectId")
    ChallengeDTO toDto(Challenge challenge);

    @Mapping(source = "measureId", target = "measure")
    @Mapping(source = "projectId", target = "project")
    Challenge toEntity(ChallengeDTO challengeDTO);

    default Challenge fromId(Long id) {
        if (id == null) {
            return null;
        }
        Challenge challenge = new Challenge();
        challenge.setId(id);
        return challenge;
    }
}
