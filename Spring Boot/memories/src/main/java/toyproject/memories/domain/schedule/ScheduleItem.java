package toyproject.memories.domain.schedule;

import lombok.Getter;
import toyproject.memories.domain.user.User;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Table(name = "schedule_item")
public class ScheduleItem {
    @Id
    @GeneratedValue
    @Column(name = "schedule_item_id")
    private Long id;

    private String name;

    @Column(name = "sub_name")
    private String subName;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Enumerated(EnumType.STRING)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
