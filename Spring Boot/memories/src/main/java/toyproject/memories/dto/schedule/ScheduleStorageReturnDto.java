package toyproject.memories.dto.schedule;

import lombok.Builder;
import lombok.Getter;
import toyproject.memories.domain.schedule.ScheduleStorage;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ScheduleStorageReturnDto {
    private final Long storageId;
    private final String title;
    private final List<ScheduleItemReturnDto> items;

    @Builder
    public ScheduleStorageReturnDto(ScheduleStorage scheduleStorage) {
        this.storageId = scheduleStorage.getId();
        this.title = scheduleStorage.getTitle();
        this.items = scheduleStorage.getScheduleItems().stream()
                .map(ScheduleItemReturnDto::new)
                .collect(Collectors.toList());
    }
}
