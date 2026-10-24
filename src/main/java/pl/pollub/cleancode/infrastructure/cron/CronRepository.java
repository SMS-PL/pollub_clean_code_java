package pl.pollub.cleancode.infrastructure.cron;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CronRepository extends JpaRepository<CronEntity, Long> {

    List<CronEntity> findByExecutedAtIsNullAndStatus(CronStatus status);
}
