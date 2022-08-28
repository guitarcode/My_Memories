package toyproject.memories.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.memories.domain.schedule.ScheduleItem;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ScheduleItemCreateDto {
    private String name;
    private String startTime;
    private String endTime;
    private String endDay;
    private String startDay;
    private String property;

    public LocalTime startTimeParse(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(this.startTime, formatter);
    }

    public LocalTime endTimeParse(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(this.endTime, formatter);
    }

    public ScheduleItemCreateDto(ScheduleItem scheduleItem) {
        this.name = scheduleItem.getName();
        this.startTime = scheduleItem.getStartTime().toString();
        this.endTime = scheduleItem.getEndTime().toString();
        this.startDay = scheduleItem.getStartDay().name();
        this.endDay = scheduleItem.getEndDay().name();
//        this.property = scheduleItem.getProperty().name();
    }
}
