package Model.ADT;

import Exceptions.ADTException;

import java.util.HashMap;
import java.util.Set;

public class ADTDictionary<T,V> implements  IADTDictionary<T,V>{
    private final HashMap<T,V> dictionary;

    public ADTDictionary() {
        this.dictionary = new HashMap<>();
    }

    @Override
    public void put(T key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public V get(T key) {
        return dictionary.get(key);
    }

    @Override
    public void remove(T key) throws ADTException {
            if(!dictionary.containsKey(key))
                throw new ADTException("DICTIONARY ERROR : Key not found.");
            dictionary.remove(key);
    }

    @Override
    public boolean containsKey(T key) {
        return dictionary.containsKey(key);
    }

    @Override
    public HashMap<T,V> getDictionary() {
        return dictionary;
    }

    @Override
    public Set<T> keySet(){
        return dictionary.keySet();
    }

    @Override
    public IADTDictionary<T,V> copy(){
        var newADTDictionary = new ADTDictionary<T,V>();

        for(var k1 : keySet()){
            newADTDictionary.put(k1, dictionary.get(k1));
        }

        return newADTDictionary;
    }
}
