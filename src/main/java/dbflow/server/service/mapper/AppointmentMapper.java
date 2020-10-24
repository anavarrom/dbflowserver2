package dbflow.server.service.mapper;


import dbflow.server.domain.*;
import dbflow.server.service.dto.AppointmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Appointment} and its DTO {@link AppointmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AppointmentMapper extends EntityMapper<AppointmentDTO, Appointment> {


    @Mapping(target = "notification", ignore = true)
    Appointment toEntity(AppointmentDTO appointmentDTO);

    default Appointment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setId(id);
        return appointment;
    }
}
