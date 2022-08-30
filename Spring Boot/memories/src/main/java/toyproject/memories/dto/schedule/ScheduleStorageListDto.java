package toyproject.memories.dto.schedule;

import lombok.Getter;
import toyproject.memories.domain.schedule.ScheduleStorage;

@Getter
public class ScheduleStorageListDto{
    private String title;

    public ScheduleStorageListDto(ScheduleStorage scheduleStorage){
        this.title = scheduleStorage.getName();
    }
}
