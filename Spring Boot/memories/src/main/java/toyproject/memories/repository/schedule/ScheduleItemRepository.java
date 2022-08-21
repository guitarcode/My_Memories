package toyproject.memories.repository.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.memories.domain.schedule.ScheduleItem;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ScheduleItemRepository {
    private final EntityManager em;

    public Long save(ScheduleItem scheduleItem){
        em.persist(scheduleItem);
        return scheduleItem.getId();
    }

}
