package aipaishe.services.repositories;

import aipaishe.models.Event;
import aipaishe.models.userregistration.VerificationToken;
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
public class VerificationTokenDao {

    /**
     * Save the verificationToken in the database.
     */
    public void create(VerificationToken verificationToken) {
        entityManager.persist(verificationToken);
        return;
    }

    /**
     * Delete the verificationToken from the database.
     */
    public void delete(VerificationToken verificationToken) {
        if (entityManager.contains(verificationToken))
            entityManager.remove(verificationToken);
        else
            entityManager.remove(entityManager.merge(verificationToken));
        return;
    }

    public List getAll() {
        return entityManager.createQuery("from VerificationToken").getResultList();
    }

    /**
     * Return the event having the passed id.
     */
    public VerificationToken getById(long id) {
        return entityManager.find(VerificationToken.class, id);
    }

    /**
     * Return the event having the passed event token.
     */
    public VerificationToken getByToken (String token) {
        return (VerificationToken) entityManager.createQuery(
                "from VerificationToken where token = :token")
                .setParameter("token", token)
                .getSingleResult();
    }

    /**
     * Update the passed verificationToken in the database.
     */
    public void update(VerificationToken verificationToken) {
        entityManager.merge(verificationToken);
        return;
    }

    // Private fields

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    private EntityManager entityManager;
}
