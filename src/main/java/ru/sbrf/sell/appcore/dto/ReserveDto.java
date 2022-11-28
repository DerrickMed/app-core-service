package ru.sbrf.sell.appcore.dto;

import lombok.Data;
import ru.sbrf.sell.appcore.entity.ReserveEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link ReserveEntity} entity
 */
@Data
public class ReserveDto implements Serializable {
    String user;
    String employed;
    int category;
    LocalDateTime time;
}