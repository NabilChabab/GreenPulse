package repository;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryInterface<T> {

    void add(T entity);
    T getById(int id);
    List<T> getAll();
    void update(T entity);
    void delete(int id);


}
