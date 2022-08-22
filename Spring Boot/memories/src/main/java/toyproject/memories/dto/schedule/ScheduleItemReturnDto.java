package toyproject.memories.dto.schedule;

import lombok.Getter;
import toyproject.memories.domain.schedule.Property;
import toyproject.memories.domain.schedule.ScheduleItem;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
public class ScheduleItemReturnDto {
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek dayOfWeek;
    private Property property;

    public ScheduleItemReturnDto(ScheduleItem scheduleItem) {
        this.startTime = scheduleItem.getStartTime();
        this.endTime = scheduleItem.getEndTime();
        this.dayOfWeek = scheduleItem.getDayOfWeek();
        this.property = scheduleItem.getProperty();
    }
}
