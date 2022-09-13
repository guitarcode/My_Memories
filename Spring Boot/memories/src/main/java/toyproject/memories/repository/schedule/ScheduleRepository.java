package toyproject.memories.repository.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.memories.domain.schedule.Schedule;
import toyproject.memories.domain.user.User;
import toyproject.memories.dto.schedule.ScheduleReturnDto;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public boolean isOverlap(LocalDateTime startTime, LocalDateTime endTime){
        List<Schedule> overlapSchedule = em.createQuery("select s from Schedule s " +
                "where s.startDateTime between :startTime and :endTime " +
                "or s.endDateTime between :startTime and :endTime", Schedule.class)
                .setParameter("startTime",startTime)
                .setParameter("endTime", endTime)
                .getResultList();

        return !overlapSchedule.isEmpty();
    }

    public List<Schedule> findAll(User user){
        return em.createQuery("select s from Schedule s " +
                "where s.user = :user", Schedule.class)
                .setParameter("user", user)
                .getResultList();
    }
}
