package toyproject.memories.domain.schedule;

import lombok.*;
import toyproject.memories.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @NotNull
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "start_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_time")
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @ManyToOne(fetch = FetchType.LAZY)
    private ScheduleStorage scheduleStorage;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
