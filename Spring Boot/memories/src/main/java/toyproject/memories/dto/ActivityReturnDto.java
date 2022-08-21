package toyproject.memories.dto;

import lombok.Getter;
import toyproject.memories.domain.memory.ActivityTagRelation;
import toyproject.memories.domain.memory.Activity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ActivityReturnDto {
    private Long ActivityId;
    private String content;
    private List<String> tags = new ArrayList<>();

    public ActivityReturnDto(Activity activity){
        this.ActivityId = activity.getId();
        this.content = activity.getContent();
        for(ActivityTagRelation tag : activity.getTags()){
            tags.add(tag.getTag().getContent());
        }
    }
}
