package toyproject.memories.repository.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.memories.domain.schedule.ScheduleStorage;
import toyproject.memories.domain.user.User;
import toyproject.memories.dto.schedule.ScheduleStorageListDto;
import toyproject.memories.dto.schedule.ScheduleStorageReturnDto;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ScheduleStorageRepository {
    private final EntityManager em;

    public ScheduleStorageReturnDto save(ScheduleStorage scheduleStorage){
        em.persist(scheduleStorage);
        return new ScheduleStorageReturnDto(scheduleStorage);
    }

    public List<ScheduleStorageListDto> findAllByUser(User user){
        return em.createQuery("select s from ScheduleStorage as s " +
                "where s.user = :user", ScheduleStorage.class)
                .setParameter("user", user)
                .getResultList().stream()
                .map(ScheduleStorageListDto::new)
                .collect(Collectors.toList());

    }
}
