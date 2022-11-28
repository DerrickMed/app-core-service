package ru.sbrf.sell.appcore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbrf.sell.appcore.dto.CreateScheduleDto;
import ru.sbrf.sell.appcore.dto.SchedulePojoDto;
import ru.sbrf.sell.appcore.service.ScheduleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService service;

    @GetMapping
    public ResponseEntity<List<SchedulePojoDto>> getSchedule(@RequestParam("user")String user, @RequestParam("category") int category){
        var schedule = service.getScheduleByUserAndCategory(user, category);
        if(schedule == null){
            return response404(null);
        }
        return response200(schedule);
    }

    @GetMapping("/{day}")
    public ResponseEntity<SchedulePojoDto> getActiveSchedule(@RequestParam("user")String user, @RequestParam("category") int category, @PathVariable(name = "day") LocalDate day){
//        var schedule = service.getScheduleByUserAndCategory(user, category);
//        if(schedule == null){
//            return response404(null);
//        }
//        return response200(schedule);

        return response200(service.getScheduleByUserAndCategoryAndDate(user,category,day));
    }

    @PostMapping
    public ResponseEntity<List<SchedulePojoDto>> createSchedule(@RequestParam("user")String user, @RequestParam("category") int category, @RequestBody CreateScheduleDto createScheduleDto){
        var schedule = service.createSchedule(user,category,createScheduleDto);

        if(schedule == null){
            return response404(null);
        }
        return response200(schedule);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSchedule (@RequestParam("user")String user, @RequestParam("category") int category){
        service.deleteSchedule(user, category);
        return response200("Deleted!");
    }

//    @PutMapping("/{day}")
//    public ResponseEntity<SchedulePojo> updateScheduleDay(@PathVariable(name="day") String day, @RequestParam("user")String user, @RequestParam("category") String category, @RequestParam(name = "active", required = false) String active){
//        SchedulePojo schedulePojo = service.updateScheduleDay(user,category, day, active);
//        return response200(schedulePojo);
//    }

    @PutMapping
    public ResponseEntity<List<SchedulePojoDto>> updateScheduleDay(@RequestParam("user")String user, @RequestParam("category") int category, @RequestBody List<SchedulePojoDto> pojoList){
       var content = service.updateSchedule(user,category, pojoList);
        return response200(content);
    }


    private <T>ResponseEntity<T> response200(T content ){
        return new ResponseEntity<T>(content, HttpStatus.OK);
    }

    private <T> ResponseEntity<T>  response404(T content){
        return new ResponseEntity<T>(content, HttpStatus.NOT_FOUND);
    }
}
