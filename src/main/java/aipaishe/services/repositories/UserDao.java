package aipaishe.services.repositories;

import aipaishe.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * This class is used to access data for the User entity.
 * Repository annotation allows the component scanning support to find and
 * configure the DAO wihtout any XML configuration and also provide the Spring
 * exceptiom translation.
 * Since we've setup setPackagesToScan and transaction manager on
 * DatabaseConfig, any bean method annotated with Transactional will cause
 * Spring to magically call begin() and commit() at the start/end of the
 * method. If exception occurs it will also call rollback().
 */
@Repository
@Transactional
public class UserDao {

    /**
     * Save the user in the database.
     */
    public void create(User user) {
        entityManager.persist(user);
        return;
    }

    /**
     * Delete the user from the database.
     */
    public void delete(User user) {
        if (entityManager.contains(user))
            entityManager.remove(user);
        else
            entityManager.remove(entityManager.merge(user));
        return;
    }

    /**
     * Return all the users stored in the database.
     */
    public List getAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    /**
     * Return the user having the passed email.
     */
    /*
    edited by Hillmon to fix the bug
     */

    public User getByEmail(String email) {
        User foundUser = null;
        try {
            foundUser = (User) entityManager.createQuery(
                    "from User where email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        }
        catch (Exception e) {
            System.out.println("No user is found!!!");
        }
        finally {
            return foundUser;
        }
        /*
        return (User) entityManager.createQuery(
                "from User where email = :email")
                .setParameter("email", email)
                .getSingleResult();
        */
    }

    /**
     * Return the user having the passed id.
     */
    public User getById(long id) {
        return entityManager.find(User.class, id);
    }

    /**
     * Update the passed user in the database.
     */
    public void update(User user) {
        entityManager.merge(user);
        return;
    }


    /**
     * Verify the passed user email and password in the database.
     */

    /*
    edited by Hillmon to fix the bug of wrong username or password
     */

    public User verifyUserLogin(String email, String password) {
        User rtnUser = this.getByEmail(email);
        if (rtnUser != null){
            if (rtnUser.getPassword().equals(password))
                return rtnUser;
            else
                return null;
        }
        else {
            return null;
        }
    }

    // Private fields

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    private EntityManager entityManager;

}