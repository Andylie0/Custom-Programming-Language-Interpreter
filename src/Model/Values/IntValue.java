package Model.Values;
import Model.Types.*;

public class IntValue implements IValue {
    private final int value;

    public IntValue(int value) { this.value = value; }

    public int getValue() { return this.value; }


    @Override
    public String toString() {
        return String.format("%d", this.value);
    }

    @Override
    public IType getType(){
        return new IntType();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof IntValue))
            return false;
        var other = (IntValue) obj;
        return this.value == other.value;
    }

}
