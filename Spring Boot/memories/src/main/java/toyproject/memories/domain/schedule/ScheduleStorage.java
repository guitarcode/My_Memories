package toyproject.memories.domain.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private String title;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
    private List<ScheduleItem> scheduleItems = new ArrayList<>();

    public ScheduleStorage(String title, User user){
        this.title = title;
        this.user = user;
    }

    public void addScheduleItem(ScheduleItem scheduleItem){
        this.scheduleItems.add(scheduleItem);
        scheduleItem.setStorageAndUser(this);
    }
}
