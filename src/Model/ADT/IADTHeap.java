package Model.ADT;

import Exceptions.ADTException;
import Model.Values.IValue;

import java.util.Map;

public interface IADTHeap{
    Integer getFreeValue();

    Map<Integer, IValue> getContent();

    void setContent(Map<Integer, IValue> newMap);

    Integer add(IValue newValue);

    void update(Integer pos, IValue newValue) throws ADTException;

    IValue getContent(Integer key) throws ADTException;
}
