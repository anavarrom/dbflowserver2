package dbflow.server.service.mapper;


import dbflow.server.domain.*;
import dbflow.server.service.dto.SafeKeepingProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SafeKeepingProject} and its DTO {@link SafeKeepingProjectDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SafeKeepingProjectMapper extends EntityMapper<SafeKeepingProjectDTO, SafeKeepingProject> {


    @Mapping(target = "safeKeepingPeriods", ignore = true)
    @Mapping(target = "removeSafeKeepingPeriod", ignore = true)
    SafeKeepingProject toEntity(SafeKeepingProjectDTO safeKeepingProjectDTO);

    default SafeKeepingProject fromId(Long id) {
        if (id == null) {
            return null;
        }
        SafeKeepingProject safeKeepingProject = new SafeKeepingProject();
        safeKeepingProject.setId(id);
        return safeKeepingProject;
    }
}
