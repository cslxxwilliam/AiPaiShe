package aipaishe.services.repositories;

import aipaishe.models.FileUpload;
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
    }

    /**
     * Delete the event from the database.
     */
    public void delete(FileUpload fileupload) {
        if (entityManager.contains(fileupload))
            entityManager.remove(fileupload);
        else
            entityManager.remove(entityManager.merge(fileupload));
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
     * Return the event having the passed id.
     */
    public List<FileUpload> getByFileName(String fileName) {

        return (List<FileUpload>) entityManager.createQuery(
                "from FileUpload where fileName = :fileName")
                .setParameter("fileName", fileName)
                .getResultList();
    }

    /**
     * Return the event having the passed id.
     */
    public List<FileUpload> getByEventId(long id) {

        return (List<FileUpload>) entityManager.createQuery(
                "from FileUpload where eventId = :id")
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * Return the event having the passed id and file type.
     */
    public List<FileUpload> getByEventIdFileType(long id, String type) {
        if (type == "") {
            return (List<FileUpload>) entityManager.createQuery(
                    "from FileUpload where eventId = :id")
                    .setParameter("id", id)
                    .getResultList();
        } else {
            return (List<FileUpload>) entityManager.createQuery(
                    "from FileUpload where eventId = :id and fileType = :type")
                    .setParameter("id", id)
                    .setParameter("type", type)
                    .getResultList();
        }
    }

    /**
     * Update the passed event in the database.
     */
    public void update(FileUpload fileupload) {
        entityManager.merge(fileupload);
    }

    // Private fields

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    private EntityManager entityManager;

}
