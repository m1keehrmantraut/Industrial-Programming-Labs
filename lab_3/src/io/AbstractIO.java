package io;

import java.util.List;
import model.AbstractEntity;

public abstract class AbstractIO<T extends AbstractEntity> {
    public abstract List<T> read(String filename);
    public abstract void write(String filename, List<T> objects);
}
