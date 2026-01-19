package Model.Values;

import Model.Types.BoolType;
import Model.Types.IType;

public class BoolValue implements IValue{
    private final boolean value;

    public BoolValue(boolean value) { this.value = value; }

    public boolean getValue() { return this.value; }

    @Override
    public String toString() {
        return String.format("%b", this.value);
    }

    @Override
    public IType getType(){
        return new BoolType();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof BoolValue))
            return false;
        var q = (BoolValue) obj;
        return this.value == q.value;
    }
}
