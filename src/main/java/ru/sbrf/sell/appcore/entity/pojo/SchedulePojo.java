package ru.sbrf.sell.appcore.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SchedulePojo implements Serializable {
    String day;
    boolean active;
    List<SessionPojo> sessions;

    public SchedulePojo(String day, boolean active, List<SessionPojo> sessions) {
        this.day = day;
        this.active = active;
        this.sessions = sessions;
    }

    public SchedulePojo() {
    }
}
