package toyproject.memories.domain.schedule;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import toyproject.memories.domain.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "schedule_storage")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleStorage {
    @Id @GeneratedValue
    @Column(name = "schedule_storage_id")
    private Long id;

    private String name;

    @Column(name = "sub_name")
    private String subName;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
    private List<ScheduleItem> scheduleItems = new ArrayList<>();

    public ScheduleStorage(String name, String sub_name, User user){
        this.name = name;
        this.subName = sub_name;
        this.user = user;
    }

    public void addScheduleItem(ScheduleItem scheduleItem){
        this.scheduleItems.add(scheduleItem);
        scheduleItem.setStorage(this);
    }
}
