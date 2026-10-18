package pl.pollub.cleancode.domain.ticket;

import jakarta.persistence.*;
import lombok.*;
import pl.pollub.cleancode.common.BaseEntity;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity extends BaseEntity {
    @Column
    String content;

    @Column
    @Enumerated(EnumType.STRING)
    PriorityEnum priority;
}
