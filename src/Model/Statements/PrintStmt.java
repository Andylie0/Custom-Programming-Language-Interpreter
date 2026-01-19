package Model.Statements;

import Exceptions.ExpressException;
import Exceptions.StatementException;
import Model.ADT.IADTDictionary;
import Model.Expressions.IExp;
import Model.Program_State.ProgramState;
import Model.Types.IType;

public class PrintStmt implements IStmt{
    private final IExp expression;

    public PrintStmt(IExp expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressException {
        state.getOut().push(expression.eval(state.getSymbolTable(), state.getHeap()).toString());
        return null;
    }

    @Override
    public String toString(){
        return "print("+ expression.toString()+")";
    }

    @Override
    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException, StatementException{
        expression.typeCheck(typeTable);
        return typeTable;
    }
}
