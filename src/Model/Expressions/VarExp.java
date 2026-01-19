package Model.Expressions;

import Exceptions.ExpressException;
import Model.ADT.IADTDictionary;
import Model.ADT.IADTHeap;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IValue;

public class VarExp implements IExp{
    private final String Id;

    public VarExp(String Id) { this.Id = Id; }

    @Override
    public IValue eval(IADTDictionary<String, IValue>symTable, IADTHeap heap) throws ExpressException{
        return symTable.get(Id);
    }

    @Override
    public String toString(){
        return Id;
    }

    @Override
    public IType typeCheck(IADTDictionary<String, IType> typeEnv) throws ExpressException {
        return typeEnv.get(Id);
    }
}
