package ru.sbrf.sell.appcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sbrf.sell.appcore.entity.ScheduleEntity;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String> {
        ScheduleEntity getScheduleByUserAndCategory(String user, int category);
        void removeAllByUserAndCategory(String user, int category);
}
