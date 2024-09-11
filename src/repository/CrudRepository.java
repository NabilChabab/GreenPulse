package repository;

import entities.Housing;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    Optional<T>  save(T entity);

    Optional<T>  update(T entity);

    Optional<T>  delete(int id);

    Optional<T>   findById(int id);

    List<T> findAll();
}
