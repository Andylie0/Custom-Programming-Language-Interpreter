package Model.Expressions;

import Exceptions.ExpressException;
import Model.ADT.IADTDictionary;
import Model.ADT.IADTHeap;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IValue;

public class ValueExp implements IExp {
    private final IValue value;

    public ValueExp(IValue value) { this.value = value; }

    @Override
    public IValue eval(IADTDictionary<String, IValue> symTable, IADTHeap heap) {
        return value;
    }

    @Override
    public String toString(){
        return value.toString();
    }

    @Override
    public IType typeCheck(IADTDictionary<String, IType> typeEnv) throws ExpressException {
        return value.getType();
    }
}
