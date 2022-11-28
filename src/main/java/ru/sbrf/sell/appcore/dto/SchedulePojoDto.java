package ru.sbrf.sell.appcore.dto;

import lombok.Data;
import ru.sbrf.sell.appcore.entity.pojo.SessionPojo;

import java.util.List;

@Data
public class SchedulePojoDto {

    String day;
    boolean active;
    List<SessionPojoDto> sessions;
}
