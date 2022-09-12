package toyproject.memories.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.memories.domain.schedule.Importance;
import toyproject.memories.domain.schedule.ScheduleItem;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleItemCreateDto {
    @NotEmpty
    private String title;

    @NotEmpty
    private String startDateTime;

    @NotEmpty
    private String endDateTime;

    @NotEmpty
    private String importance;

    public ScheduleItem toEntity(){
        LocalDateTime start = LocalDateTime.parse(this.startDateTime);
        LocalDateTime end = LocalDateTime.parse(this.endDateTime);
        return ScheduleItem.builder()
                .title(this.title)
                .startTime(start.toLocalTime())
                .startDay(start.getDayOfWeek())
                .endTime(end.toLocalTime())
                .endDay(end.getDayOfWeek())
                .importance(Importance.valueOf(this.importance))
                .build();
    }
}
