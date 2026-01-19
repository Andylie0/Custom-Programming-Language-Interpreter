package Model.Values;

import Model.Types.IType;
import Model.Types.StringType;

public class StringValue implements IValue{
    private final String value;

    public StringValue(String value) { this.value = value; }

    public String getValue() { return this.value; }

    @Override
    public String toString() { return this.value; }

    @Override
    public IType getType(){
        return new StringType();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof StringValue))
            return false;
        var other = (StringValue) obj;
        return this.value.equals(other.value);
    }
}
