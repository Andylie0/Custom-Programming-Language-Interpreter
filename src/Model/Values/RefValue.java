package Model.Values;

import Model.Types.IType;
import Model.Types.RefType;

public class RefValue implements IValue{
    private final int address;
    private final IType locationType;

    public RefValue(int address, IType locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() { return this.address; }

    public IType getLocationType() { return this.locationType; }

    @Override
    public String toString() {
        return String.format("(%d, %s)", this.address, this.locationType);
    }

    @Override
    public IType getType(){
        return new RefType(locationType);
    }

}
