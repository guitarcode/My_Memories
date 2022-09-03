package toyproject.memories.dto.schedule;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ScheduleStorageCreateDto {

    @NotEmpty
    private String title;
    @NotEmpty
    private List<ScheduleItemCreateDto> scheduleItems = new ArrayList<>();
}
