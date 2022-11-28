package ru.sbrf.sell.appcore.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateScheduleDto implements Serializable {
    String startTime;
    int sessionDuration;
    String endTime;
}
