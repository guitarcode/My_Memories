package toyproject.memories.controller.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import toyproject.memories.dto.schedule.ScheduleStorageChangeDto;
import toyproject.memories.dto.schedule.ScheduleStorageCreateDto;
import toyproject.memories.service.schedule.ScheduleStorageService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@RequestMapping("/api")
public class ScheduleStorageController {

    private final ScheduleStorageService scheduleStorageService;

    @PostMapping("/schedule/storage")
    public Map<String, Object> create(@RequestBody ScheduleStorageCreateDto scheduleStorageCreateDto
                                           , @AuthenticationPrincipal User user){
        return scheduleStorageService.createScheduleStorage(scheduleStorageCreateDto, user.getUsername());
    }

    @GetMapping("/schedule/storage")
    public Map<String, Object> storageList(@AuthenticationPrincipal User user){
        return scheduleStorageService.storageList(user.getUsername());
    }

    @GetMapping("/schedule/storage/{storageId}")
    public Map<String, Object> storageDetail(@PathVariable("storageId") Long id,
                                                          @AuthenticationPrincipal User user){
        return scheduleStorageService.storageDetail(id, user.getUsername());
    }

    @PutMapping("/schedule/storage")
    public Map<String, Object> storageChange(@RequestBody ScheduleStorageChangeDto scheduleStorageChangeDto,
                                             @AuthenticationPrincipal User user){
        return scheduleStorageService.storageChange(scheduleStorageChangeDto, user.getUsername());
    }

    @DeleteMapping("/schedule/storage/{storageId}")
    public Map<String, Object> storageDelete(@PathVariable("storageId") Long id,
                                             @AuthenticationPrincipal User user){
        return scheduleStorageService.storageDelete(id,user.getUsername());
    }
}
