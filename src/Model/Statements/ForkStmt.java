package Model.Statements;

import Exceptions.ADTException;
import Exceptions.ExpressException;
import Exceptions.StatementException;
import Model.ADT.ADTStack;
import Model.ADT.IADTDictionary;
import Model.Program_State.ProgramState;
import Model.Types.IType;

public class ForkStmt implements IStmt{
    private final IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    public ProgramState execute(ProgramState state) throws StatementException, ExpressException, ADTException {
        ADTStack<IStmt> newExecStack = new ADTStack<>();
        newExecStack.push(statement);
        return new ProgramState(newExecStack, state.getSymbolTable(), state.getFileTable(), state.getHeap(), state.getOut());
    }

    @Override
    public String toString() {
        return String.format("fork(%s)",statement);
    }

    @Override
    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException, StatementException{
        statement.typeCheck(typeTable.copy());
        return typeTable;
    }
}
