package toyproject.memories.domain.memory;

import lombok.Getter;
import lombok.Setter;
import toyproject.memories.domain.ActivityTagRelation;
import toyproject.memories.domain.Member;
import toyproject.memories.domain.Tag;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Activity {
    @Id
    @GeneratedValue
    @Column(name = "activity_id")
    private Long id;

//    private String photo;
    @Column(name = "activity_content")
    private String content;

    private LocalDateTime dateTime;

    @OneToMany (mappedBy = "activity", cascade = CascadeType.ALL)
    private List<ActivityTagRelation> tags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void addRelation(ActivityTagRelation activityTagRelation){
        tags.add(activityTagRelation);
        activityTagRelation.setActivity(this);
    }

}
