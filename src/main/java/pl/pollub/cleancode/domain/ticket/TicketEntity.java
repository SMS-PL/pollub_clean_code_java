package pl.pollub.cleancode.domain.ticket;

import jakarta.persistence.*;
import lombok.*;
import pl.pollub.cleancode.common.BaseEntity;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String content;

    @Column
    @Enumerated(EnumType.STRING)
    PriorityEnum priority;
}
