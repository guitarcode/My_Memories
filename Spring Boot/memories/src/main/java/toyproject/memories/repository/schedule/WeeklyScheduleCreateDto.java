package toyproject.memories.repository.schedule;

import lombok.Getter;

@Getter
public class WeeklyScheduleCreateDto {
    private Long storageId;
    private String startDay;
    private String endDay;
}
