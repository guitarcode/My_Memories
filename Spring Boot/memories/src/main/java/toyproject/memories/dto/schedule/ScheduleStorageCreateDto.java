package toyproject.memories.dto.schedule;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter

public class ScheduleStorageCreateDto {
    private String name;
    private String subName;
    private List<ScheduleItemCreateDto> scheduleItems = new ArrayList<>();
}
