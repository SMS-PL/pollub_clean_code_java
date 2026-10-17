package pl.pollub.cleancode.infrastructure.cron;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table
public class CronEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private CronTypeAction cronType;
    private String emailAddress;
    private String content;
    private CronStatus status;


    private Long scheduledAt;
    private Long executedAt;
}
