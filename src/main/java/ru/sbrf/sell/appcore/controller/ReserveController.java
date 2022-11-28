package ru.sbrf.sell.appcore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbrf.sell.appcore.dto.TransactionDto;
import ru.sbrf.sell.appcore.entity.ReserveEntity;
import ru.sbrf.sell.appcore.service.ReserveService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/reserve")
public class ReserveController {
    @Autowired
    private ReserveService service;



    @PostMapping("/create")
    public ResponseEntity<ReserveEntity> createReserve(@RequestParam("user")String user,  @RequestParam("employed") String employed, @RequestParam("category") int category, @RequestParam("time") String time){
        var reserve = service.createReserve(user, employed, category, LocalDateTime.parse(time).truncatedTo(ChronoUnit.MINUTES));
        if(reserve == null){
            return response404(null);
        }
        return response200(reserve);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteReserve (@RequestParam("user")String user, @RequestParam("time") String time){
        service.deleteReserve(user, LocalDateTime.parse(time).truncatedTo(ChronoUnit.MINUTES));
        return response200("Deleted!");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ReserveEntity>> getAllReserves (@RequestParam("user")String user){
        var reserve = service.getByUser(user);
        if(reserve == null){
            return response404(null);
        }
        return response200(reserve);
    }

    @GetMapping("/getByEmployee")
    public ResponseEntity<List<ReserveEntity>> getByEmployee (@RequestParam("employee")String employee){
        var reserve = service.getByEmployee(employee);
        if(reserve == null){
            return response404(null);
        }
        return response200(reserve);
    }

    @PostMapping(value = "/{id}/pay",consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ReserveEntity> addPayment(@PathVariable("id") String id, @RequestParam("amount") int amount)
    {
        var reserveEntity = service.pay(id, amount);
        return response200(reserveEntity);
    }

    private <T>ResponseEntity<T>  response200(T content){
        return new ResponseEntity<T>(content, HttpStatus.OK);
    }

    private <T> ResponseEntity<T>  response404(T content){
        return new ResponseEntity<T>(content, HttpStatus.NOT_FOUND);
    }
}
