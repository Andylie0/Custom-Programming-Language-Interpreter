package Model.Types;

import Model.Values.IValue;
import Model.Values.RefValue;

public class RefType implements IType{
    private final IType inner;

    public RefType(IType inner)  { this.inner = inner; }

    public IType getInner()  { return this.inner; }

    @Override
    public boolean equals(IType obj){
        return obj instanceof RefType && this.inner.equals(((RefType) obj).getInner());
    }

    @Override
    public String toString(){
        return  String.format("Ref("+inner.toString()+")");
    }

    @Override
    public IValue defaultValue(){
        return new RefValue(0,inner);
    }
}
