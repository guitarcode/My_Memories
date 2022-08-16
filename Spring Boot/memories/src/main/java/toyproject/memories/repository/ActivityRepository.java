package toyproject.memories.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.memories.domain.ActivityTagRelation;
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

    public List<ActivityTagRelation> findByTag(String content){
        return entityManager.createQuery(
                "select r from ActivityTagRelation as r where r.tag.content = :content")
                .setParameter("content",content)
                .getResultList();
    }
}
