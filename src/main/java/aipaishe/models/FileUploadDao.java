package aipaishe.models;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by hillmon on 16/6/2017.
 */
@Repository
@Transactional
public class FileUploadDao {

    /**
     * Save the event in the database.
     */
    public void create(FileUpload fileupload) {
        entityManager.persist(fileupload);
        return;
    }

    /**
     * Delete the event from the database.
     */
    public void delete(FileUpload fileupload) {
        if (entityManager.contains(fileupload))
            entityManager.remove(fileupload);
        else
            entityManager.remove(entityManager.merge(fileupload));
        return;
    }

    public List getAll() {
        return entityManager.createQuery("from FileUpload").getResultList();
    }

    /**
     * Return the event having the passed id.
     */
    public FileUpload getById(long id) {
        return entityManager.find(FileUpload.class, id);
    }

    /**
     * Update the passed event in the database.
     */
    public void update(FileUpload fileupload) {
        entityManager.merge(fileupload);
        return;
    }

    // Private fields

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    private EntityManager entityManager;

}
