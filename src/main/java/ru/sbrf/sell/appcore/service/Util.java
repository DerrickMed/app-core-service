package ru.sbrf.sell.appcore.service;

import org.springframework.stereotype.Component;
import ru.sbrf.sell.appcore.controller.CategoryController;
import ru.sbrf.sell.appcore.controller.ReserveController;
import ru.sbrf.sell.appcore.controller.ScheduleController;
import ru.sbrf.sell.appcore.dto.CategoryDto;
import ru.sbrf.sell.appcore.dto.CreateScheduleDto;
import ru.sbrf.sell.appcore.dto.ReserveDto;
import ru.sbrf.sell.appcore.entity.ReserveEntity;
import ru.sbrf.sell.appcore.entity.ScheduleEntity;
import ru.sbrf.sell.appcore.entity.pojo.SchedulePojo;
import ru.sbrf.sell.appcore.entity.pojo.SessionPojo;

import javax.annotation.PostConstruct;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@Component
public class Util {

    ScheduleController scheduleController;
    ReserveController reserveController;

    CategoryController categoryController;



    public Util(ScheduleController scheduleController,  ReserveController reserveC, CategoryController categoryController) {
        this.scheduleController = scheduleController;
        this.reserveController = reserveC;
        this.categoryController = categoryController;
    }

    @PostConstruct
    public void testBd() {
        System.out.println("Начал работу метод testBd");
        CreateScheduleDto dto = new CreateScheduleDto();
        dto.setStartTime(LocalTime.of(9,0).toString());
        dto.setEndTime(LocalTime.of(18,0).toString());
        dto.setSessionDuration(40);
        scheduleController.createSchedule("Bob", 1, dto);
        System.out.println(scheduleController.getSchedule("Bob", 1));
        reserveController.createReserve("123", "test", 1,LocalDateTime.of(LocalDate.now(), LocalTime.of(14,00)).toString());
        System.out.println(reserveController.getAllReserves("Bob"));
        categoryController.createCategory(new CategoryDto("Уборка",true));
        categoryController.createCategory(new CategoryDto("Ремонт телефонов",true));

        System.out.println("Завершение работы метода testBd");
    }
}
