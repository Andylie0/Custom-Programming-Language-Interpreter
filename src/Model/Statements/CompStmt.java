package Model.Statements;

import Exceptions.ExpressException;
import Exceptions.StatementException;
import Model.ADT.IADTDictionary;
import Model.ADT.IADTStack;
import Model.Program_State.ProgramState;
import Model.Types.IType;
import jdk.jfr.Percentage;

public class CompStmt implements IStmt {
    private final IStmt first;
    private final IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "("+first.toString()+";"+second.toString()+")";
    }

    @Override
    public ProgramState execute(ProgramState state){
        IADTStack<IStmt> stk = state.getExecStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException, StatementException{
        return second.typeCheck(first.typeCheck(typeTable));
    }
}
