package toyproject.memories.domain.schedule;

import toyproject.memories.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    private String name;

    @Column(name = "sub_name")
    private String subName;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    private ScheduleItem scheduleItem;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
