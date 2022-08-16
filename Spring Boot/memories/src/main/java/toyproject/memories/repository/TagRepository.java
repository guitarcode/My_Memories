package toyproject.memories.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.memories.domain.Tag;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TagRepository {
    private final EntityManager entityManager;

    public Tag save(Tag tag){
        entityManager.persist(tag);
        return tag;
    }

    public Tag findByContent(String content){
        try {
            Tag tag = entityManager.createQuery(
                            "select t from Tag as t where t.content = :content", Tag.class)
                    .setParameter("content", content)
                    .getSingleResult();
            return tag;
        }
        catch (Exception e){
            return null;
        }
    }
}
