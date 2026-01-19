package Model.ADT;
import Exceptions.ADTException;

import java.util.Map;
import java.util.Set;

public interface IADTDictionary<T,V> {
    V get(T key);
    void remove(T key) throws ADTException;
    void put(T key, V value);
    boolean containsKey(T key);
    Map<T,V> getDictionary();
    Set<T> keySet();
    IADTDictionary<T,V> copy();
}
