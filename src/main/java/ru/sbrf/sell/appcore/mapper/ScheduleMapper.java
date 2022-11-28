package ru.sbrf.sell.appcore.mapper;

import org.hibernate.Session;
import ru.sbrf.sell.appcore.dto.ScheduleDto;
import ru.sbrf.sell.appcore.dto.SchedulePojoDto;
import ru.sbrf.sell.appcore.dto.SessionPojoDto;
import ru.sbrf.sell.appcore.entity.ScheduleEntity;
import ru.sbrf.sell.appcore.entity.pojo.SchedulePojo;
import ru.sbrf.sell.appcore.entity.pojo.SessionPojo;

import java.time.LocalTime;
import java.util.ArrayList;

public class ScheduleMapper {

    public ScheduleDto entityToDto(ScheduleEntity entity){
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setUser(entity.getUser());
        scheduleDto.setCategory(entity.getCategory());
        scheduleDto.setSchedule(new ArrayList<>());
        for( SchedulePojo schedulePojo : entity.getSchedule() ){
            SchedulePojoDto schedulePojoDto = new SchedulePojoDto();
            schedulePojoDto.setActive(schedulePojo.isActive());
            schedulePojoDto.setDay(schedulePojo.getDay());
            schedulePojoDto.setSessions( new ArrayList<>());
            var sessions = schedulePojo.getSessions();
            for(SessionPojo sessionPojo : sessions){
               schedulePojoDto.getSessions().add(sessionEntityToDto(sessionPojo));
            }

          scheduleDto.getSchedule().add(schedulePojoDto);

        }
        return scheduleDto;
    }

    public SessionPojoDto sessionEntityToDto(SessionPojo sessionPojo){
        SessionPojoDto sessionPojoDto = new SessionPojoDto();
        sessionPojoDto.setActive(sessionPojo.isActive());
            String hour = String.valueOf(sessionPojo.getTime().getHour());
            String minutes = String.valueOf(sessionPojo.getTime().getMinute());

            if(hour.length() == 1){
                hour = "0" + hour;
            }
            if(minutes.length() == 1){
                minutes = "0" + minutes;
            }
            sessionPojoDto.setTime(hour+":"+minutes);

            return sessionPojoDto;
    }

    public SchedulePojo schedulePojoDtoToSchedulePojo( SchedulePojoDto schedulePojoDto){

        SchedulePojo schedulePojo = new SchedulePojo();
        schedulePojo.setDay(schedulePojoDto.getDay());
        schedulePojo.setActive(schedulePojoDto.isActive());

        schedulePojo.setSessions(new ArrayList<>());

        for( SessionPojoDto sessionPojoDto : schedulePojoDto.getSessions()){
            SessionPojo sessionPojo = new SessionPojo();
            sessionPojo.setActive(sessionPojoDto.isActive());
            var time = sessionPojoDto.getTime().split(":");
            LocalTime localTime = LocalTime.of(Integer.parseInt(time[0])  , Integer.parseInt(time[1]));
            sessionPojo.setTime(localTime);
            schedulePojo.getSessions().add(sessionPojo);
        }


        return schedulePojo;
    }


}
