package toyproject.memories.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toyproject.memories.domain.schedule.ScheduleItem;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ScheduleItemCreateAndReturnDto {
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek;
    private String property;

    public ScheduleItemCreateAndReturnDto(ScheduleItem scheduleItem) {
        this.startTime = scheduleItem.getStartTime();
        this.endTime = scheduleItem.getEndTime();
        this.dayOfWeek = scheduleItem.getDayOfWeek().name();
        this.property = scheduleItem.getProperty().name();
    }
}
