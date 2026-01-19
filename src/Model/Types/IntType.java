package Model.Types;

import Model.Values.IValue;
import Model.Values.IntValue;

public class IntType implements IType{
    @Override
    public boolean equals(IType obj){
        return obj instanceof IntType;
    }

    @Override
    public IValue defaultValue(){
        return new IntValue(0);
    }

    @Override
    public String toString(){
        return "int";
    }
}
