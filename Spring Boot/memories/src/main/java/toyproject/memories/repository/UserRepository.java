package toyproject.memories.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.memories.domain.user.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager entityManager;

    public User saveMember(User user){
        entityManager.persist(user);
        return user;
    }

    public Optional<User> findByName(String name){
        Optional<User> user = entityManager.createQuery("select u from User as u where u.username = :name", User.class)
                .setParameter("name",name)
                .getResultList().stream().findAny();
        return user;
    }
}
