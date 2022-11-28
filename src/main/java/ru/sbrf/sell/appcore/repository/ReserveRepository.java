package ru.sbrf.sell.appcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sbrf.sell.appcore.entity.ReserveEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReserveRepository extends JpaRepository<ReserveEntity, String> {
    void removeByUserAndTime(String user, LocalDateTime time);

    List<ReserveEntity> getAllByUser(String user);

    List<ReserveEntity> getAllByEmployedAndCategory(String employed, int category);

    List<ReserveEntity> getByUser(String user);

    List<ReserveEntity> getByEmployed(String employed);
}
