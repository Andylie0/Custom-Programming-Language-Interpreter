package Model.Statements;

import Exceptions.ADTException;
import Exceptions.ExpressException;
import Exceptions.StatementException;
import Model.ADT.IADTDictionary;
import Model.Expressions.IExp;
import Model.Program_State.ProgramState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Values.BoolValue;

public class WhileStmt implements IStmt{
    private final IExp expression;
    private final IStmt statement;

    public WhileStmt(IExp expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressException, ADTException{
        var eval = expression.eval(state.getSymbolTable(),state.getHeap());

        if(!eval.getType().equals(new BoolType())) {
            throw new StatementException(String.format("WHILE Error: Expression %s is not of type bool",eval));
        }

        BoolValue boolValue = (BoolValue) eval;
        if(boolValue.getValue()) {
            state.getExecStack().push(this);
            state.getExecStack().push(statement);
        }
        return null;

    }

    @Override
    public String toString(){
        return String.format("While(%s) {%s})", expression, statement);
    }

    @Override
    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException, StatementException{
        IType typeExp = expression.typeCheck(typeTable);

        if(typeExp.equals(new BoolType())) {
            statement.typeCheck(typeTable.copy());
            return typeTable;
        }

        throw new StatementException(String.format("WHILE Error: Expression %s is not of type bool",expression));
    }

}