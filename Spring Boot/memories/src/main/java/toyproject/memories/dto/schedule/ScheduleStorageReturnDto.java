package toyproject.memories.dto.schedule;

import lombok.Builder;
import lombok.Getter;
import toyproject.memories.domain.schedule.ScheduleStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ScheduleStorageReturnDto {
    private final Long storage_id;
    private final String name;
    private final String subName;
    private final List<ScheduleItemCreateAndReturnDto> scheduleItems;

    @Builder
    public ScheduleStorageReturnDto(ScheduleStorage scheduleStorage) {
        this.storage_id = scheduleStorage.getId();
        this.name = scheduleStorage.getName();
        this.subName = scheduleStorage.getSubName();
        this.scheduleItems = scheduleStorage.getScheduleItems().stream()
                .map(item -> new ScheduleItemCreateAndReturnDto(item))
                .collect(Collectors.toList());
    }
}
