package toyproject.memories.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

     @GetMapping("/api")
    public ResponseEntity<String> api(){
        return ResponseEntity.ok("ok");
    }


    @PostMapping("/activity")
    public Map<String, Object> createActivity(@RequestBody ActivityCreateDto activityCreateDto){
        return activityService.createActivity(activityCreateDto);
    }

    @GetMapping("/activity")
    public Map<String, Object> readAllActivity(){
        return activityService.findAll();
    }

    @GetMapping("/activity/")
    public Map<String,Object> readActivityByTags(@RequestParam(value = "tagString") String tagString){
        return activityService.findByTags(tagString);
    }
}
