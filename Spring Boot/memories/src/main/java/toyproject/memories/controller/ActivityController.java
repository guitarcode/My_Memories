package toyproject.memories.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import toyproject.memories.domain.memory.Activity;
import toyproject.memories.dto.ActivityCreateDto;
import toyproject.memories.service.ActivityService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;


    @PostMapping("/activity")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Map<String, Object> createActivity(@RequestBody ActivityCreateDto activityCreateDto){
        System.out.println("액티비티 생성을 시작합니다.");
        return activityService.createActivity(activityCreateDto);
    }

    @GetMapping("/activity")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Map<String, Object> readAllActivity(){
        return activityService.findAll();
    }

    @GetMapping("/activity/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Map<String,Object> readActivityByTags(@RequestParam(value = "query") String query){
        return activityService.findByTags(query);
    }
}
