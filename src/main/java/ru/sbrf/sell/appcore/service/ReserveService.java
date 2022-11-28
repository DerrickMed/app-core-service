package ru.sbrf.sell.appcore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.sbrf.sell.appcore.dto.TransactionDto;
import ru.sbrf.sell.appcore.dto.TransactionResponseDto;
import ru.sbrf.sell.appcore.entity.ReserveEntity;
import ru.sbrf.sell.appcore.repository.ReserveRepository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class ReserveService {

    @Autowired
    private ReserveRepository repository;

    public ReserveEntity createReserve(String user, String Employed, int category, LocalDateTime time){
        var reserve = new ReserveEntity();
        reserve.setTime(time);
        reserve.setUser(user);
        reserve.setCategory(category);
        reserve.setEmployed(Employed);
        repository.save(reserve);

        return reserve;
    }

    public ReserveEntity pay(String id, int amount){
        var reserveEntity = repository.findById(id).orElse(null);

        if( reserveEntity == null){
            return null;
        }

        TransactionDto transaction = new TransactionDto();
        transaction.setDate(LocalDate.now());
        transaction.setSum(BigDecimal.valueOf(amount));
        transaction.setFrom_user_id(reserveEntity.getUser());
        transaction.setTo_user_id(reserveEntity.getEmployed());

        RestTemplate restTemplate = new RestTemplate();
        var transactionResponse = restTemplate.postForObject("http://localhost:8082/transaction/add",transaction, TransactionResponseDto.class);

       reserveEntity.setPaymentId(String.valueOf(transactionResponse.getTransactionId()));
       reserveEntity = repository.save(reserveEntity);

        return reserveEntity;
    }

    public List<ReserveEntity> getByUser(String user){
        return repository.getByUser(user);
    }

    public List<ReserveEntity> getByEmployee(String employee){
        return repository.getByEmployed(employee);
    }

//    public ReserveEntity getByUserAndCategoryAndDay(String user, int category, LocalDateTime time){
//        return repository.ge(user,category,time);
//    }
    public void deleteReserve(String user, LocalDateTime time){
        repository.removeByUserAndTime(user, time);
    }
}
