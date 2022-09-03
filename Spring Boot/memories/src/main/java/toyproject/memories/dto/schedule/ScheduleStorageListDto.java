package toyproject.memories.dto.schedule;

import lombok.Getter;
import toyproject.memories.domain.schedule.ScheduleStorage;

@Getter
public class ScheduleStorageListDto{
    private Long id;
    private String title;

    public ScheduleStorageListDto(ScheduleStorage scheduleStorage){
        this.id = scheduleStorage.getId();
        this.title = scheduleStorage.getTitle();
    }
}
