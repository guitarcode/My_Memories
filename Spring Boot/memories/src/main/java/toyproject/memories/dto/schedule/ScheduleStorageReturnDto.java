package toyproject.memories.dto.schedule;

import lombok.Builder;
import lombok.Getter;
import toyproject.memories.domain.schedule.ScheduleStorage;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ScheduleStorageReturnDto {
    private final Long storage_id;
    private final String name;
    private final List<ScheduleItemCreateDto> items;

    @Builder
    public ScheduleStorageReturnDto(ScheduleStorage scheduleStorage) {
        this.storage_id = scheduleStorage.getId();
        this.name = scheduleStorage.getTitle();
        this.items = scheduleStorage.getScheduleItems().stream()
                .map(item -> new ScheduleItemCreateDto(item))
                .collect(Collectors.toList());
    }
}
