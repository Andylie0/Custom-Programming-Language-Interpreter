package Model.Statements;

import Exceptions.ExpressException;
import Exceptions.StatementException;
import Model.ADT.IADTDictionary;
import Model.ADT.IADTStack;
import Model.Expressions.IExp;
import Model.Program_State.ProgramState;
import Model.Types.IType;
import Model.Values.IValue;

public class AssignStmt implements IStmt{
    private final String Id;
    private final IExp expression;

    public AssignStmt(String Id, IExp expression) {
        this.Id = Id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressException {
        IADTDictionary<String, IValue> symbolTable = state.getSymbolTable();

        if (symbolTable.containsKey(Id)){
            IValue value = expression.eval(symbolTable, state.getHeap());
            IType type = symbolTable.get(Id).getType();
            if(value.getType().equals(type))
                symbolTable.put(Id, value);
            else
                throw new StatementException("Assignment Error: Declared type of variable "+Id+" and type of the assigned expression do not match");
        }
        else
            throw new StatementException("Assignment Error: The used variable "+Id+" was not declared before");

        return null;
    }


    @Override
    public String toString(){
        return Id+"="+expression.toString();
    }


    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException, StatementException{
        var typeVar = typeTable.get(Id);
        var typeExp = expression.typeCheck(typeTable);
        if(typeVar.equals(typeExp))
            return typeTable;
        else
            throw new StatementException("Assignment Error: Declared type of variable "+Id+" and type of the assigned expression do not match");
    }
}
