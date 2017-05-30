package aipaishe.models;

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
        return;
    }

    /**
     * Return the event having the passed id.
     */
    public Event getById(long id) {
        return entityManager.find(Event.class, id);
    }

    public Event getByName(String name) {
        return entityManager.find(Event.class, name);
    }

    // Private fields

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    private EntityManager entityManager;
}
