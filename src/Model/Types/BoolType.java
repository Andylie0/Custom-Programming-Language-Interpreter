package Model.Types;

import Model.Values.BoolValue;
import Model.Values.IValue;

public class BoolType implements IType{
    @Override
    public boolean equals(IType obj){
        return obj instanceof BoolType;
    }

    @Override
    public IValue defaultValue(){
        return new BoolValue(false);
    }

    @Override
    public String toString(){
        return "bool";
    }
}
