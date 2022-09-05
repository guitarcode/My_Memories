package toyproject.memories.repository.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.memories.domain.schedule.ScheduleItem;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleItemRepository {
    private final EntityManager em;

    public Long save(ScheduleItem scheduleItem){
        em.persist(scheduleItem);
        return scheduleItem.getId();
    }

    public Optional<ScheduleItem> findOne(Long id){
        return em.createQuery("select i from ScheduleItem i " +
                "where i.id = :id")
                .setParameter("id",id)
                .getResultList().stream().findAny();
    }
}
