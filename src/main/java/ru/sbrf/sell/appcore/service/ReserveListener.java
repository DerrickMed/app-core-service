package ru.sbrf.sell.appcore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.sbrf.sell.appcore.dto.ReserveDto;

@Service
public class ReserveListener {
    private final ObjectMapper objectMapper;
    private final ReserveService reserveService;

    public ReserveListener(ObjectMapper objectMapper, ReserveService reserveService) {
        this.objectMapper = objectMapper;
        this.reserveService = reserveService;
    }

//    @KafkaListener(topics = "UI.TO.RESERVE", groupId = "1")
//    public void listen(String raw) throws JsonProcessingException {
//        reserveService.createReserve(objectMapper.readValue(raw, ReserveDto.class));
//    }
}
