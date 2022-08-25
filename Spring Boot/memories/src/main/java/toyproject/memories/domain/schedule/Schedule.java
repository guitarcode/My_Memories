package toyproject.memories.domain.schedule;

import toyproject.memories.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @NotNull
    private String name;

    @Column(name = "sub_name")
    private String subName;

    @Column(name = "start_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_time")
    private LocalDateTime endDateTime;


    @Enumerated(EnumType.STRING)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    private ScheduleStorage scheduleStorage;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
