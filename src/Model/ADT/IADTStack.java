package Model.ADT;
import Exceptions.ADTException;

import java.util.Deque;
import java.util.List;

public interface IADTStack<T> extends Iterable<T>{
    T pop() throws ADTException;
    T peek() throws ADTException;
    void push(T elem);
    boolean isEmpty();
    Deque<T> getStack();
    public List<T> getReversed();
}
