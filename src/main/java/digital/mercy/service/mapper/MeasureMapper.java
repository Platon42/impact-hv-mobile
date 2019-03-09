package digital.mercy.service.mapper;

import digital.mercy.domain.*;
import digital.mercy.service.dto.MeasureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Measure and its DTO MeasureDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MeasureMapper extends EntityMapper<MeasureDTO, Measure> {


    @Mapping(target = "challenge", ignore = true)
    Measure toEntity(MeasureDTO measureDTO);

    default Measure fromId(Long id) {
        if (id == null) {
            return null;
        }
        Measure measure = new Measure();
        measure.setId(id);
        return measure;
    }
}
