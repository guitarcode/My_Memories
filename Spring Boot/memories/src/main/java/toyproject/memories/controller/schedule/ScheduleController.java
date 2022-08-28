package toyproject.memories.controller.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.memories.dto.schedule.ScheduleItemCreateDto;
import toyproject.memories.service.schedule.ScheduleItemService;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleItemService scheduleItemService;

    @PostMapping("/scheduleitem")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Long create(@RequestBody ScheduleItemCreateDto scheduleItemCreateDto,
                       @AuthenticationPrincipal User user){
        return scheduleItemService.createScheduleItem(scheduleItemCreateDto,user.getUsername());
    }
}
