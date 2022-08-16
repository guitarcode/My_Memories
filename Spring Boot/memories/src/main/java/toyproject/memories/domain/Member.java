package toyproject.memories.domain;

import lombok.Getter;
import lombok.Setter;
import toyproject.memories.domain.memory.Activity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String password;

    private LocalDateTime birthDate;



    @OneToMany (mappedBy = "member")
    private List<Activity> activities = new ArrayList<>();


}