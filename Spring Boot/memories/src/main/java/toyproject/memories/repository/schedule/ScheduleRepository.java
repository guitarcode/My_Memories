package toyproject.memories.repository.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.memories.domain.schedule.Schedule;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final EntityManager em;

    public List<Schedule> saveSchedules(List<Schedule> schedules){
        for (Schedule schedule : schedules) {
            em.persist(schedule);
        }
        return schedules;
    }
}
