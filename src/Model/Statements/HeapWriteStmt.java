package Model.Statements;

import Exceptions.ADTException;
import Exceptions.ExpressException;
import Exceptions.StatementException;
import Model.ADT.IADTDictionary;
import Model.Expressions.IExp;
import Model.Program_State.ProgramState;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Values.RefValue;

import java.io.IOException;

public class HeapWriteStmt implements IStmt{

    private final IExp expression;
    private final String var_name;

    public HeapWriteStmt(String var_name, IExp expression) {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressException, ADTException {
        var symTable = state.getSymbolTable();
        var heap = state.getHeap();

        if (!symTable.containsKey(var_name)) {
            throw new StatementException("STATEMENT ERROR: Variable " + var_name + " not declared");
        }

        if (!(symTable.get(var_name).getType() instanceof RefType))
            throw new StatementException("Statement ERROR: " + var_name + "Not of type Reference!!!");

        var eval = expression.eval(state.getSymbolTable(),state.getHeap());
        var locationType = ((RefValue)symTable.get(var_name)).getLocationType();

        if(!locationType.equals(eval.getType())){
            throw new StatementException("HEAP ALLOCATION ERROR: " + var_name + "is not of type" + eval.getType() + "!!!");
        }

        heap.update(((RefValue)symTable.get(var_name)).getAddress(), eval);
        return null;
    }


    @Override
    public String toString() {
        return String.format("wH(%s,%s)",var_name,expression);
    }

    @Override
    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException, StatementException{
        var typeExp = expression.typeCheck(typeTable);
        var typeVar = typeTable.get(var_name);
        if(!typeVar.equals(new RefType(typeExp)))
            throw new StatementException("HEAP ALLOCATION ERROR: Type of variable "+var_name+" does not match the type of the expression");

        return typeTable;
    }
}
