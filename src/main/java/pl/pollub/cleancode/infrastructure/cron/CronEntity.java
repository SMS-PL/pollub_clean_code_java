package pl.pollub.cleancode.infrastructure.cron;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table
public class CronEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private CronTypeAction cronType;
    private String emailAddress;
    @Column(length = 2000)
    private String content;
    private CronStatus status;


    private Long scheduledAt;
    private Long executedAt;

    public static Builder builder() {
        return new Builder();
    }

    //[Builder]
    public static class Builder {
        private Long id;
        private CronTypeAction cronType;
        private String emailAddress;
        private String content;
        private CronStatus status;
        private Long scheduledAt;
        private Long executedAt;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder cronType(CronTypeAction cronType) {
            this.cronType = cronType;
            return this;
        }

        public Builder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder status(CronStatus status) {
            this.status = status;
            return this;
        }

        public Builder scheduledAt(Long scheduledAt) {
            this.scheduledAt = scheduledAt;
            return this;
        }

        public Builder executedAt(Long executedAt) {
            this.executedAt = executedAt;
            return this;
        }

        public CronEntity build() {
            return new CronEntity(id, cronType, emailAddress, content, status, scheduledAt, executedAt);
        }
    }
}
