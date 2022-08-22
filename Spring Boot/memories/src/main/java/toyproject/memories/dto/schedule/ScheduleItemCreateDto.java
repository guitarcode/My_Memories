package toyproject.memories.dto.schedule;

import lombok.Getter;
import toyproject.memories.domain.schedule.ScheduleItem;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
public class ScheduleItemCreateDto {
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek;
    private String property;
}
