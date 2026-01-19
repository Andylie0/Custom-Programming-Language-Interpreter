package Model.Expressions;

import Exceptions.ExpressException;
import Model.ADT.IADTDictionary;
import Model.ADT.IADTHeap;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.*;

import java.lang.reflect.Type;

public class ArithExp implements IExp{
    private final IExp exp1,exp2;
    private final char operator;

    public ArithExp(IExp exp1, IExp exp2, char operator) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = operator;
    }

    @Override
    public IValue eval(IADTDictionary<String, IValue> symTable, IADTHeap heap) throws ExpressException {
        IValue val1 = exp1.eval(symTable, heap);
        if(val1.getType().equals(new IntType())) {
            IValue val2 = exp2.eval(symTable, heap);
            if(val2.getType().equals(new IntType())) {
                var i1 = (IntValue) val1;
                var i2 = (IntValue) val2;
                int n1,n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                switch (operator) {
                    case '+': return new IntValue(n1+n2);
                    case '-': return new IntValue(n1-n2);
                    case '*': return new IntValue(n1*n2);
                    case '/': if(n2==0) throw new ExpressException("Division by zero"); else return new IntValue(n1/n2);
                    default : throw new ExpressException("Arithmetic Error: Invalid operator");
                }
            }
            else
                throw new ExpressException("Arithmetic Error: The second operand is not an integer.");
        }
        else
            throw new ExpressException("Arithmetic Error: The first operand is not an integer.");
    }

    @Override
    public String toString(){
        return exp1.toString()+operator+exp2.toString();
    }

    @Override
    public IType typeCheck(IADTDictionary<String, IType> typeEnv) throws ExpressException {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);
        if(type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else
                throw new ExpressException("Arithmetic Error: The second operand is not an integer.");
        }
        else
            throw new ExpressException("Arithmetic Error: The first operand is not an integer.");
    }

}
