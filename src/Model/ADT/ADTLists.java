package Model.ADT;
import Exceptions.ADTException;
import java.util.function.Consumer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ADTLists<T> implements IADTList<T>{
    private final List<T> list;

    public ADTLists() {
        this.list = new LinkedList<>();
    }

    public ADTLists(List<T> list) {
        this.list = list;
    }

    @Override
    public T pop() throws ADTException {
            if(!list.isEmpty()) {
                T newList = list.getFirst();
                list.removeFirst();
                return newList;
            }
        throw new ADTException("LIST ERROR : List is empty.");
    }

    @Override
    public void push(T elem) {
        list.add(elem);
    }

    @Override
    public void forEach(Consumer<? super T>action){
        list.forEach(action);
    }


    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public List<T> getList() {
            return list;
    }
}
