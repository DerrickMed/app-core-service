package ru.sbrf.sell.appcore.dto;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDto {
    private long id;
    private String from_user_id;
    private String to_user_id;
    private BigDecimal sum;
    private LocalDate date;
}
