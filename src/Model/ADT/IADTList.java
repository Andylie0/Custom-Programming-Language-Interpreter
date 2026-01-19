package Model.ADT;

import Exceptions.ADTException;
import java.util.function.Consumer;
import java.util.List;

public interface IADTList<T> extends Iterable<T> {
    T pop() throws ADTException;
    void push(T elem);

    @Override
    void forEach(Consumer<? super T>action);

    boolean isEmpty();
    List<T> getList();
}
