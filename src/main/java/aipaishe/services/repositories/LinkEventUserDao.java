package aipaishe.services.repositories;

import aipaishe.models.LinkEventUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by hillmon on 7/7/2017.
 */
@Repository
@Transactional
public class LinkEventUserDao {

    // CURD methods

    /**
     * Save the record in the database.
     */
    public void create(LinkEventUser linkEventUser) {
        entityManager.persist(linkEventUser);
        return;
    }

    /**
     * Update the passed record in the database.
     */
    public void update(LinkEventUser linkEventUser) {
        entityManager.merge(linkEventUser);
        return;
    }

    /**
     * Delete the record from the database.
     */
    public void delete(LinkEventUser linkEventUser) {
        if (entityManager.contains(linkEventUser))
            entityManager.remove(linkEventUser);
        else
            entityManager.remove(entityManager.merge(linkEventUser));
        return;
    }

    /**
     * Return all the records stored in the database.
     */
    public List getAll() {
        return entityManager.createQuery("from LinkEventUser").getResultList();
    }

    /**
     * Return the links having the passed event ID.
     */
    public List<LinkEventUser> getListByEventId(long eventId) {
        return (List<LinkEventUser>) entityManager.createQuery(
                "from LinkEventUser where eventId = :eventId")
                .setParameter("email", eventId)
                .getResultList();
    }

    /**
     * Return the links having the passed event ID.
     */
    public List<LinkEventUser> getListByUserId(long userId) {
        return (List<LinkEventUser>) entityManager.createQuery(
                "from LinkEventUser where userId = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }

    /**
     * Return the links having the passed event ID.
     */
    public LinkEventUser getByEventUser(long eventId, long userId) {
        return (LinkEventUser) entityManager.createQuery(
                "from LinkEventUser where eventId = :eventId and userId = :userId")
                .setParameter("eventId", eventId)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    // Private fields

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    private EntityManager entityManager;
}
