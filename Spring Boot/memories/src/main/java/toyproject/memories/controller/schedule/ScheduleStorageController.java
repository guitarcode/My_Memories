package toyproject.memories.controller.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.memories.dto.schedule.ScheduleStorageCreateDto;
import toyproject.memories.dto.schedule.ScheduleStorageReturnDto;
import toyproject.memories.service.schedule.ScheduleStorageService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081/")
public class ScheduleStorageController {

    private final ScheduleStorageService scheduleStorageService;

    @PostMapping("/schedule/storage")
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ScheduleStorageReturnDto create(@RequestBody ScheduleStorageCreateDto scheduleStorageCreateDto
//                                           ,@AuthenticationPrincipal User user
    ){
        System.out.println("뭐가문젠데");
        return scheduleStorageService.createScheduleStorage(scheduleStorageCreateDto);
    }
}