package Model.Statements;

import Model.ADT.IADTDictionary;
import Model.Program_State.ProgramState;
import Model.Types.IType;
import jdk.jfr.Percentage;

public class NopStmt implements IStmt{
    @Override
    public ProgramState execute(ProgramState state) { return null; }

    @Override
    public String toString() { return "nop"; }

    @Override
    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) { return typeTable; }
}
