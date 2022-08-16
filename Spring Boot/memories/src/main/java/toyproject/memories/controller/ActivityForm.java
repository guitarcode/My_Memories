package toyproject.memories.controller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ActivityForm {
    private String activityContent;
    private String tagContent;
    private LocalDateTime dateTime;
}
