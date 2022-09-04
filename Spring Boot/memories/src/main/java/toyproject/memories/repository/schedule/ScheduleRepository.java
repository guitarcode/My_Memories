package toyproject.memories.repository.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.memories.domain.schedule.Schedule;
import toyproject.memories.dto.schedule.ScheduleReturnDto;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final EntityManager em;

    public List<ScheduleReturnDto> saveSchedules(List<Schedule> schedules){
        List<ScheduleReturnDto> res = new ArrayList<>();

        for (Schedule schedule : schedules) {
            em.persist(schedule);
            res.add(new ScheduleReturnDto(schedule));
        }
        return res;
    }
}
