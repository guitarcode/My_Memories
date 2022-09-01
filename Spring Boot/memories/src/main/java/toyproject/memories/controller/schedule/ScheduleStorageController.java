package toyproject.memories.controller.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import toyproject.memories.dto.schedule.ScheduleStorageCreateDto;
import toyproject.memories.dto.schedule.ScheduleStorageListDto;
import toyproject.memories.dto.schedule.ScheduleStorageReturnDto;
import toyproject.memories.service.schedule.ScheduleStorageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleStorageController {

    private final ScheduleStorageService scheduleStorageService;

    @PostMapping("/schedule/storage")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ScheduleStorageReturnDto create(@RequestBody ScheduleStorageCreateDto scheduleStorageCreateDto
                                           ,@AuthenticationPrincipal User user){
        return scheduleStorageService.createScheduleStorage(scheduleStorageCreateDto, user.getUsername());
    }

    @GetMapping("/schedule/storage")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<ScheduleStorageListDto> storageList(@AuthenticationPrincipal User user){
        return scheduleStorageService.storageList(user.getUsername());
    }

    @GetMapping("/schedule/storage/{storageId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ScheduleStorageReturnDto storageDetail(@PathVariable("storageId") Long id,
                                                          @AuthenticationPrincipal User user){
        return scheduleStorageService.storageDetail(id, user.getUsername());
    }
}
