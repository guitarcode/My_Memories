package toyproject.memories.dto.schedule;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ScheduleStorageCreateDto {
    private String title;
    private String subName;
    private List<ScheduleItemCreateDto> scheduleItems = new ArrayList<>();
}
