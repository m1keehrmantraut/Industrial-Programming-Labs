package io;

import model.AbstractEntity;
import java.util.List;

public abstract class IODecorator<T extends AbstractEntity> extends AbstractIO<T> {

    protected final AbstractIO<T> wrappee;

    protected IODecorator(AbstractIO<T> wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public List<T> read(String filename) {
        return wrappee.read(filename);
    }

    @Override
    public void write(String filename, List<T> objects) {
        wrappee.write(filename, objects);
    }
}
