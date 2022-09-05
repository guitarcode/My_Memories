package toyproject.memories.controller.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.memories.domain.schedule.Schedule;
import toyproject.memories.dto.schedule.ScheduleReturnDto;
import toyproject.memories.repository.schedule.WeeklyScheduleCreateDto;
import toyproject.memories.service.schedule.ScheduleService;

import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Map<String, Object> createWeeklySchedule(@RequestBody WeeklyScheduleCreateDto weeklyScheduleCreateDto,
                                                    @AuthenticationPrincipal User user){
        return scheduleService.createWeeklySchedule(weeklyScheduleCreateDto, user.getUsername());
    }
}
