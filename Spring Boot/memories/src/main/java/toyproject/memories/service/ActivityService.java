package toyproject.memories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.memories.domain.ActivityTagRelation;
import toyproject.memories.domain.Tag;
import toyproject.memories.domain.memory.Activity;
import toyproject.memories.domain.memory.Memory;
import toyproject.memories.dto.ActivityCreateDto;
import toyproject.memories.dto.ActivityReturnDto;
import toyproject.memories.repository.ActivityRepository;
import toyproject.memories.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final TagRepository tagRepository;

    public Map<String, Object> findAll(){
        Map<String, Object> response;

        List<Activity> activities = activityRepository.findAll();

        if(activities.isEmpty()) {
            response = createMap.failMap("저장된 활동이 없습니다. 활동을 생성해주세요. ");
            return response;
        }
        List<ActivityReturnDto> returnDtos = new ArrayList<>();
        for(Activity activity : activities){
            returnDtos.add(new ActivityReturnDto(activity));
        }

        response = createMap.successMap(returnDtos);
        return response;
    }

    @Transactional
    public Map<String, Object> createActivity(ActivityCreateDto activityCreateDto){
        Map<String, Object> response;

        if(activityCreateDto.getActivityContent() == null){
            response = createMap.failMap("내용을 입력해주세요.");
            return response;
        }

        Memory memory = new Memory();

        memory.setContent(activityCreateDto.getActivityContent());

        //클라이언트로부터 입력받은 하나의 String을 정제
        List<String> stringTags = parseStringTag(activityCreateDto.getTagContent());
        List<Tag> tags = saveTags(stringTags);

        for(Tag tag: tags){
            ActivityTagRelation activityTagRelation =ActivityTagRelation.createRelation(memory,tag);
            memory.addRelation(activityTagRelation);
        }
        activityRepository.save(memory);

        Long activityId = memory.getId();
        response = createMap.successMap(activityId);
        System.out.println(response.get("result"));
        return response;
    }

    @Transactional
    public List<Tag> saveTags(List<String> stringTags){
        List<Tag> tags = new ArrayList<>();
        for(String content : stringTags){
            Tag tag = tagRepository.findByContent(content);
            if(tag == null){
                Tag newTag = new Tag();
                newTag.setContent(content);
                tags.add(tagRepository.save(newTag));
            }
            else tags.add(tag);
        }
        for(Tag tag : tags){
            System.out.println(tag.getContent());
        }
        return tags;
    }

    public Map<String, Object> findByTags(String tagString){
        Map<String, Object> response;

        List<String> stringTags = parseStringTag(tagString);
        int size = stringTags.size();

        List<ActivityTagRelation>[] relations = new List[size];
        List<Activity> activities = activityRepository.findByTag(stringTags);
//        for (int i = 0; i < size; i++) {
//            System.out.println(stringTags.get(i));
//            relations[i] = activityRepository.findByTag(stringTags.get(i));
//        }
//
//        for(ActivityTagRelation relation : relations[0]){
//            activities.add(relation.getActivity());
//        }
//
//        for (int i = 1; i < size; i++) {
//            List<Activity> commonActivities = new ArrayList<>();
//            for(Activity activity : activities){
//                for(ActivityTagRelation relation : relations[i]){
//                    if(activity == relation.getActivity())
//                        commonActivities.add(activity);
//                }
//            }
//            activities = commonActivities;
//        }
//
        List<ActivityReturnDto> returnDtos = activities.stream().
                map(a -> new ActivityReturnDto(a))
                .collect(Collectors.toList());

        response = createMap.successMap(returnDtos);
        return response;
    }

    public List<String> parseStringTag(String tagString){
        List<String> stringTags = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(tagString,"# ");
        //strip 메소드로 앞뒤에 남는 공백을 제거
        while(st.hasMoreTokens()){
            stringTags.add(st.nextToken().strip());
        }
        return stringTags;
    }
}
