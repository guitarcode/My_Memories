package toyproject.memories.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleStorageChangeDto {
    private Long storageId;
    private String title;
    private List<ScheduleItemChangeDto> items;
}
