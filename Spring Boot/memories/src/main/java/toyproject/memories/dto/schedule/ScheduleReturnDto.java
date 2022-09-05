package toyproject.memories.dto.schedule;

import lombok.Getter;
import toyproject.memories.domain.schedule.Schedule;

@Getter
public class ScheduleReturnDto {
    private String title;
    private String subTitle;
    private String start;
    private String end;
    private String importance;

    public ScheduleReturnDto(Schedule schedule) {
        this.title = schedule.getTitle();
        this.subTitle = schedule.getSubTitle();
        this.start = schedule.getStartDateTime().toString();
        this.end = schedule.getEndDateTime().toString();
        this.importance = schedule.getImportance().name();
    }
}
