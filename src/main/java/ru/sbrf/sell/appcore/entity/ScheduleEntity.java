package ru.sbrf.sell.appcore.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.sbrf.sell.appcore.entity.pojo.SchedulePojo;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "schedule_table", uniqueConstraints = {
        @UniqueConstraint(name = "uNameAndCategory", columnNames = {"user_name", "category"})
})
@TypeDef(name = "json", typeClass = JsonType.class)
public class ScheduleEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    String id;
    @Column(name = "user_name")
    String user;
    @Column
    Integer category;

//    Для postgre jsonb формат
    @Column(name = "schedule", columnDefinition = "json")
    @Type( type = "json")
    List<SchedulePojo> schedule;


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ScheduleEntity that = (ScheduleEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
