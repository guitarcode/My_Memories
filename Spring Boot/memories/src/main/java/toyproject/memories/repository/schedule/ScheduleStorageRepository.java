package toyproject.memories.repository.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.memories.domain.schedule.ScheduleStorage;
import toyproject.memories.dto.schedule.ScheduleStorageReturnDto;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ScheduleStorageRepository {
    private final EntityManager em;

    public ScheduleStorageReturnDto save(ScheduleStorage scheduleStorage){
        em.persist(scheduleStorage);
        return new ScheduleStorageReturnDto(scheduleStorage);
    }
}
