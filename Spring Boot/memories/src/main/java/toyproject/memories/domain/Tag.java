package toyproject.memories.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "tag")
public class Tag {
    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tag_content", unique = true)
    private String content;

//    @OneToMany(mappedBy = "tag")
//    private List<ActivityTagRelation> activities = new ArrayList<>();


}
