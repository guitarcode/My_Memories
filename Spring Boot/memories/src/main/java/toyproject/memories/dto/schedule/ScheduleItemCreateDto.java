package toyproject.memories.dto.schedule;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
public class ScheduleItemCreateDto {
    private String name;
    private String subName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek;
    private String property;
}
