package toyproject.memories.domain.schedule;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.memories.domain.user.User;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schedule_item")
public class ScheduleItem {
    @Id
    @GeneratedValue
    @Column(name = "schedule_item_id")
    private Long id;

    @Column(name = "start_day")
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek startDay;

    @Column(name = "end_day")
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek endDay;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_storage_id")
    private ScheduleStorage storage;

    @Builder
    public ScheduleItem(LocalTime startTime, LocalTime endTime, DayOfWeek startDay, DayOfWeek endDay, Property property, User user) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDay = startDay;
        this.endDay = endDay;
        this.property = property;
        this.user = user;
    }

    protected void setStorageAndUser(ScheduleStorage scheduleStorage){
        this.storage = scheduleStorage;
        this.user = scheduleStorage.getUser();
    }
}
