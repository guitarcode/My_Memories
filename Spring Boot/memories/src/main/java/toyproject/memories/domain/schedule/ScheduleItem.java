package toyproject.memories.domain.schedule;

import lombok.*;
import toyproject.memories.domain.user.User;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@Table(name = "schedule_item")
public class ScheduleItem {
    @Id
    @GeneratedValue
    @Column(name = "schedule_item_id")
    private Long id;

    private String title;

    @Column(name = "start_day")
    @Enumerated(EnumType.STRING)
    private DayOfWeek startDay;

    @Column(name = "end_day")
    @Enumerated(EnumType.STRING)
    private DayOfWeek endDay;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_storage_id")
    private ScheduleStorage storage;

    protected void setStorageAndUser(ScheduleStorage scheduleStorage){
        this.storage = scheduleStorage;
        this.user = scheduleStorage.getUser();
    }

    public void change(String title, DayOfWeek startDay, DayOfWeek endDay, LocalTime startTime, LocalTime endTime, Importance importance) {
        this.title = title;
        this.startDay = startDay;
        this.endDay = endDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.importance = importance;
    }
}
