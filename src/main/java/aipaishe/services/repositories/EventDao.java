package aipaishe.services.repositories;

import aipaishe.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by hillmon on 7/5/2017.
 */
/**
 * This class is used to access data for the User entity.
 * Repository annotation allows the component scanning support to find and
 * configure the DAO without any XML configuration and also provide the Spring
 * exception translation.
 * Since we've setup setPackagesToScan and transaction manager on
 * DatabaseConfig, any bean method annotated with Transactional will cause
 * Spring to magically call begin() and commit() at the start/end of the
 * method. If exception occurs it will also call rollback().
 */
@Repository
@Transactional
public class EventDao {

    /**
     * Save the event in the database.
     */
    public void create(Event event) {
        entityManager.persist(event);
    }

    /**
     * Delete the event from the database.
     */
    public void delete(Event event) {
        if (entityManager.contains(event))
            entityManager.remove(event);
        else
            entityManager.remove(entityManager.merge(event));
    }

    public List getAll() {
        return entityManager.createQuery("from Event").getResultList();
    }

    /**
     * Return the event having the passed id.
     */
    public Event getById(long id) {
        return entityManager.find(Event.class, id);
    }

    /**
     * Return the event having the passed id.
     */
    public Integer getRemainingPlace(long id) {

        Integer remainingPlace = 0;

        Event event = this.getById(id);

        List participantList = linkEventUserDao.getListByEventId(id);

        remainingPlace = event.getEventQuota() - participantList.size() > 0 ? event.getEventQuota() - participantList.size() : 0;

        return remainingPlace;
    }

    /**
     * Return the event having the passed event name.
     */
    public Event getByName (String name) {
        return (Event) entityManager.createQuery(
                "from Event where eventName = :name")
                .setParameter("name", name)
                .getSingleResult();
    }

    /**
     * Update the passed event in the database.
     */
    public void update(Event event) {
        entityManager.merge(event);
    }

    // Private fields

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LinkEventUserDao linkEventUserDao;
}
