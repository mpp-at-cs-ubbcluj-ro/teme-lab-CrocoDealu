package org.example.repository;

import org.example.domain.User;
import java.util.List;
import java.util.Optional;

public interface IRepository<ID> {
    /**
     * Retrieve all records from the repository.
     * @return a list of all records.
     */
    List<User> findAll();

    /**
     * Retrieve a record by its unique identifier.
     * @param id the identifier of the record.
     * @return an Optional containing the record, or an empty Optional if not found.
     */
    Optional<User> findById(ID id);

    /**
     * Save a record to the repository.
     * @param entity the entity to be saved.
     * @return the saved entity.
     */
    Optional<User> save(User entity);

    /**
     * Delete a record by its unique identifier.
     * @param id the identifier of the record to delete.
     */
    Optional<User> deleteById(ID id);

    /**
     * Update an existing record in the repository.
     * @param entity the entity to update.
     * @return the updated entity.
     */
    Optional<User> update(User entity);
}
