package Model.Expressions;

import Exceptions.ExpressException;
import Model.ADT.IADTDictionary;
import Model.ADT.IADTHeap;
import Model.Expressions.IExp;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Values.IValue;
import Model.Values.RefValue;


public class HeapReadExpr implements IExp{
    private final IExp expression;

    public HeapReadExpr(IExp expression) {
        this.expression = expression;
    }

    @Override
    public IValue eval(IADTDictionary<String, IValue> symTable, IADTHeap heap) throws ExpressException{
        IValue eval = expression.eval(symTable,heap);
        if(!(eval instanceof RefValue)){
            throw new ExpressException("HEAP READ ERROR: Expression is not a reference");
        }
        var address = ((RefValue)eval).getAddress();
        var content = heap.getContent();
        if(!content.containsKey(address)){
            throw new ExpressException("HEAP READ ERROR: Address "+address+" does not exist");
        }
        return content.get(address);
    }

    @Override
    public String toString(){
        return String.format("rH(%s)",expression.toString());
    }

    @Override
    public IType typeCheck(IADTDictionary<String, IType> typeEnv) throws ExpressException {
        IType type = expression.typeCheck(typeEnv);
        if (type instanceof RefType) {
            RefType reft = (RefType) type;
            return reft.getInner();
        }
        else
            throw new ExpressException("HEAP READ ERROR: Expression is not a reference");
    }
}
