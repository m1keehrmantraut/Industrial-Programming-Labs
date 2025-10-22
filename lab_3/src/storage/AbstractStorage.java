package storage;

import model.AbstractEntity;
import java.util.List;
import java.util.Map;

public abstract class AbstractStorage<T extends AbstractEntity> {
    public abstract void add(T obj);
    public abstract void update(int id, T newObj);
    public abstract void delete(int id);
    public abstract T findById(int id);
    public abstract List<T> getAllList();
    public abstract Map<Integer, T> getAllMap();
}
