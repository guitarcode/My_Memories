package toyproject.memories.dto.schedule;

import lombok.Builder;
import lombok.Getter;
import toyproject.memories.domain.schedule.ScheduleStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ScheduleStorageReturnDto {
    private Long storage_id;
    private String name;
    private String subName;
    private List<ScheduleItemReturnDto> scheduleItems;

    @Builder
    public ScheduleStorageReturnDto(ScheduleStorage scheduleStorage) {
        this.storage_id = scheduleStorage.getId();
        this.name = scheduleStorage.getName();
        this.subName = scheduleStorage.getSubName();
        this.scheduleItems = scheduleStorage.getScheduleItems().stream()
                .map(item -> new ScheduleItemReturnDto(item))
                .collect(Collectors.toList());
    }
}
