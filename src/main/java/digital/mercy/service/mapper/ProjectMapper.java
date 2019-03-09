package digital.mercy.service.mapper;

import digital.mercy.domain.*;
import digital.mercy.service.dto.ProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {FavoriteProjectMapper.class})
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {

    @Mapping(source = "projectName.id", target = "projectNameId")
    ProjectDTO toDto(Project project);

    @Mapping(target = "projectNames", ignore = true)
    @Mapping(source = "projectNameId", target = "projectName")
    @Mapping(target = "projectNames", ignore = true)
    Project toEntity(ProjectDTO projectDTO);

    default Project fromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }
}
