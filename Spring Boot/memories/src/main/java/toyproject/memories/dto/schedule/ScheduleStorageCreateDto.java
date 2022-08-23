package toyproject.memories.dto.schedule;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import toyproject.memories.domain.schedule.ScheduleStorage;

import java.util.ArrayList;
import java.util.List;

@Getter

public class ScheduleStorageCreateDto {
    private String name;
    private String subName;
    private List<ScheduleItemCreateAndReturnDto> scheduleItems = new ArrayList<>();
}
