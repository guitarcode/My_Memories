package toyproject.memories.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toyproject.memories.domain.memory.Activity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityTagRelation {
    @Id @GeneratedValue
    @Column(name = "classification_id")
    private Long id;

    @ManyToOne
    private Activity activity;

    @ManyToOne
    private Tag tag;

    public static ActivityTagRelation createRelation(Activity activity,Tag tag){
        ActivityTagRelation activityTagRelation = new ActivityTagRelation();
        activityTagRelation.setActivity(activity);
        activityTagRelation.setTag(tag);

        return activityTagRelation;
    }
}
