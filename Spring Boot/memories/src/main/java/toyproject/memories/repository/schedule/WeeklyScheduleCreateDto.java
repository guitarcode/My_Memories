package toyproject.memories.repository.schedule;

import lombok.Getter;

@Getter
public class WeeklyScheduleCreateDto {
    private Long storage_id;
    private String startDay;
    private String endDay;
}
