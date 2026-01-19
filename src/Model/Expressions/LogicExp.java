package Model.Expressions;

import Exceptions.ExpressException;
import Model.ADT.IADTDictionary;
import Model.ADT.IADTHeap;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IValue;
import Model.Values.IntValue;

public class LogicExp implements IExp{
    private final IExp exp1,exp2;
    private final String operator;

    public LogicExp(IExp exp1, IExp exp2, String operator) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = operator;
    }

    @Override
    public IValue eval(IADTDictionary<String, IValue> symTable, IADTHeap heap) throws ExpressException {
        IValue val1 = exp1.eval(symTable, heap);
        if(val1.getType().equals(new BoolType())) {
            IValue val2 = exp2.eval(symTable, heap);
            if(val2.getType().equals(new BoolType())) {
                var i1 = (BoolValue) val1;
                var i2 = (BoolValue) val2;
                boolean b1,b2;
                b1 = i1.getValue();
                b2 = i2.getValue();
                switch (operator) {
                    case "and" : return new BoolValue(b1 && b2);
                    case "or" :  return new BoolValue(b1 || b2);
                    default : throw new ExpressException("Logic Error: Invalid operator");
                }
            }
            else
                throw new ExpressException("Logic Error: The second operand is not a boolean.");
        }
        else
            throw new ExpressException("Logic Error: The first operand is not a boolean.");
    }


    @Override
    public String toString(){
        return exp1.toString()+" "+operator+" "+exp2.toString();
    }

    @Override
    public IType typeCheck(IADTDictionary<String, IType> typeEnv) throws ExpressException {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);
        if(type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new ExpressException("Arithmetic Error: The second operand is not an integer.");
        }
        else
            throw new ExpressException("Arithmetic Error: The first operand is not an integer.");
    }
}
