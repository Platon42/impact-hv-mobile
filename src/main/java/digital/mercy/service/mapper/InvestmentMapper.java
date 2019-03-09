package digital.mercy.service.mapper;

import digital.mercy.domain.*;
import digital.mercy.service.dto.InvestmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Investment and its DTO InvestmentDTO.
 */
@Mapper(componentModel = "spring", uses = {ProjectMapper.class, MemberMapper.class})
public interface InvestmentMapper extends EntityMapper<InvestmentDTO, Investment> {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "user.id", target = "userId")
    InvestmentDTO toDto(Investment investment);

    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "userId", target = "user")
    Investment toEntity(InvestmentDTO investmentDTO);

    default Investment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Investment investment = new Investment();
        investment.setId(id);
        return investment;
    }
}
