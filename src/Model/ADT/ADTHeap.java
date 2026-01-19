package Model.ADT;

import Exceptions.ADTException;
import Model.ADT.IADTHeap;
import Model.Values.IValue;

import java.util.HashMap;
import java.util.Map;

public class ADTHeap implements IADTHeap{

    private final Map<Integer, IValue> map;
    private Integer freeValue;

    public ADTHeap(){
        map = new HashMap<>();
        freeValue = 1;
    }

    public ADTHeap(Map<Integer, IValue> map){
        freeValue = 1;
        this.map = map;
    }

    public Integer newValue(){
        freeValue += 1;
        if(map.containsKey(freeValue)){
            freeValue +=1;
        }

        return freeValue;
    }

    @Override
    public Integer getFreeValue(){
        return freeValue;
    }

    @Override
    public Map<Integer, IValue> getContent(){
        return map;
    }

    @Override
    public void setContent(Map<Integer, IValue> newMap){
        map.clear();
        for(var i : newMap.keySet())
            map.put(i, newMap.get(i));
    }

    @Override
    public Integer add(IValue value){
        map.put(freeValue, value);
        Integer toReturn = freeValue;
        freeValue += newValue();
        return toReturn;
    }

    @Override
    public void update(Integer pos, IValue newValue) throws ADTException{
        if(!map.containsKey(pos))
            throw new ADTException(String.format("HEAP ERROR : Position %d is not in the heap.",pos));
        map.put(pos, newValue);
    }

    @Override
    public IValue getContent(Integer pos) throws ADTException{
        if(pos == 0)
            throw new ADTException("HEAP ERROR : Heap doesn't start from 0.");
        if(!map.containsKey(pos))
            throw new ADTException(String.format("HEAP ERROR : Position %d is not in the heap.", pos));
        return map.get(pos);
    }

}
