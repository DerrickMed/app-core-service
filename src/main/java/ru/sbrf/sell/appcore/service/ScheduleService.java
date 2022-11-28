package ru.sbrf.sell.appcore.service;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.sell.appcore.dto.CreateScheduleDto;
import ru.sbrf.sell.appcore.dto.SchedulePojoDto;
import ru.sbrf.sell.appcore.entity.ReserveEntity;
import ru.sbrf.sell.appcore.entity.ScheduleEntity;
import ru.sbrf.sell.appcore.entity.pojo.SchedulePojo;
import ru.sbrf.sell.appcore.entity.pojo.SessionPojo;
import ru.sbrf.sell.appcore.mapper.ScheduleMapper;
import ru.sbrf.sell.appcore.repository.ReserveRepository;
import ru.sbrf.sell.appcore.repository.ScheduleRepository;

//import javax.transaction.Transactional;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository repository;

    @Autowired
    private ReserveRepository reserveRepository;

    public List<SchedulePojoDto> getScheduleByUserAndCategory(String user, int category){
        var schedule = repository.getScheduleByUserAndCategory(user,category);
        if( schedule == null ){
            return null;
        }

        var dto = new ScheduleMapper().entityToDto(schedule);

        return dto.getSchedule();
    }

    public SchedulePojoDto getScheduleByUserAndCategoryAndDate(String employed, int category, LocalDate date){
        var schedule = repository.getScheduleByUserAndCategory(employed,category);
        LocalDateTime startTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0,0);
        LocalDateTime endTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23,59);
        //var reserveList = reserveRepository.getAllByEmployedAndCategoryAndTimeBetween(employed,category,startTime,endTime);
        var reserveList = reserveRepository.getAllByEmployedAndCategory(employed, category);
        reserveList =  reserveList.stream().filter(s -> s.getTime().isAfter(startTime) && s.getTime().isBefore(endTime)).toList();
        //var reserveList = reserveRepository.getByEmployed(employed);

        for(ReserveEntity reserve : reserveList){
            var schedulePojo = schedule.getSchedule().stream()
                            .filter(s-> s.getDay().equals(reserve.getTime().getDayOfWeek().toString().toUpperCase()))
                            .findFirst().orElse(null);

            if(schedulePojo == null){
                continue;
            }

             var session = schedulePojo.getSessions().stream().filter( s->  s.getTime().getHour() == reserve.getTime().getHour()
                                                                                            && s.getTime().getMinute() == reserve.getTime().getMinute() )
                                        .findFirst().orElse(null);

            if( session != null){
                session.setActive(false);
            }
        }

        return new ScheduleMapper().entityToDto(schedule).getSchedule().stream().filter(s -> s.getDay().equals(date.getDayOfWeek().toString().toUpperCase())).findFirst().orElse(null);
    }

    @Transactional
    public List<SchedulePojoDto> createSchedule(String user, int category, CreateScheduleDto dto){
        var startTime = LocalTime.parse(dto.getStartTime(), DateTimeFormatter.ISO_LOCAL_TIME);
        var endTime = LocalTime.parse(dto.getEndTime(), DateTimeFormatter.ISO_LOCAL_TIME);
        List<SchedulePojo> schedule = new ArrayList<>();
        List<SessionPojo> sessions = new ArrayList<>();
        //Добавляем данные в одну сущность, потом изменить
        ScheduleEntity scheduleEntity = repository.getScheduleByUserAndCategory(user, category);

        if( scheduleEntity  == null ){
            scheduleEntity = new ScheduleEntity();
        }


        scheduleEntity.setUser(user);
        scheduleEntity.setCategory(category);

//        Добавить проверки на время

        for( ;startTime.plusMinutes(dto.getSessionDuration()).compareTo(endTime) < 0; startTime = startTime.plusMinutes(dto.getSessionDuration()) ){
//            String hour = String.valueOf(startTime.getHour());
//            String minutes = String.valueOf(startTime.getMinute());
//
//            if(hour.length() == 1){
//                hour = "0" + hour;
//            }
//            if(minutes.length() == 1){
//                minutes = "0" + minutes;
//            }

            sessions.add(new SessionPojo(startTime,true));
        }

        schedule.add(new SchedulePojo(String.valueOf(DayOfWeek.MONDAY),true, sessions));
        schedule.add(new SchedulePojo(String.valueOf(DayOfWeek.TUESDAY),true, sessions));
        schedule.add(new SchedulePojo(String.valueOf(DayOfWeek.WEDNESDAY),true, sessions));
        schedule.add(new SchedulePojo(String.valueOf(DayOfWeek.THURSDAY),true, sessions));
        schedule.add(new SchedulePojo(String.valueOf(DayOfWeek.FRIDAY),true, sessions));
        schedule.add(new SchedulePojo(String.valueOf(DayOfWeek.SATURDAY),true, sessions));
        schedule.add(new SchedulePojo(String.valueOf(DayOfWeek.SUNDAY),true, sessions));

        scheduleEntity.setSchedule(schedule);

        repository.save(scheduleEntity);

          var scheduleDto = new ScheduleMapper().entityToDto(scheduleEntity);

        return scheduleDto.getSchedule();
    }

    @Transactional
    public void deleteSchedule(String user, int category){
        repository.removeAllByUserAndCategory(user, category);
    }

    @Transactional
    public List<SchedulePojoDto> updateSchedule(String user, int category, List<SchedulePojoDto> modifiedSchedule){
        ScheduleEntity scheduleEntity = repository.getScheduleByUserAndCategory(user,category);

        for( SchedulePojo schedulePojo : scheduleEntity.getSchedule()){
             SchedulePojo modifiedSchedulePojo = modifiedSchedule.stream()
                     .map(s-> new ScheduleMapper().schedulePojoDtoToSchedulePojo(s))
                     .filter(s-> s.getDay().equals(schedulePojo.getDay()))
                     .findFirst().orElse(null);

             if(modifiedSchedulePojo == null){
                 continue;
             }

             schedulePojo.setActive(modifiedSchedulePojo.isActive());

             if(modifiedSchedulePojo.getSessions() != null){
                 for(SessionPojo modifiedSession : modifiedSchedulePojo.getSessions()){
                     SessionPojo session = schedulePojo.getSessions().stream()
                                     .filter(s -> s.getTime().equals(modifiedSession.getTime()))
                                     .findFirst().orElse(null);

                     if(session == null){
                         continue;
                     }

                     session.setActive(modifiedSession.isActive());
                 }
             }

        }

       repository.save(scheduleEntity);
        var dto = new ScheduleMapper().entityToDto(scheduleEntity);
       return dto.getSchedule();
    }
}
