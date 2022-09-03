package toyproject.memories.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.memories.domain.schedule.ScheduleItem;

import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ScheduleItemCreateDto {
    @NotEmpty
    private String title;

    @NotEmpty
    private String startTime;

    @NotEmpty
    private String endTime;

    @NotEmpty
    private String endDay;

    @NotEmpty
    private String startDay;

    @NotEmpty
    private String importance;

    public LocalTime startTimeParse(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(this.startTime, formatter);
    }

    public LocalTime endTimeParse(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(this.endTime, formatter);
    }

    public ScheduleItemCreateDto(ScheduleItem scheduleItem) {
        this.title = scheduleItem.getTitle();
        this.startTime = scheduleItem.getStartTime().toString();
        this.endTime = scheduleItem.getEndTime().toString();
        this.startDay = scheduleItem.getStartDay().name();
        this.endDay = scheduleItem.getEndDay().name();
        this.importance = scheduleItem.getImportance().name();
    }
}
