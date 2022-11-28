package ru.sbrf.sell.appcore.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

@Data
public class SessionPojo implements Serializable {
    LocalTime time;
    boolean active;

    public SessionPojo(LocalTime time, boolean active) {
        this.time = time;
        this.active = active;
    }

    public SessionPojo() {
    }
}
