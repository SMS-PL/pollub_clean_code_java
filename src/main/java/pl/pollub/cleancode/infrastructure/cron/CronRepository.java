package pl.pollub.cleancode.infrastructure.cron;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CronRepository extends JpaRepository<CronEntity, Long> {


    @Query("SELECT c FROM CronEntity c WHERE c.executedAt IS NULL")
    Iterable<CronEntity> findNotExecuted();
}
