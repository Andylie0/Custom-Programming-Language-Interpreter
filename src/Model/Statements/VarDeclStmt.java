package Model.Statements;

import Exceptions.ExpressException;
import Exceptions.StatementException;
import Model.ADT.IADTDictionary;
import Model.Program_State.ProgramState;
import Model.Types.IType;
import Model.Values.IValue;

public class VarDeclStmt implements IStmt{
    private final String name;
    private final IType type;

    public VarDeclStmt(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException{
        IADTDictionary<String, IValue> symbolTable = state.getSymbolTable();
        if(symbolTable.containsKey(name))
            throw new StatementException("Variable Declaration Error: Variable "+name+" is already defined");
        symbolTable.put(name, type.defaultValue());
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s %s",type.toString(),name);
    }

    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException, StatementException{
        typeTable.put(name, type);
        return typeTable;
    }
}
