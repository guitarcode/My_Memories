package toyproject.memories.domain.user;

import lombok.Getter;
import lombok.Setter;
import toyproject.memories.domain.memory.Activity;
import toyproject.memories.domain.schedule.Schedule;
import toyproject.memories.domain.schedule.ScheduleItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Getter @Setter
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String password;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private boolean isActivate;

    @OneToMany (mappedBy = "user")
    private List<Activity> activities = new ArrayList<>();

    @OneToMany (mappedBy = "user")
    private List<Schedule> schedules;

    @OneToMany (mappedBy = "user")
    private List<ScheduleItem> scheduleInventories;
}