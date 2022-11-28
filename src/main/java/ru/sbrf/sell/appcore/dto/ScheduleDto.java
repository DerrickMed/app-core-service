package ru.sbrf.sell.appcore.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleDto {

    String user;
    Integer category;
    List<SchedulePojoDto> schedule;
}
