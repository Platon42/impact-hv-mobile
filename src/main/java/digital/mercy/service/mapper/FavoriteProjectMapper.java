package digital.mercy.service.mapper;

import digital.mercy.domain.*;
import digital.mercy.service.dto.FavoriteProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FavoriteProject and its DTO FavoriteProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {MemberMapper.class})
public interface FavoriteProjectMapper extends EntityMapper<FavoriteProjectDTO, FavoriteProject> {

    @Mapping(source = "user.id", target = "userId")
    FavoriteProjectDTO toDto(FavoriteProject favoriteProject);

    @Mapping(target = "projects", ignore = true)
    @Mapping(source = "userId", target = "user")
    FavoriteProject toEntity(FavoriteProjectDTO favoriteProjectDTO);

    default FavoriteProject fromId(Long id) {
        if (id == null) {
            return null;
        }
        FavoriteProject favoriteProject = new FavoriteProject();
        favoriteProject.setId(id);
        return favoriteProject;
    }
}
