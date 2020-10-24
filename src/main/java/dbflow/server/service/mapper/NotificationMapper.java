package dbflow.server.service.mapper;


import dbflow.server.domain.*;
import dbflow.server.service.dto.NotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notification} and its DTO {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppointmentMapper.class})
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {

    @Mapping(source = "appointment.id", target = "appointmentId")
    NotificationDTO toDto(Notification notification);

    @Mapping(source = "appointmentId", target = "appointment")
    Notification toEntity(NotificationDTO notificationDTO);

    default Notification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notification notification = new Notification();
        notification.setId(id);
        return notification;
    }
}
