package toyproject.memories.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.memories.domain.memory.Activity;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ActivityRepository {

    private final EntityManager entityManager;

    public Long save(Activity activity){
        entityManager.persist(activity);
        return activity.getId();
    }

    public List<Activity> findAll(){
        return entityManager.createQuery("select a from Activity as a").getResultList();
    }

    public Activity findOne(Long activityId){
        return entityManager.find(Activity.class,activityId);
    }

    public List<Activity> findByTag(List<String> contents){
        System.out.println("쿼리문 생성");

        return entityManager.createQuery("select r.activity " +
                "from ActivityTagRelation r " +
                "inner join Tag as t on t = r.tag " +
                "where t.content in :contents "+
                "group by r.activity.id " +
                "having count(r.tag) >= :num ", Activity.class )
                .setParameter("contents", contents)
                .setParameter("num", contents.size())
                .getResultList();
    }
}
