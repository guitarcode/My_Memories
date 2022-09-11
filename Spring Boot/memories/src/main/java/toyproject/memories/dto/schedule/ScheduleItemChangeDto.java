package toyproject.memories.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.memories.domain.schedule.ScheduleItem;

import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ScheduleItemChangeDto {
    private Long itemId;

    private String title;

    private String startTime;

    private String endTime;
    private String endDay;

    private String startDay;
    private String importance;

    public LocalTime startTimeParse(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(this.startTime, formatter);
    }

    public LocalTime endTimeParse(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(this.endTime, formatter);
    }

    public ScheduleItemChangeDto(ScheduleItem scheduleItem) {
        this.title = scheduleItem.getTitle();
        this.startTime = scheduleItem.getStartTime().toString();
        this.endTime = scheduleItem.getEndTime().toString();
        this.startDay = scheduleItem.getStartDay().name();
        this.endDay = scheduleItem.getEndDay().name();
        this.importance = scheduleItem.getImportance().name();
    }
}
