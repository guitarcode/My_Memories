package toyproject.memories.controller.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import toyproject.memories.domain.schedule.Schedule;
import toyproject.memories.dto.schedule.ScheduleReturnDto;
import toyproject.memories.repository.schedule.WeeklyScheduleCreateDto;
import toyproject.memories.service.CreateMap;
import toyproject.memories.service.schedule.ScheduleService;

import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/schedule")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Map<String, Object> scheduleList(@AuthenticationPrincipal User user){
        return CreateMap.successMap(scheduleService.scheduleList(user.getUsername()));
    }

    @PostMapping("/schedule")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Map<String, Object> createWeeklySchedule(@RequestBody WeeklyScheduleCreateDto weeklyScheduleCreateDto,
                                                    @AuthenticationPrincipal User user){
        return scheduleService.createWeeklySchedule(weeklyScheduleCreateDto, user.getUsername());
    }
}
