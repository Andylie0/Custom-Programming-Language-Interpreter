package Model.Statements;

import Exceptions.*;
import Model.ADT.IADTDictionary;
import Model.Expressions.IExp;
import Model.Program_State.ProgramState;
import Model.Types.IType;
import Model.Types.StringType;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{
    private final IExp expression;

    public CloseRFileStmt(IExp expression) { this.expression = expression; }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressException {
        var val = expression.eval(state.getSymbolTable(),state.getHeap());

        if(!val.getType().equals(new StringType())) {
            throw new StatementException(String.format("CLOSE READ FILE Error: Expression %s is not of type string",val));
        }

        StringValue filename = (StringValue) val;
        IADTDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if(!fileTable.containsKey(filename.getValue()))
            throw new StatementException("CLOSE READ FILE Error: File "+filename.getValue()+" was not found");

        BufferedReader buff = fileTable.get(filename.getValue());
        try{
            buff.close();
            fileTable.remove(filename.getValue());
        }catch(IOException | ADTException e){
            throw new StatementException("CLOSE READ FILE Error: File "+filename.getValue()+" did not close");
        }

        return null;
    }

    @Override
    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException, StatementException{
        var typeExp = expression.typeCheck(typeTable);
        if(!typeExp.equals(new StringType()))
            throw new StatementException("CLOSE READ FILE Error: Expression is not of type string");
        return typeTable;
    }
}
