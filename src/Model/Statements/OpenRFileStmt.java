package Model.Statements;

import Exceptions.ExpressException;
import Exceptions.StatementException;
import Model.ADT.IADTDictionary;
import Model.Expressions.IExp;
import Model.Program_State.ProgramState;
import Model.Types.IType;
import Model.Types.StringType;
import Model.Values.StringValue;

import java.io.FileReader;
import java.io.IOException;

import java.io.BufferedReader;

public class OpenRFileStmt implements IStmt{
    private final IExp expression;

    public OpenRFileStmt(IExp expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressException {
        var value = expression.eval(state.getSymbolTable(),state.getHeap());
        if(!value.getType().equals(new StringType())) {
            throw new StatementException(String.format("OPEN READ FILE Error: Expression %s is not of type string",value));
        }
        StringValue filename = (StringValue) value;
        IADTDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if(fileTable.containsKey(filename.getValue()))
            throw new StatementException("OPEN READ FILE Error: File "+filename.getValue()+" already opened");

        BufferedReader buff;
        try{
            buff = new BufferedReader(new FileReader(filename.getValue()));
        }
        catch(IOException e){
            throw new StatementException(String.format("OPEN READ FILE Error: File %s did not open",filename));
        }
        fileTable.put(filename.getValue(), buff);

        return null;
    }

    @Override
    public String toString(){
        return String.format("OpenReadFile(%s)", expression);
    }

    @Override
    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException, StatementException{
        var typeExp = expression.typeCheck(typeTable);
        if(!typeExp.equals(new StringType())) {
            throw new StatementException("Open Read File Error: OpenReadFile() requires a string expression.");
        }
        return typeTable;
    }
}
