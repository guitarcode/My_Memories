package toyproject.memories.dto.schedule;

import lombok.Getter;
import toyproject.memories.domain.schedule.ScheduleItem;

import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ScheduleItemReturnDto {

    private Long id;

    private String title;

    private String startDay;
    private String startTime;

    private String endDay;
    private String endTime;

    private String importance;


    public ScheduleItemReturnDto(ScheduleItem scheduleItem) {
        this.id = scheduleItem.getId();
        this.title = scheduleItem.getTitle();
        this.startTime = scheduleItem.getStartTime().toString();
        this.endTime = scheduleItem.getEndTime().toString();
        this.startDay = scheduleItem.getStartDay().name();
        this.endDay = scheduleItem.getEndDay().name();
        this.importance = scheduleItem.getImportance().name();
    }
}