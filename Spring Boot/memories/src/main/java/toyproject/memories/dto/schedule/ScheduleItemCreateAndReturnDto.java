package toyproject.memories.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toyproject.memories.domain.schedule.ScheduleItem;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ScheduleItemCreateAndReturnDto {
    private String startTime;
    private String endTime;
    private String startDay;
    private String endDay;
    private String property;

    public LocalTime startTimeParse(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(this.startTime, formatter);
    }

    public LocalTime endTimeParse(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(this.endTime, formatter);
    }

    public ScheduleItemCreateAndReturnDto(ScheduleItem scheduleItem) {
        this.startTime = scheduleItem.getStartTime().toString();
        this.endTime = scheduleItem.getEndTime().toString();
        this.startDay = scheduleItem.getStartDay().name();
        this.endDay = scheduleItem.getEndDay().name();
        this.property = scheduleItem.getProperty().name();
    }
}
