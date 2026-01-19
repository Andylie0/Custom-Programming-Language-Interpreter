package Model.Statements;

import Exceptions.ExpressException;
import Exceptions.StatementException;
import Model.ADT.IADTDictionary;
import Model.Expressions.IExp;
import Model.Program_State.ProgramState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Values.BoolValue;

public class IfStmt implements IStmt{
    private final IExp expression;
    private final IStmt thenS;
    private final IStmt elseS;

    public IfStmt(IExp expression, IStmt thenS, IStmt elseS) {
        this.expression = expression;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException{
         var value = expression.eval(state.getSymbolTable(),state.getHeap());

         if(!value.getType().equals(new BoolType())) {
             throw new StatementException(String.format("Conditional Error: Condition %s is not of type bool",value));
         }

         var condition = (BoolValue) value;
         if (condition.getValue())
             state.getExecStack().push(thenS);
         else
             state.getExecStack().push(elseS);

         return null;
    }

    @Override
    public String toString(){
        return String.format("IF(%s) THEN(%s) ELSE(%s)", expression.toString(), thenS.toString(), elseS.toString());
    }

    @Override
    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException, StatementException{
        IType typeExp = expression.typeCheck(typeTable);
        if(!typeExp.equals(new BoolType()))
            throw new StatementException(String.format("Conditional Error: Condition %s is not of type bool",expression));
        thenS.typeCheck(typeTable.copy());
        elseS.typeCheck(typeTable.copy());
        return typeTable;
    }
}
